package ru.nsu.votintsev.model;

import ru.nsu.votintsev.model.game.objects.GameObject;

public class Wall extends GameObject {
    private int width;
    private int height;

    public Wall() {
        width = 0;
        height = 0;
        X = 0;
        Y = 0;
    }

    public Wall(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        X = x;
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
