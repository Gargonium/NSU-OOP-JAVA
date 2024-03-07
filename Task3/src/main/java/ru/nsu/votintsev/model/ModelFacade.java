package ru.nsu.votintsev.model;

import ru.nsu.votintsev.model.aw.*;
import ru.nsu.votintsev.model.directions.EnemyDirection;
import ru.nsu.votintsev.model.directions.PlayerDirection;
import ru.nsu.votintsev.model.observer.interfaces.Observable;
import ru.nsu.votintsev.model.observer.interfaces.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ModelFacade implements Observable, ActionListener {
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Wall> walls = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();
    private final Player player;
    private final Door door;
    private final GameContext ctx;

    private final List<GameObject> objects = new ArrayList<>();

    private final Timer modelTimer = new Timer(10, this);

    public ModelFacade() {
        ctx = new GameContext();

        ctx.setWalls(walls);
        ctx.setEnemies(enemies);

        door = new Door(ctx);
        ctx.setDoor(door);
        player = new Player(ctx);

        readWalls();
        readEnemies();

        objects.addAll(enemies);
        objects.addAll(walls);
        objects.add(player);
        objects.add(door);
    }

    private void readWalls() {
        walls.add(new Wall(ctx, 0, 450, 200, 128));
        walls.add(new Wall(ctx,300, 450, 1000, 128));
        walls.add(new Wall(ctx,1400, 450, 200, 100));
    }

    private void readEnemies() {
        enemies.add(new Enemy(ctx,900, 386));
        enemies.add(new Enemy(ctx,400, 386));
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

    public void movePlayer(boolean isUp, boolean isRight, boolean isLeft) {
        player.setMovements(isUp, isRight, isLeft);
    }

    public void setGameFieldDimensions(int gameFieldWidth, int gameFieldHeight) {
        ctx.setGameFieldDimensions(gameFieldWidth, gameFieldHeight);
    }
    public void setScalePercentage(double scalePercentWidth, double scalePercentHeight) {
        ctx.modelScaleInator.setScalePercentage(scalePercentWidth, scalePercentHeight);
        for (GameObject object : objects) {
            object.scaleMe();
        }
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

    public PlayerDirection getPlayerDirection() {
        return player.getPlayerDirection();
    }

    public EnemyDirection getEnemyDirection(int id) {
        return enemies.get(id).getEnemyDirection();
    }

    public void playerInteract() {
        player.interact();
    }

    public void startModelTimer() {
        modelTimer.start();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Changes change) {
        for (Observer observer : observers) {
            observer.update(change);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modelTimer) {
            player.checkForCollisionsAndMove();
            notifyObservers(Changes.CHANGE_PLAYER_CORDS);
            for (Enemy enemy : enemies) {
                enemy.checkForCollisionsAndMove();
            }
            notifyObservers(Changes.CHANGE_ENEMY_CORDS);
        }
    }
}
