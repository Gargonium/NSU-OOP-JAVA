package ru.nsu.votintsev.Model;

import ru.nsu.votintsev.View.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String change);
}
