package ru.nsu.votintsev.model.game.objects;

public interface GameObject {

    void scaleMe();

    int getX();
    int getY();

    int getWidth();
    int getHeight();

    void setX(int x);
    void setY(int y);
    void setWidth(int width);
    void setHeight(int height);

    void checkForCollisionsAndMove();
}
