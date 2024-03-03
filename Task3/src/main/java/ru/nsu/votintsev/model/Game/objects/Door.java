package ru.nsu.votintsev.model.game.objects;

public class Door extends GameObject {

    private int width;
    private int height;

    public Door() {
        X = 1000;
        Y = 386;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
