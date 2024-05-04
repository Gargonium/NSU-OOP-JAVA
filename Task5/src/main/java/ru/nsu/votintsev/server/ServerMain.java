package ru.nsu.votintsev.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    private final static int port = 8886;

    public static void main(String[] args) {

        System.out.println("Port to connect: " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ServerThread(clientSocket).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}