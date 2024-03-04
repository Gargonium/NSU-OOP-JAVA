package ru.nsu.votintsev.model;

import ru.nsu.votintsev.model.game.objects.Door;
import ru.nsu.votintsev.model.game.objects.Enemy;

import java.util.Vector;

public class GameContext {

    Vector<Wall> walls;
    Vector<Enemy> enemies;
    Door door;

    private int GAME_FIELD_WIDTH;
    private int GAME_FIELD_HEIGHT;

    public GameContext() {

    }
    public void setWalls(Vector<Wall> walls) {
        this.walls = walls;
    }

    public Vector<Wall> getWalls() {
        return walls;
    }

    public Vector<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(Vector<Enemy> enemies) {
        this.enemies = enemies;
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
