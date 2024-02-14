package ru.nsu.votintsev.Model;

import ru.nsu.votintsev.Observer;

import java.awt.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class ModelFacade implements Observable {
    AbstractList<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(new Object());
        }
    }
}
