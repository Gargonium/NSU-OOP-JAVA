package ru.nsu.votintsev.server;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMain {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        List<Socket> clientSockets = new ArrayList<>();

        FileExchanger fileExchanger = new FileExchanger();
        XMLParser xmlParser = new XMLParser();
        ServerSender serverSender = new ServerSender(clientSockets, fileExchanger);

        Map<String, Integer> usersDataBase = new HashMap<>();

        List<String> connectedUsers = new ArrayList<>();

        System.out.println("Port to connect: " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                ServerThread serverThread = null;
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientSockets.add(clientSocket);
                    serverThread = new ServerThread(clientSocket, fileExchanger, xmlParser, serverSender, usersDataBase, connectedUsers);
                    serverThread.start();
                } catch (IOException e) {
                    serverThread.interrupt();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}