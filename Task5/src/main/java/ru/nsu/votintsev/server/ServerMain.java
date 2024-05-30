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

    private static final List<String> fileNames = new ArrayList<>();

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        List<ServerThread> serverThreads = new ArrayList<>();
        Queue<String> lastMessages = new LinkedList<>();

        FileExchanger fileExchanger = new FileExchanger();
        XMLParser xmlParser = new XMLParser();
        ServerSender serverSender = new ServerSender(serverThreads, fileExchanger, lastMessages);

        Map<String, Integer> usersDataBase = new HashMap<>();

        List<String> connectedUsers = new ArrayList<>();

        FileWriter logFileWriter = null;

        AtomicInteger fileId = new AtomicInteger(0);

        List<String> mimeTypes = new ArrayList<>();
        List<String> encodings = new ArrayList<>();

        System.out.println("Port to connect: " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            logFileWriter = new FileWriter("ServerLog.txt");
            logFileWriter.append("Server online\n");
            logFileWriter.flush();

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ServerThread serverThread = new ServerThread(clientSocket, fileExchanger, xmlParser, serverSender, usersDataBase, connectedUsers, lastMessages, logFileWriter, fileId);
                    serverThreads.add(serverThread);
                    serverThread.setListsForFiles(fileNames, mimeTypes, encodings);
                    serverThread.start();
                }
                catch (IOException e) {
                    System.out.println("Error accepting client connection");
                }
            }
        } catch (IOException e) {

            deleteFiles();

            try {
                assert logFileWriter != null;
                logFileWriter.append("Server crashed ").append(e.getMessage()).append("\n");
                logFileWriter.flush();
                logFileWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void deleteFiles() {
        for (int i = 0; i < fileNames.size(); i++) {
            File file = new File(i + "");
            file.delete();
        }
    }
}