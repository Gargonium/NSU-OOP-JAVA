package ru.nsu.votintsev.client;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.client.view.MessageType;
import ru.nsu.votintsev.client.view.ViewEvents;
import ru.nsu.votintsev.xmlclasses.*;
import ru.nsu.votintsev.xmlclasses.Error;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientReceiver implements Runnable, Observable {
    private final Socket socket;
    private final XMLParser xmlParser;
    private final FileExchanger fileExchanger;
    private final DataInputStream inputStream;

    private final ClientClasses clientClasses = new ClientClasses();
    private Observer observer;

    private final Map<String, MessageType> messageBuffer = new HashMap<>();
    private Users users;

    public ClientReceiver(Socket socket, XMLParser xmlParser, FileExchanger fileExchanger) throws IOException {
        this.socket = socket;
        this.xmlParser = xmlParser;
        this.fileExchanger = fileExchanger;
        inputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                String xmlString = fileExchanger.receiveFile(inputStream);
                switch (clientClasses.parseFromXML(xmlParser, xmlString)) {
                    case Event event -> {
                        switch (event.getEvent()) {
                            case "message" -> messageEvent(event);
                            case "userlogin" -> userLoginEvent(event);
                            case "userlogout" -> userLogoutEvent(event);
                            default -> System.out.println("Unknown event: " + event.getEvent());
                        }
                    }
                    case Success success -> {
                        if (success.getUsers() != null)
                            userListReceived(success);
                    }
                    case Error error -> System.out.printf(error.getMessage());
                    default -> System.out.println("Unknown xmlBlock");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void messageEvent(Event event) {
        messageBuffer.put("<" + event.getFrom() + ">: " + event.getMessage(), MessageType.USER_MESSAGE);
        notifyObservers(ViewEvents.MESSAGE_RECEIVED);
    }

    private void userLoginEvent(Event event) {
        messageBuffer.put(event.getName() + " connect", MessageType.SERVER_MESSAGE);
        notifyObservers(ViewEvents.MESSAGE_RECEIVED);
    }

    private void userLogoutEvent(Event event) {
        messageBuffer.put(event.getName() + " disconnect", MessageType.SERVER_MESSAGE);
        notifyObservers(ViewEvents.MESSAGE_RECEIVED);
    }

    private void userListReceived(Success success) {
        users = success.getUsers();
        notifyObservers(ViewEvents.USER_LIST_RECEIVED);
    }

    public Map<String, MessageType> getMessageBuffer() {
        Map<String, MessageType> temp = new HashMap<>(messageBuffer);
        messageBuffer.clear();
        return temp;
    }

    public Users getUsers() {
        return users;
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