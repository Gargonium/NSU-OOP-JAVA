package ru.nsu.votintsev.Model;

public abstract class GameObject {
    private int X = 0;
    private int Y = 0;

    public void action() {}

    public int getX() { return X; }

    public int getY() { return Y;}

    public void setX(int x) { X = x; }

    public void setY(int y) { Y = y; }
}
