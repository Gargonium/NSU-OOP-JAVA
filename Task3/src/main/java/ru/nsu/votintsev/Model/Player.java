package ru.nsu.votintsev.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player extends GameObject implements ActionListener {

    private final int xVelocity = 7;
    private final int yVelocity = 5;
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveRight;
    private boolean moveLeft;

    private int jumpCounter = 20;
    private boolean isJump = false;
    private boolean isInAir = true;

    private int height;
    private int width;

    private boolean gravity = false;

    private final Timer timer = new Timer(1, this);

    private PlayerDirection playerDirection = PlayerDirection.STAND;

    private int GAME_FIELD_WIDTH;
    private int GAME_FIELD_HEIGHT;

    private int startX = 0;
    private int startY = 0;

    private final GameContext ctx;

    public Player(GameContext context) {
        X = startX;
        Y = startY;
        timer.start();

        ctx = context;
    }

    public void setStartCords(int x, int y) {
        startX = x;
        startY = y;
    }

    public void setGameFieldDimensions(int gameFieldWidth, int gameFieldHeight) {
        GAME_FIELD_WIDTH = gameFieldWidth;
        GAME_FIELD_HEIGHT = gameFieldHeight;
    }

    public void setMovements(boolean isUp, boolean isDown, boolean isRight, boolean isLeft) {
        moveDown = isDown;
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

    private void checkForWalls() {
        for (Wall wall : ctx.getWalls()) {
            if ((X + width > wall.getX()) && (X <= wall.getWidth() + wall.getX())){
                if ((Y + height <= wall.getY()) || (Y >= wall.getY() + wall.getHeight())){
                    gravity = true;
                } else {
                    gravity = false;
                    Y = wall.getY() - height;
                    isInAir = false;
                }
            } else if (!gravity) {
                gravity = true;
                isInAir = true;
            }
        }

        moveDown = gravity;
    }

    private void jump() {
        jumpCounter--;
        Y -= yVelocity;
        isInAir = true;
        if (jumpCounter == 0) {
            isJump = false;
            jumpCounter = 20;
        }
        moveDown = false;
        moveMe();
    }

    private void moveMe() {

        if (moveDown) {
            Y += yVelocity;
            if (Y >= GAME_FIELD_HEIGHT) {
                death();
            }
        }

        if (moveLeft) {
            if (X > 0)
                X -= xVelocity;
            playerDirection = PlayerDirection.LEFT;
        } else if (moveRight) {
            if (X < GAME_FIELD_WIDTH + width)
                X += xVelocity;
            playerDirection = PlayerDirection.RIGHT;
        } else {
            playerDirection = PlayerDirection.STAND;
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {

            if (moveUp && !isInAir) {
                isJump = true;
            }

            if (isJump) {
                jump();
            } else {
                moveMe();
            }

            checkForWalls();
        }
    }
}
