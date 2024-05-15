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

    private final static int port = 8886;

    public static void main(String[] args) {

        List<Socket> clientSockets = new ArrayList<>();

        FileExchanger fileExchanger = new FileExchanger();
        XMLParser xmlParser = new XMLParser();
        ServerSender serverSender = new ServerSender(clientSockets, fileExchanger);

        Map<String, Integer> usersDataBase = new HashMap<>();

        System.out.println("Port to connect: " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);
                new ServerThread(clientSocket, fileExchanger, xmlParser, serverSender, usersDataBase).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}