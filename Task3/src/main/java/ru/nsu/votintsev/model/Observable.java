package ru.nsu.votintsev.model;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers(Changes change);
}
