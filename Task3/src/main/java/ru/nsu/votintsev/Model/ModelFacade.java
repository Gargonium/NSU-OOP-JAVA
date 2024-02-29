package ru.nsu.votintsev.Model;

import ru.nsu.votintsev.Observer;

import java.awt.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class ModelFacade implements Observable {
    AbstractList<Observer> observers;
    AbstractList<GameObject> objects;
    AbstractList<Enemy> enemies;
    AbstractList<Wall> walls;
    Player player;
    Door door;
    GameContext ctx;

    public ModelFacade() {
        ctx = new GameContext();
        walls = new ArrayList<>();

        ctx.setWalls(walls);

        observers = new ArrayList<>();
        objects = new ArrayList<>();
        enemies = new ArrayList<>();

        player = new Player(ctx);
        door = new Door();

        objects.addAll(enemies);
        objects.add(player);
        objects.add(door);
        readWalls();
        objects.addAll(walls);

        start();
    }

    public void start() {
        for (GameObject object : objects) {
            object.action();
        }
    }

    public int getPlayerX() {
        return player.getX();
    }
    public int getPlayerY() {
        return player.getY();
    }

    public void setPlayerSize(int width, int height) {
        player.setSize(width, height);
    }

    public void movePlayer(boolean isUp, boolean isDown, boolean isRight, boolean isLeft) {
        player.setMovements(isUp, isDown, isRight, isLeft);
        notifyObservers("Change Cords");
    }

    public int[] getWall(int index) {
        Wall wall = walls.get(index);
        return new int[]{wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight()};
    }

    public int getWallsCount() {
        return walls.size();
    }

    private void readWalls() {
        walls.add(new Wall(0, 450, 500, 50));
    }

    @Override
    public void addObserver(Observer observer) {
    observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
    observers.remove(observer);
    }

    @Override
    public void notifyObservers(String change) {
        for (Observer observer : observers) {
            observer.update(change);
        }
    }
}
