package ru.nsu.votintsev.client;

import ru.nsu.votintsev.client.view.ViewEvents;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers(ViewEvents change);
}
