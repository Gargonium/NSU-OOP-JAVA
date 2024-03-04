package ru.nsu.votintsev.model;

import ru.nsu.votintsev.model.directions.EnemyDirection;
import ru.nsu.votintsev.model.directions.PlayerDirection;
import ru.nsu.votintsev.model.game.objects.Player;
import ru.nsu.votintsev.model.game.objects.Door;
import ru.nsu.votintsev.model.game.objects.Enemy;
import ru.nsu.votintsev.model.game.objects.Wall;
import ru.nsu.votintsev.view.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ModelFacade implements Observable, ActionListener {
    private final Vector<Enemy> enemies = new Vector<>();
    private final Vector<Wall> walls = new Vector<>();
    private final Vector<Observer> observers = new Vector<>();
    private final Player player;
    private final Door door;
    private final GameContext ctx;

    private final Timer modelTimer = new Timer(10, this);

    public ModelFacade() {
        ctx = new GameContext();

        ctx.setWalls(walls);
        ctx.setEnemies(enemies);

        door = new Door();
        ctx.setDoor(door);
        player = new Player(ctx);

        modelTimer.start();

        readWalls();
        readEnemies();
    }

    private void readWalls() {
        walls.add(new Wall(0, 450, 200, 64));
        walls.add(new Wall(300, 450, 1000, 64));
        walls.add(new Wall(1400, 450, 200, 64));
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
        notifyObservers("Change Cords");
    }

    public void setGameFieldDimensions(int gameFieldWidth, int gameFieldHeight) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modelTimer) {
            player.checkForCollisionsAndMove();
            for (Enemy enemy : enemies) {
                enemy.checkForCollisionsAndMove();
            }
        }
    }
}
