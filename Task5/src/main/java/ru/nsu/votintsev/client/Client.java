package ru.nsu.votintsev.client;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;

    private final ClientController clientController;
    private final XMLParser xmlParser;
    private final FileExchanger fileExchanger;
    private Socket socket;

    private DataOutputStream out;
    private DataInputStream in;

    public Client(ClientController clientController) throws IOException {
        xmlParser = new XMLParser();
        fileExchanger = new FileExchanger();
        this.clientController = clientController;
    }

    public void run() throws IOException {
        clientSender = new ClientSender(xmlParser, fileExchanger, out);
        clientReceiver = new ClientReceiver(socket, xmlParser, fileExchanger, in);

        clientReceiver.addObserver(clientController);
        clientController.setClientReceiver(clientReceiver);
        clientController.setClientSender(clientSender);

        clientReceiver.setClient(this);

        new Thread(clientReceiver).start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
