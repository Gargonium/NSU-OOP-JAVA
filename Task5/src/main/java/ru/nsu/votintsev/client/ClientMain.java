package ru.nsu.votintsev.client;

import ru.nsu.votintsev.client.view.ClientView;

import javax.swing.*;
import java.io.IOException;

public class ClientMain {

//    private final static String host = "localhost";
//    private final static int port = 8886;

    private final static String host = "192.168.31.192";
    private final static int port = 8080;

    public static void main() {
        try {
            ClientController clientController = new ClientController();
            Client client = new Client(clientController);
            client.connect(host, port);
            client.run();
            SwingUtilities.invokeLater(() -> new ClientView(clientController, client.getSocket()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
