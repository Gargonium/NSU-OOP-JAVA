package ru.nsu.votintsev.client;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSender implements Runnable {
    private final Socket socket;
    private final XMLParser xmlParser;
    private final FileExchanger fileExchanger;
    private final DataOutputStream outputStream;

    public ClientSender(Socket socket, XMLParser xmlParser, FileExchanger fileExchanger) throws IOException {
        this.socket = socket;
        this.xmlParser = xmlParser;
        this.fileExchanger = fileExchanger;
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {

        }
    }
}
