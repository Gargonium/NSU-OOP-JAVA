package ru.nsu.votintsev.client;

import ru.nsu.votintsev.client.view.ViewEvents;

public class ClientController implements Observer {
    @Override
    public void update(ViewEvents change) {
        switch (change) {
            case MESSAGE_RECEIVED -> {}
            case USER_LIST_RECEIVED -> {}
        }
    }
}
