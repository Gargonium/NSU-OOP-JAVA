package ru.nsu.votintsev.client;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final ClientSender clientSender;
    private final ClientReceiver clientReceiver;
    private ClientController clientController = new ClientController();

    public Client(Socket socket) throws IOException {
        XMLParser xmlParser = new XMLParser();
        FileExchanger fileExchanger = new FileExchanger();
        clientSender = new ClientSender(socket, xmlParser, fileExchanger);
        clientReceiver = new ClientReceiver(socket, xmlParser, fileExchanger);

        clientReceiver.addObserver(clientController);
    }

    public void run() {
        new Thread(clientSender).start();
        new Thread(clientReceiver).start();
    }
}
