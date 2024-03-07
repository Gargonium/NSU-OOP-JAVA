package ru.nsu.votintsev.model;

import ru.nsu.votintsev.view.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers(Changes change);
}
