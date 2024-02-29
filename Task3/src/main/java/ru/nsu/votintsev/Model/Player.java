package ru.nsu.votintsev.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractList;
import java.util.ArrayList;

public class Player extends GameObject implements ActionListener {

    private final int xVelocity = 10;
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

    private final AbstractList<Wall> walls;

    private boolean gravity = false;

    private final Timer timer = new Timer(1, this);

    public Player(GameContext context) {
        X = 0;
        Y = 0;
        width = 64;
        height = 64;
        timer.start();

        walls = context.getWalls();
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

    private void checkForWalls() {
        for (Wall wall : walls) {
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
        }

        if (moveLeft) {
            X -= xVelocity;
        } else if (moveRight) {
            X += xVelocity;
        }
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
