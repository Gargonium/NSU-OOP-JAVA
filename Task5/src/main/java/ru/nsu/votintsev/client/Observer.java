package ru.nsu.votintsev.client;

import ru.nsu.votintsev.client.view.ViewEvents;

public interface Observer {
    void update(ViewEvents change);
}
