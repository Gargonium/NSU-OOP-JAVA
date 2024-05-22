package ru.nsu.votintsev.client;

import ru.nsu.votintsev.client.view.MessageType;
import ru.nsu.votintsev.client.view.ViewEvents;
import ru.nsu.votintsev.xmlclasses.Users;

import java.util.Map;

public class ClientController implements Observer, Observable {

    private ClientReceiver clientReceiver;
    private ClientSender clientSender;
    private Observer observer;

    @Override
    public void update(ViewEvents change) {
        notifyObservers(change);
    }

    public void setClientReceiver(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    public void setClientSender(ClientSender clientSender) {
        this.clientSender = clientSender;
    }

    public ClientSender getCLientSender() {
        return clientSender;
    }

    public Map<String, MessageType> getMessages() {
        return clientReceiver.getMessageBuffer();
    }

    public Users getUserList() {
        return clientReceiver.getUsers();
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void notifyObservers(ViewEvents change) {
        observer.update(change);
    }
}
