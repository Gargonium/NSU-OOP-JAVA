package ru.nsu.votintsev.server;

import ru.nsu.votintsev.FileExchanger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class ServerSender {
    private final List<ServerThread> clientSockets;
    private final FileExchanger fileExchanger;
    private final Queue<String> lastMessages;

    public ServerSender(List<ServerThread> clientSockets, FileExchanger fileExchanger, Queue<String> lastMessages) {
        this.clientSockets = clientSockets;
        this.fileExchanger = fileExchanger;
        this.lastMessages = lastMessages;
    }

    public void sendToAll(String file, String sender) throws IOException {
        synchronized (clientSockets) {

            if (lastMessages.size() == 10)
                lastMessages.remove();

            lastMessages.add(file);

            for (ServerThread client : clientSockets) {

                if (client.getMutedUsers().contains(sender)) {
                    continue;
                }

                DataOutputStream out = client.getClientOutputStream();
                fileExchanger.sendFile(out, file);
            }
        }
    }
}
