package ru.nsu.votintsev.factory.pattern.observer;

import ru.nsu.votintsev.factory.pattern.observer.Changes;

public interface Observer {
    void update(Changes change);
}
