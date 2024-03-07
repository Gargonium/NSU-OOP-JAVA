package ru.nsu.votintsev.model.observer.interfaces;

import ru.nsu.votintsev.model.Changes;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers(Changes change);
}
