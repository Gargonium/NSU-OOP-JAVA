package ru.nsu.votintsev.server;

import ru.nsu.votintsev.FileExchanger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerSender {
    private final List<Socket> clientSockets;
    private final FileExchanger fileExchanger;

    public ServerSender(List<Socket> clientSockets, FileExchanger fileExchanger) {
        this.clientSockets = clientSockets;
        this.fileExchanger = fileExchanger;
    }

    public void sendToAll(File file) throws IOException {
        synchronized (clientSockets) {
            for (Socket client : clientSockets) {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                fileExchanger.sendFile(out, file);
            }
        }
    }
}
