package ru.nsu.votintsev.client.oldrealize;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.client.Observable;
import ru.nsu.votintsev.client.Observer;
import ru.nsu.votintsev.client.view.ViewEvents;
import ru.nsu.votintsev.xmlclasses.*;
import ru.nsu.votintsev.xmlclasses.Error;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class ClientThreadOutput1 implements Runnable, Observable {
    private final FileExchanger fileExchanger;
    private final DataInputStream in;
    private final XMLParser xmlParser;
    private final File file;
    private final ClientClasses parser = new ClientClasses();

    private Observer observer;

    public ClientThreadOutput1(FileExchanger fileExchanger, DataInputStream in, XMLParser xmlParser, File file) {
        this.fileExchanger = fileExchanger;
        this.in = in;
        this.xmlParser = xmlParser;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            fileExchanger.receiveFile(in, file);

            switch (parser.parseFromXML(xmlParser, file)) {
                case Event event -> {
                    switch (event.getEvent()) {
                        case "message" -> messageEvent(event);
                        case "userlogout" -> logoutEvent(event);
                        case "userlogin" -> loginEvent(event);
                    }
                }
                case Error error -> System.out.println(error.getMessage());
                case Success success -> {
                    if (success.getUsers() != null)
                        listSuccess(success);
                }
                default -> throw new IllegalStateException("Unexpected value");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listSuccess(Success success) {
        for (User user : success.getUsers().getUsers()) {
            System.out.println(user.getName());
        }
        notifyObservers(ViewEvents.NEED_USER_LIST);
    }

    private void messageEvent(Event event) {
        String message = event.getMessage();
        System.out.println("From: " + event.getFrom() + "\nMessage: " + message);
        notifyObservers(ViewEvents.MESSAGE_RECEIVED);
    }

    private void logoutEvent(Event event) {
        String name = event.getName();
        System.out.println(name + " disconnect");
        notifyObservers(ViewEvents.USER_DISCONNECT);
    }

    private void loginEvent(Event event) {
        String name = event.getName();
        System.out.println(name + " connect");
        notifyObservers(ViewEvents.USER_CONNECT);
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
