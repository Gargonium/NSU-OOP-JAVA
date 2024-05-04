package ru.nsu.votintsev.client;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8886)) {
            Client client = new Client(socket);
            client.run();
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}