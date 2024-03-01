package ru.nsu.votintsev.Model;

import ru.nsu.votintsev.View.Observer;

import java.util.AbstractList;
import java.util.ArrayList;

public class ModelFacade implements Observable {
    private final AbstractList<Observer> observers;
    private final AbstractList<GameObject> objects;
    private final AbstractList<Enemy> enemies;
    private final AbstractList<Wall> walls;
    private final Player player;
    private final Door door;
    private final GameContext ctx;

    public ModelFacade() {
        ctx = new GameContext();
        walls = new ArrayList<>();

        ctx.setWalls(walls);

        observers = new ArrayList<>();
        objects = new ArrayList<>();
        enemies = new ArrayList<>();

        player = new Player(ctx);
        door = new Door();
        ctx.setDoor(door);

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

    public void setDoorSize(int width, int height) {
        door.setWidth(width);
        door.setHeight(height);
    }

    public void movePlayer(boolean isUp, boolean isDown, boolean isRight, boolean isLeft) {
        player.setMovements(isUp, isDown, isRight, isLeft);
        notifyObservers("Change Cords");
    }

    public void setGameFieldDimensions(int gameFieldHeight, int gameFieldWidth) {
        player.setGameFieldDimensions(gameFieldWidth, gameFieldHeight);
    }

    public void setPlayerStartCords(int x, int y) {
        player.setStartCords(x, y);
    }

    public int[] getWall(int index) {
        Wall wall = walls.get(index);
        return new int[]{wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight()};
    }

    public int getWallsCount() {
        return walls.size();
    }

    private void readWalls() {
        walls.add(new Wall(0, 450, 2000, 50));
    }

    public PlayerDirection getPlayerDirection() {
        return player.getPlayerDirection();
    }

    public int getDoorX() {
        return door.getX();
    }

    public int getDoorY() {
        return door.getY();
    }

    public void playerInteract() {
        player.interact();
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
