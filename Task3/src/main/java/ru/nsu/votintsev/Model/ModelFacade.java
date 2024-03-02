package ru.nsu.votintsev.Model;

import ru.nsu.votintsev.View.Observer;

import java.awt.*;
import java.util.Vector;

public class ModelFacade implements Observable {
    private final Vector<Enemy> enemies = new Vector<>();
    private final Vector<Wall> walls = new Vector<>();
    private final Vector<Observer> observers = new Vector<>();
    private final Player player;
    private final Door door;
    private final GameContext ctx;

    public ModelFacade() {
        ctx = new GameContext();

        ctx.setWalls(walls);

        player = new Player(ctx);
        door = new Door();
        ctx.setDoor(door);

        readWalls();
        readEnemies();
    }

    public int getPlayerX() {
        return player.getX();
    }
    public int getPlayerY() {
        return player.getY();
    }

    public int getDoorX() {
        return door.getX();
    }
    public int getDoorY() {
        return door.getY();
    }

    public int getEnemyX(int id) {
        return enemies.get(id).getX();
    }
    public int getEnemyY(int id) {
        return enemies.get(id).getY();
    }

    public void setPlayerSize(int width, int height) {
        player.setSize(width, height);
    }
    public void setDoorSize(int width, int height) {
        door.setWidth(width);
        door.setHeight(height);
    }
    public void setEnemySize(int id, int width, int height) {
        enemies.get(id).setWidth(width);
        enemies.get(id).setHeight(height);
    }

    public void movePlayer(boolean isUp, boolean isDown, boolean isRight, boolean isLeft) {
        player.setMovements(isUp, isDown, isRight, isLeft);
        notifyObservers("Change Cords");
    }

    public void setGameFieldDimensions(int gameFieldHeight, int gameFieldWidth) {
        ctx.setGameFieldDimensions(gameFieldWidth, gameFieldHeight);
    }

    public void setPlayerStartCords(int x, int y) {
        player.setStartCords(x, y);
    }

    public Rectangle getWallRect(int index) {
        Wall wall = walls.get(index);
        return new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
    }

    public int getWallsCount() {
        return walls.size();
    }

    public int getEnemiesCount() {
        return enemies.size();
    }

    private void readWalls() {
        walls.add(new Wall(100, 450, 2000, 50));
    }

    private void readEnemies() {
        enemies.add(new Enemy(ctx, 0,900, 386));
        enemies.add(new Enemy(ctx, 1,400, 386));
    }

    public PlayerDirection getPlayerDirection() {
        return player.getPlayerDirection();
    }

    public EnemyDirection getEnemyDirection(int id) {
        return enemies.get(id).getEnemyDirection();
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
