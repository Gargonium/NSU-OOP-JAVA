package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;
import ru.nsu.votintsev.model.directions.PlayerDirection;

public class Player extends BaseGameObject {

    private boolean moveUp;
    private boolean moveRight;
    private boolean moveLeft;

    private PlayerDirection playerDirection = PlayerDirection.STAND;

    private int startX = 0;
    private int startY = 0;

    private int xSpeed = 7;
    private int gravitySpeed = 10;

    private boolean isJumping = false;
    private int jumpSpeed = 0;
    private int jumpAcceleration = 1;
    private int maxJumpSpeed = 16;

    public Player(GameContext context) {
        ctx = context;
    }

    public void setStartCords(int X, int Y) {
        startX = X;
        startY = Y;
        this.x = X;
        this.y = Y;
    }

    public void setMovements(boolean isUp, boolean isRight, boolean isLeft) {
        moveLeft = isLeft;
        moveRight = isRight;
        moveUp = isUp;
    }

    public void setSize(int width, int height) {
        this.width = width - 10;
        this.height = height;
    }

    public PlayerDirection getPlayerDirection() {
        return playerDirection;
    }

    public void interact() {
        Door door = ctx.getDoor();
        if (((x + width >= door.getX()) && (x <= door.getX() + door.getWidth())) &&
                ((y <= door.getY() + door.getHeight()) && (y + height >= door.getY()))) {
           reachDoor();
        }
    }

    private void death() {
        x = startX;
        y = startY;
    }

    private void reachDoor() {
        x = startX;
        y = startY;
    }

    @Override
    public void scaleMe() {
        startX = ctx.modelScaleInator.scaleByX(x);
        startY = ctx.modelScaleInator.scaleByY(y);
        x = startX;
        y = startY;

        xSpeed = ctx.modelScaleInator.scaleByX(xSpeed);
        gravitySpeed = ctx.modelScaleInator.scaleByY(gravitySpeed);
        jumpAcceleration = ctx.modelScaleInator.scaleByY(jumpAcceleration);

        int jumpHeight = 0;
        for (int i = 1; i < maxJumpSpeed; ++i) {
            jumpHeight += i;
        }
        jumpHeight = ctx.modelScaleInator.scaleByY(jumpHeight);

        maxJumpSpeed = 0;
        int jumpSpeedIterator = 1;
        while (jumpHeight >= 0) {
            jumpHeight -= jumpSpeedIterator;
            jumpSpeedIterator++;
            maxJumpSpeed++;
        }
    }

    @Override
    public void checkForCollisionsAndMove() {
        boolean isOnGround = false;
        try {
            for (Wall wall : ctx.getWalls()) {
                if (x < wall.getX() + wall.getWidth() && x + width > wall.getX() &&
                        y <= wall.getY() + wall.getHeight() && y + height >= wall.getY()) {

                    int overlapX = Math.min(x + width, wall.getX() + wall.getWidth()) - Math.max(x, wall.getX());
                    int overlapY = Math.min(y + height, wall.getY() + wall.getHeight()) - Math.max(y, wall.getY());

                    if (overlapX < overlapY) {
                        if (x < wall.getX()) {
                            x = wall.getX() - width;
                        } else {
                            x = wall.getX() + wall.getWidth();
                        }
                    } else {
                        if (y <= wall.getY()) {
                            y = wall.getY() - height;
                            isOnGround = true;
                        } else {
                            y = wall.getY() + wall.getHeight();
                        }
                    }
                }
            }

            for (Enemy enemy : ctx.getEnemies()) {
                if (x <= enemy.getX() + enemy.getWidth() &&
                        x + width >= enemy.getX() &&
                        y <= enemy.getY() + enemy.getHeight() &&
                        y + height >= enemy.getY()) {
                    death();
                }
            }
        } catch (Exception ignored) {

        }

        if (moveLeft) {
            if (x > 0)
                x -= xSpeed;
            playerDirection = PlayerDirection.LEFT;
        } else if (moveRight) {
            if (x + width < ctx.getGameFieldWidth())
                x += xSpeed;
            playerDirection = PlayerDirection.RIGHT;
        } else {
            playerDirection = PlayerDirection.STAND;
        }

        if (!isOnGround && !isJumping) {
            y += gravitySpeed;
            if (y >= ctx.getGameFieldHeight())
                death();
        }

        if (moveUp && isOnGround) {
            isJumping = true;
            jumpSpeed = maxJumpSpeed;
        }

        if (isJumping) {
            y -= jumpSpeed;
            jumpSpeed -= jumpAcceleration;

            if (jumpSpeed <= 0) {
                isJumping = false;
            }
        }
    }
}
