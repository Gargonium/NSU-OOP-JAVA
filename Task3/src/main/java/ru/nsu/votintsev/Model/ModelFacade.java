package ru.nsu.votintsev.Model;

import ru.nsu.votintsev.Observer;

import java.awt.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class ModelFacade implements Observable {
    AbstractList<Observer> observers;
    AbstractList<GameObject> objects;
    AbstractList<Enemy> enemies;
    Player player;
    Door door;

    public ModelFacade() {
        observers = new ArrayList<>();
        objects = new ArrayList<>();
        player = new Player();
        door = new Door();
        enemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            objects.add(enemy);
        }
        objects.add(player);
        objects.add(door);
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

    public void movePlayer(boolean isUp, boolean isDown, boolean isRight, boolean isLeft) {
        if (isUp) {
            player.setY(player.getY() - player.getYVelocity());
        } else if (isDown) {
            player.setY(player.getY() + getPlayerY());
        }

        if (isRight) {
            player.setX(player.getX() + player.getXVelocity());
        } else if (isLeft) {
            player.setX(player.getX() - player.getXVelocity());
        }
        notifyObservers("Change Cords");
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
