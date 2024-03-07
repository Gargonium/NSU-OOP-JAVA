package ru.nsu.votintsev.model.observer.interfaces;

import ru.nsu.votintsev.model.Changes;

public interface Observer {
    void update(Changes change);
}
