package ru.nsu.votintsev.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread extends Thread {
    private final Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                out.println(inputLine);
            }

            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
