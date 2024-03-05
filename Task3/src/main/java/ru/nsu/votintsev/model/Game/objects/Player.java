package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;
import ru.nsu.votintsev.model.directions.PlayerDirection;

public class Player implements GameObject {

    private int X;
    private int Y;

    private int height;
    private int width;

    private final GameContext ctx;

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
        this.X = X;
        this.Y = Y;
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
        if (((X + width >= door.getX()) && (X <= door.getX() + door.getWidth())) &&
                ((Y <= door.getY() + door.getHeight()) && (Y + height >= door.getY()))) {
           reachDoor();
        }
    }

    private void death() {
        X = startX;
        Y = startY;
    }

    private void reachDoor() {
        X = startX;
        Y = startY;
    }

    @Override
    public void scaleMe() {
        startX = ctx.modelScaleInator.scaleByX(X);
        startY = ctx.modelScaleInator.scaleByY(Y);
        X = startX;
        Y = startY;

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
    public int getX() {
        return X;
    }

    @Override
    public void setX(int x) {
        X = x;
    }

    @Override
    public int getY() {
        return Y;
    }

    @Override
    public void setY(int y) {
        Y = y;
    }

    public void checkForCollisionsAndMove() {
        boolean isOnGround = false;
        try {
            for (Wall wall : ctx.getWalls()) {
                if (X < wall.getX() + wall.getWidth() && X + width > wall.getX() &&
                        Y <= wall.getY() + wall.getHeight() && Y + height >= wall.getY()) {

                    int overlapX = Math.min(X + width, wall.getX() + wall.getWidth()) - Math.max(X, wall.getX());
                    int overlapY = Math.min(Y + height, wall.getY() + wall.getHeight()) - Math.max(Y, wall.getY());

                    if (overlapX < overlapY) {
                        if (X < wall.getX()) {
                            X = wall.getX() - width;
                        } else {
                            X = wall.getX() + wall.getWidth();
                        }
                    } else {
                        if (Y <= wall.getY()) {
                            Y = wall.getY() - height;
                            isOnGround = true;
                        } else {
                            Y = wall.getY() + wall.getHeight();
                        }
                    }
                }
            }

            for (Enemy enemy : ctx.getEnemies()) {
                if (X <= enemy.getX() + enemy.getWidth() &&
                        X + width >= enemy.getX() &&
                        Y <= enemy.getY() + enemy.getHeight() &&
                        Y + height >= enemy.getY()) {
                    death();
                }
            }
        } catch (Exception ignored) {

        }

        if (moveLeft) {
            if (X > 0)
                X -= xSpeed;
            playerDirection = PlayerDirection.LEFT;
        } else if (moveRight) {
            if (X + width < ctx.getGameFieldWidth())
                X += xSpeed;
            playerDirection = PlayerDirection.RIGHT;
        } else {
            playerDirection = PlayerDirection.STAND;
        }

        if (!isOnGround && !isJumping) {
            Y += gravitySpeed;
            if (Y >= ctx.getGameFieldHeight())
                death();
        }

        if (moveUp && isOnGround) {
            isJumping = true;
            jumpSpeed = maxJumpSpeed;
        }

        if (isJumping) {
            Y -= jumpSpeed;
            jumpSpeed -= jumpAcceleration;

            if (jumpSpeed <= 0) {
                isJumping = false;
            }
        }
    }
}
