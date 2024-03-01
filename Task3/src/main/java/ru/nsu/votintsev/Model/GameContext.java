package ru.nsu.votintsev.Model;

import java.util.AbstractList;

public class GameContext {

    AbstractList<Wall> walls;
    Door door;

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

}
