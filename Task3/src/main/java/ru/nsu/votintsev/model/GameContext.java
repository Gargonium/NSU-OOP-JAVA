package ru.nsu.votintsev.model;

import ru.nsu.votintsev.model.game.objects.Door;
import ru.nsu.votintsev.model.game.objects.Enemy;
import ru.nsu.votintsev.model.game.objects.Wall;

import java.util.List;

public class GameContext {

    private List<Wall> walls;
    private List<Enemy> enemies;
    private Door door;

    private int GAME_FIELD_WIDTH;
    private int GAME_FIELD_HEIGHT;

    public ModelScaleInator modelScaleInator = new ModelScaleInator();

    public GameContext() {

    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
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
