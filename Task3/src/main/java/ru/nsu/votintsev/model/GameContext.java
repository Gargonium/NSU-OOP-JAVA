package ru.nsu.votintsev.model;

import ru.nsu.votintsev.model.game.objects.Door;

import java.util.AbstractList;

public class GameContext {

    AbstractList<Wall> walls;
    Door door;

    private int GAME_FIELD_WIDTH;
    private int GAME_FIELD_HEIGHT;

    public GameContext() {

    }
    public void setWalls(AbstractList<Wall> walls) {
        this.walls = walls;
    }

    public AbstractList<Wall> getWalls() {
        return walls;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return door;
    }

    public void setGameFieldDimensions(int gameFieldWidth, int gameFieldHeight) {
        GAME_FIELD_WIDTH = gameFieldWidth;
        GAME_FIELD_HEIGHT = gameFieldHeight;
    }

    public int getGameFieldWidth() {
        return GAME_FIELD_WIDTH;
    }

    public int getGameFieldHeight() {
        return GAME_FIELD_HEIGHT;
    }
}
