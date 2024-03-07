package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;

public abstract class BaseGameObject implements GameObject{

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected GameContext ctx;

    @Override
    public void scaleMe() {
        width = ctx.modelScaleInator.scaleByX(width);
        height = ctx.modelScaleInator.scaleByY(height);
        x = ctx.modelScaleInator.scaleByX(x);
        y = ctx.modelScaleInator.scaleByY(y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void checkForCollisionsAndMove() {

    }
}
