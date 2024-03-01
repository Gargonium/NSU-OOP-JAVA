package ru.nsu.votintsev.Model;

import java.util.AbstractList;

public class GameContext {

    AbstractList<Wall> walls;

    public GameContext() {

    }
    public void setWalls(AbstractList<Wall> walls) {
        this.walls = walls;
    }

    public AbstractList<Wall> getWalls() {
        return walls;
    }



}
