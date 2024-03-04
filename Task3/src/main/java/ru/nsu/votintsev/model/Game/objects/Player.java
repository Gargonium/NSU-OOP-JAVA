package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;
import ru.nsu.votintsev.model.directions.PlayerDirection;

public class Player implements GameObject {

    private int X;
    private int Y;

    private int height;
    private int width;

    private final static int X_SPEED = 7;
    private final static int GRAVITY_SPEED = 10;

    private boolean moveUp;
    private boolean moveRight;
    private boolean moveLeft;

    private PlayerDirection playerDirection = PlayerDirection.STAND;

    private int startX = 0;
    private int startY = 0;

    private final GameContext ctx;

    private boolean isJumping = false;
    private int jumpSpeed = 0;
    private static final int JUMP_ACCELERATION = 1;
    private static final int MAX_JUMP_SPEED = 20;

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
        this.width = width;
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
                X -= X_SPEED;
            playerDirection = PlayerDirection.LEFT;
        } else if (moveRight) {
            if (X + width < ctx.getGameFieldWidth())
                X += X_SPEED;
            playerDirection = PlayerDirection.RIGHT;
        } else {
            playerDirection = PlayerDirection.STAND;
        }

        if (!isOnGround && !isJumping) {
            Y += GRAVITY_SPEED;
            if (Y >= ctx.getGameFieldHeight())
                death();
        }

        if (moveUp && isOnGround) {
            isJumping = true;
            jumpSpeed = MAX_JUMP_SPEED;
        }

        if (isJumping) {
            Y -= jumpSpeed;
            jumpSpeed -= JUMP_ACCELERATION;

            if (jumpSpeed <= 0) {
                isJumping = false;
            }
        }
    }
}
