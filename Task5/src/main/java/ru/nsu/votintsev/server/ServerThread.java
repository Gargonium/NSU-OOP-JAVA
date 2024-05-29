package ru.nsu.votintsev.server;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.xmlclasses.Error;
import ru.nsu.votintsev.xmlclasses.*;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

class ServerThread extends Thread {
    private final Socket clientSocket;

    private final FileExchanger fileExchanger;
    private final XMLParser xmlParser;
    private final ServerSender serverSender;

    private final DataOutputStream out;
    private final DataInputStream in;

    private String username;
    private int passwordHash;

    private final Map<String, Integer> usersDataBase;
    private final List<String> connectedUsers;
    private final Queue<String> lastMessages;

    private final FileWriter logFileWriter;
    private final AtomicInteger fileId;

    private List<String> fileNames;
    private List<String> mimeTypes;
    private List<String> encodings;
    private List<byte[]> files;

    public ServerThread(Socket clientSocket, FileExchanger fileExchanger, XMLParser xmlParser, ServerSender serverSender, Map<String, Integer> usersDataBase, List<String> connectedUsers, Queue<String> lastMessages, FileWriter logFileWriter, AtomicInteger fileId) throws IOException {
        this.clientSocket = clientSocket;
        this.fileExchanger = fileExchanger;
        this.xmlParser = xmlParser;
        this.serverSender = serverSender;
        this.usersDataBase = usersDataBase;
        this.connectedUsers = connectedUsers;
        this.lastMessages = lastMessages;
        this.logFileWriter = logFileWriter;
        this.fileId = fileId;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setListsForFiles(List<String> fileNames, List<String> mimeTypes, List<String> encodings, List<byte[]> files) {
        this.fileNames = fileNames;
        this.mimeTypes = mimeTypes;
        this.encodings = encodings;
        this.files = files;
    }

    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                try {
                    String xmlString = fileExchanger.receiveFile(in);
                    Command command = (Command) xmlParser.parseFromXML(Command.class, xmlString);
                    switch (command.getCommand()) {
                        case "login" -> loginCommand(command);
                        case "list" -> listCommand();
                        case "message" -> messageCommand(command);
                        case "logout" -> logoutCommand();
                        case "download" -> downloadCommand(command);
                        case "upload" -> uploadCommand(command);
                    }
                } catch (JAXBException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            connectedUsers.remove(username);
            System.out.println(e.getMessage());
            try { logFileWriter.append("Server Thread got Error ").append(e.getMessage()).append("\n"); logFileWriter.flush(); } catch (IOException ex) { throw new RuntimeException(ex); }
            if (!clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void loginCommand(Command command) throws IOException, JAXBException {
        username = command.getName();
        passwordHash = command.getPassword().hashCode();

        if (usersDataBase.containsKey(username)) {
            if (passwordHash == usersDataBase.get(username)) {
                logFileWriter.append("Client login successful: ").append(username).append("\n");
                logFileWriter.flush();
                successSendEmpty();
                sendLastMessages();
            }
            else {
                errorSend("Wrong password");
                logFileWriter.append("Client login failed: ").append(username).append(" (Wrong Password)\n");
                logFileWriter.flush();
                return;
            }
        }
        else {
            if ((username.equals("Server")) || (username.isEmpty()) || (username.equals("null"))) {
                errorSend("Invalid name");
                logFileWriter.append("Client login failed: ").append(username).append(" (Invalid name)\n");
                logFileWriter.flush();
                return;
            }
            else {
                usersDataBase.put(username, passwordHash);
                connectedUsers.add(username);
                logFileWriter.append("Client login successful: ").append(username).append("\n");
                logFileWriter.flush();
                successSendEmpty();
                sendLastMessages();
            }
        }

        Event event = new Event();
        event.setEvent("userlogin");
        event.setName(username);
        serverSender.sendToAll(xmlParser.parseToXML(event));
    }

    private void listCommand() throws JAXBException, IOException {
        try {
            List<User> userList = connectedUsers.stream().map(name -> {
                User user = new User();
                user.setName(name);
                return user;
            }).toList();

            logFileWriter.append("List of users for ").append(username).append("\n");
            logFileWriter.flush();

            Success success = new Success();
            Users users = new Users();
            users.setUsers(userList);
            success.setUsers(users);

            fileExchanger.sendFile(out, xmlParser.parseToXML(success));
        }
        catch (Exception e) {
            logFileWriter.append("Error in giving list of users ").append(e.getMessage()).append("\n");
            logFileWriter.flush();
            errorSend(e.getMessage());
        }
    }

    private void messageCommand(Command command) throws JAXBException, IOException {
        String message = command.getMessage();
        logFileWriter.append("Got message ").append(message).append(" From ").append(username).append("\n");
        logFileWriter.flush();
        try {
            Event event = new Event();
            event.setEvent("message");
            event.setMessage(message);
            event.setFrom(username);

            serverSender.sendToAll(xmlParser.parseToXML(event));
        } catch (IOException | JAXBException e) {
            logFileWriter.append("Error in giving message ").append(e.getMessage()).append("\n");
            logFileWriter.flush();
            errorSend(e.getMessage());
        }
        successSendEmpty();
    }

    private void successSendEmpty() throws JAXBException, IOException {
        Success success = new Success();
        fileExchanger.sendFile(out, xmlParser.parseToXML(success));
    }

    private void errorSend(String reason) throws JAXBException, IOException {
        Error error = new Error();
        error.setMessage(reason);
        fileExchanger.sendFile(out, xmlParser.parseToXML(error));
    }

    private void logoutCommand() throws JAXBException, IOException {
        Event event = new Event();
        event.setEvent("userlogout");
        event.setName(username);
        logFileWriter.append("User logout: ").append(username).append("\n");
        logFileWriter.flush();
        serverSender.sendToAll(xmlParser.parseToXML(event));
        successSendEmpty();
        connectedUsers.remove(username);
        Thread.currentThread().interrupt();
    }

    private void sendLastMessages() throws IOException {
        for (String xmlString : lastMessages) {
            fileExchanger.sendFile(out, xmlString);
        }
        logFileWriter.append("Send last messages\n");
        logFileWriter.flush();
    }

    private void uploadCommand(Command command) throws JAXBException, IOException {
        try {

            long size = command.getContent().length;

            if (size == 0) {
                errorSend("File is empty");
                return;
            }
            else if (size >= 10*1024*1024) { // If file is more than 10 Mb
                errorSend("File is too big");
                return;
            }

            fileNames.add(command.getName());
            mimeTypes.add(command.getMimeType());
            encodings.add(command.getEncoding());
            files.add(command.getContent());

            int currentFileId = fileId.getAndIncrement();

            Success success = new Success();
            success.setId(currentFileId);
            fileExchanger.sendFile(out, xmlParser.parseToXML(success));

            Event event = new Event();
            event.setEvent("file");
            event.setId(currentFileId);
            event.setFrom(username);
            event.setName(fileNames.get(currentFileId));
            event.setSize(files.get(currentFileId).length);
            event.setMimeType(mimeTypes.get(currentFileId));
            serverSender.sendToAll(xmlParser.parseToXML(event));

            logFileWriter.append("Received file: ").append(fileNames.get(currentFileId)).append("\n");
            logFileWriter.flush();
        }
        catch (Exception e) {
            logFileWriter.append("Error upload file by: ").append(username).append("\n");
            logFileWriter.flush();
            errorSend(e.getMessage());
        }
    }

    private void downloadCommand(Command command) throws JAXBException, IOException {
        try {
            int requiredFileId = command.getId();

            Success success = new Success();
            success.setId(requiredFileId);
            success.setName(fileNames.get(requiredFileId));
            success.setMimeType(mimeTypes.get(requiredFileId));
            success.setEncoding(encodings.get(requiredFileId));
            success.setContent(files.get(requiredFileId));

            fileExchanger.sendFile(out, xmlParser.parseToXML(success));

            logFileWriter.append("Send file: ").append(fileNames.get(requiredFileId)).append("\n");
            logFileWriter.flush();
        }
        catch (Exception e) {
            logFileWriter.append("Error download file by ").append(username).append("\n");
            logFileWriter.flush();
            errorSend(e.getMessage());
        }
    }
}
