package ru.nsu.votintsev.client;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.client.xmlclasses.Event;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class ClientThreadOutput implements Runnable {
    private final FileExchanger fileExchanger;
    private final DataInputStream in;
    private final XMLParser xmlParser;
    private final File file;

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
            Event event = (Event) xmlParser.parseFromXML(Event.class, file);
            switch (event.getEvent()) {
                case "message" -> messageEvent(event);
            }
        } catch (IOException | JAXBException e) {
            System.out.println(e.getMessage());
        }
    }

    private void messageEvent(Event event) {
        String message = event.getMessage();
        System.out.println("From: " + event.getFrom() + "\nMessage: " + message);
    }
}
