package ru.nsu.votintsev.Model;

public class Player extends GameObject {

    int xVelocity;
    int yVelocity;

    int lives;

    public Player() {

    }

    public int getXVelocity() {
        return xVelocity;
    }
    public int getYVelocity() {
        return yVelocity;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void action() {

    }
}
