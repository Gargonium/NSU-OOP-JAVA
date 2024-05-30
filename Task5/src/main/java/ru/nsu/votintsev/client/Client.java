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

    private boolean isConnected = false;

    public Client(ClientController clientController) throws IOException {
        xmlParser = new XMLParser();
        fileExchanger = new FileExchanger();
        this.clientController = clientController;
        clientSender = new ClientSender(xmlParser, fileExchanger);
        clientReceiver = new ClientReceiver(xmlParser, fileExchanger);
        clientReceiver.addObserver(clientController);
        clientController.setClientReceiver(clientReceiver);
        clientController.setClientSender(clientSender);

        clientReceiver.setClient(this);
    }

    public void run() throws IOException {

        clientSender.setOutputStream(out);
        clientReceiver.setSocket(socket);
        clientReceiver.setInputStream(in);
        new Thread(clientReceiver).start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        isConnected = true;
        run();
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

    public boolean isConnected() {
        return isConnected;
    }
}
