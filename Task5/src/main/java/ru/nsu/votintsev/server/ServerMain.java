package ru.nsu.votintsev.server;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerMain {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        List<Socket> clientSockets = new ArrayList<>();
        Queue<String> lastMessages = new LinkedList<>();

        FileExchanger fileExchanger = new FileExchanger();
        XMLParser xmlParser = new XMLParser();
        ServerSender serverSender = new ServerSender(clientSockets, fileExchanger, lastMessages);

        Map<String, Integer> usersDataBase = new HashMap<>();

        List<String> connectedUsers = new ArrayList<>();

        FileWriter logFileWriter = null;

        AtomicInteger fileId = new AtomicInteger(0);

        List<String> fileNames = new ArrayList<>();
        List<String> mimeTypes = new ArrayList<>();
        List<String> encodings = new ArrayList<>();
        List<File> files = new ArrayList<>();

        System.out.println("Port to connect: " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            logFileWriter = new FileWriter("ServerLog.txt");
            logFileWriter.append("Server online\n");
            logFileWriter.flush();

            while (true) {
                ServerThread serverThread = null;
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientSockets.add(clientSocket);
                    serverThread = new ServerThread(clientSocket, fileExchanger, xmlParser, serverSender, usersDataBase, connectedUsers, lastMessages, logFileWriter, fileId);
                    serverThread.setListsForFiles(fileNames, mimeTypes, encodings, files);
                    serverThread.start();
                } catch (IOException e) {
                    serverThread.interrupt();
                }
            }
        } catch (IOException e) {
            try {
                assert logFileWriter != null;
                logFileWriter.append("Server crashed ").append(e.getMessage()).append("\n");
                logFileWriter.flush();
                logFileWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        }
    }
}