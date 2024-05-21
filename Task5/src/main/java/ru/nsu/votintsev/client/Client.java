package ru.nsu.votintsev.client;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final ClientSender clientSender;
    private final ClientReceiver clientReceiver;

    public Client(Socket socket, ClientController clientController) throws IOException {
        XMLParser xmlParser = new XMLParser();
        FileExchanger fileExchanger = new FileExchanger();
        clientSender = new ClientSender(socket, xmlParser, fileExchanger);
        clientReceiver = new ClientReceiver(socket, xmlParser, fileExchanger);

        clientReceiver.addObserver(clientController);
        clientController.setClientReceiver(clientReceiver);
    }

    public void run() {
        new Thread(clientSender).start();
        new Thread(clientReceiver).start();
    }
}
