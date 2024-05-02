package ru.nsu.votintsev.server;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@AllArgsConstructor
class ServerThread extends Thread {
    private Socket socket;

    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
