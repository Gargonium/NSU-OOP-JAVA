package ru.nsu.votintsev.view;

import ru.nsu.votintsev.model.Changes;

public interface Observer {
    void update(Changes change);
}
