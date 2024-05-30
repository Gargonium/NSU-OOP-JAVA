package ru.nsu.votintsev.client;

import ru.nsu.votintsev.client.view.ClientView;

import javax.swing.*;
import java.io.IOException;

public class ClientMain {

    public static void main() {
        try {
            ClientController clientController = new ClientController();
            Client client = new Client(clientController);
            SwingUtilities.invokeLater(() -> new ClientView(clientController, client.getSocket(), client));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
