package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;

public class Door implements GameObject {

    private int X;
    private int Y;

    private int width;
    private int height;

    private final GameContext ctx;

    public Door(GameContext ctx) {
        this.ctx = ctx;
        X = 1000;
        Y = 386;
    }

    @Override
    public void scaleMe() {
        X = ctx.modelScaleInator.scaleByX(X);
        Y = ctx.modelScaleInator.scaleByY(Y);
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
