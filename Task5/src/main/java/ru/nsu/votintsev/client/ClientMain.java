package ru.nsu.votintsev.client;

import ru.nsu.votintsev.client.view.ClientView;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main() {
        try (Socket socket = new Socket("localhost", 8886)) {
            new Client(socket).run();
            SwingUtilities.invokeLater(ClientView::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
