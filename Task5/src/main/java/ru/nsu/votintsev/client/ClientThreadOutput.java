package ru.nsu.votintsev.client;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.xmlclasses.ClientClasses;
import ru.nsu.votintsev.xmlclasses.Error;
import ru.nsu.votintsev.xmlclasses.Event;
import ru.nsu.votintsev.xmlclasses.Success;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class ClientThreadOutput implements Runnable {
    private final FileExchanger fileExchanger;
    private final DataInputStream in;
    private final XMLParser xmlParser;
    private final File file;
    private final ClientClasses parser = new ClientClasses();

    public ClientThreadOutput(FileExchanger fileExchanger, DataInputStream in, XMLParser xmlParser, File file) {
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
                case Success success -> {}
                default -> throw new IllegalStateException("Unexpected value" );
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void messageEvent(Event event) {
        String message = event.getMessage();
        System.out.println("From: " + event.getFrom() + "\nMessage: " + message);
    }

    private void logoutEvent(Event event) {
        String name = event.getName();
        System.out.println(name + " disconnect");
    }

    private void loginEvent(Event event) {
        String name = event.getName();
        System.out.println(name + " connect");
    }
}
