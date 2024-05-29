package ru.nsu.votintsev.server;

import ru.nsu.votintsev.FileExchanger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Queue;

public class ServerSender {
    private final List<Socket> clientSockets;
    private final FileExchanger fileExchanger;
    private final Queue<String> lastMessages;

    public ServerSender(List<Socket> clientSockets, FileExchanger fileExchanger, Queue<String> lastMessages) {
        this.clientSockets = clientSockets;
        this.fileExchanger = fileExchanger;
        this.lastMessages = lastMessages;
    }

    public void sendToAll(String file) throws IOException {
        synchronized (clientSockets) {

            if (lastMessages.size() == 10)
                lastMessages.remove();

            lastMessages.add(file);

            for (Socket client : clientSockets) {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                fileExchanger.sendFile(out, file);
            }
        }
    }
}
