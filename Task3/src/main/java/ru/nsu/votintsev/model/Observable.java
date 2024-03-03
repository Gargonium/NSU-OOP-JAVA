package ru.nsu.votintsev.model;

import ru.nsu.votintsev.view.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String change);
}
