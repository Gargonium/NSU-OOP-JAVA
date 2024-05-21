package ru.nsu.votintsev.client.oldrealize;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.net.Socket;

public class ClientMain1 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8886)) {
            Client1 client = new Client1(socket);
            client.run();
            //SwingUtilities.invokeLater(() -> new ClientView(client));
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}