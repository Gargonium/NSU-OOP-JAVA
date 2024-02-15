package ru.nsu.votintsev.Model;

public abstract class GameObject {
    protected int X = 0;
    protected int Y = 0;

    public void action() {}

    public int getX() { return X; }

    public int getY() { return Y;}

    public void setX(int x) { X = x; }

    public void setY(int y) { Y = y; }
}
