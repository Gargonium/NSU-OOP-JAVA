package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;

public class Wall implements GameObject {

    private int X;
    private int Y;

    private int width;
    private int height;

    private final GameContext ctx;

    public Wall(GameContext ctx, int x, int y, int width, int height) {
        this.ctx = ctx;
        this.width = width;
        this.height = height;
        X = x;
        Y = y;
    }

    @Override
    public void scaleMe() {
        width = ctx.modelScaleInator.scaleByX(width);
        height = ctx.modelScaleInator.scaleByY(height);
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
