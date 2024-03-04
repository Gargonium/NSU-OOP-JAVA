package ru.nsu.votintsev.model.game.objects;

public class Wall implements GameObject {

    private int X;
    private int Y;

    private int width;
    private int height;

    public Wall(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        X = x;
        Y = y;
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
