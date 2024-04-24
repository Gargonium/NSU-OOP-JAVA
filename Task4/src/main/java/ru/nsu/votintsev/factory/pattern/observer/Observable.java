package ru.nsu.votintsev.factory.pattern.observer;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers(Changes change);
}
