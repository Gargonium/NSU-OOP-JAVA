package ru.nsu.votintsev.server;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.xmlclasses.Error;
import ru.nsu.votintsev.xmlclasses.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

class ServerThread extends Thread {
    private final Socket clientSocket;

    private final FileExchanger fileExchanger;
    private final XMLParser xmlParser;
    private final ServerSender serverSender;

    private final DataOutputStream out;
    private final DataInputStream in;

    private String userName;
    private int passwordHash;

    private final Map<String, Integer> usersDataBase;

    public ServerThread(Socket clientSocket, FileExchanger fileExchanger, XMLParser xmlParser, ServerSender serverSender, Map<String, Integer> usersDataBase) {
        this.clientSocket = clientSocket;
        this.fileExchanger = fileExchanger;
        this.xmlParser = xmlParser;
        this.serverSender = serverSender;
        this.usersDataBase = usersDataBase;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        File file = new File("src\\main\\resources\\ServerProtocol" + Thread.currentThread().threadId() + ".xml");
        try {
            while (clientSocket.isConnected()) {
                try {
                    fileExchanger.receiveFile(in, file);
                    Command command = (Command) xmlParser.parseFromXML(Command.class, file);
                    switch (command.getCommand()) {
                        case "login" -> loginCommand(command, file);
                        case "list" -> listCommand(file);
                        case "message" -> messageCommand(command, file);
                        case "logout" -> logoutCommand(file);
                    }
                } catch (JAXBException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Client Disconnect");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        file.delete();
    }

    private void loginCommand(Command command, File file) throws IOException, JAXBException {
        userName = command.getUserName();
        passwordHash = command.getUserPassword().hashCode();

        if (usersDataBase.containsKey(userName)) {
            if (passwordHash == usersDataBase.get(userName))
                successSendEmpty(file);
            else {
                errorSend("Wrong password", file);
            }
        }
        else {
            if ((userName.equals("Server")) || (userName.isEmpty()) || (userName.equals("null")))
                errorSend("Invalid name", file);
            else {
                usersDataBase.put(userName, passwordHash);
                successSendEmpty(file);
            }
        }

        Event event = new Event();
        event.setEvent("userlogin");
        event.setName(userName);
        xmlParser.parseToXML(event, file);
        serverSender.sendToAll(file);
    }

    private void listCommand(File file) throws JAXBException, IOException {
        try {
            List<User> userList = usersDataBase.keySet().stream().map(name -> {
                User user = new User();
                user.setName(name);
                return user;
            }).toList();

            Success success = new Success();
            Users users = new Users();
            users.setUsers(userList);
            success.setUsers(users);

            xmlParser.parseToXML(success, file);
            fileExchanger.sendFile(out, file);
        }
        catch (Exception e) {
            errorSend(e.getMessage(), file);
        }
    }

    private void messageCommand(Command command, File file) throws JAXBException, IOException {
        String message = command.getMessage();
        try {
            Event event = new Event();
            event.setEvent("message");
            event.setMessage(message);
            event.setFrom(userName);
            xmlParser.parseToXML(event, file);
            serverSender.sendToAll(file);
        } catch (IOException | JAXBException e) {
            errorSend(e.getMessage(), file);
        }
        successSendEmpty(file);
    }

    private void successSendEmpty(File file) throws JAXBException, IOException {
        Success success = new Success();
        xmlParser.parseToXML(success, file);
        fileExchanger.sendFile(out, file);
    }

    private void errorSend(String reason, File file) throws JAXBException, IOException {
        Error error = new Error();
        error.setMessage(reason);
        xmlParser.parseToXML(error, file);
        fileExchanger.sendFile(out, file);
    }

    private void logoutCommand(File file) throws JAXBException, IOException {
        Event event = new Event();
        event.setEvent("userlogout");
        event.setName(userName);
        xmlParser.parseToXML(event, file);
        serverSender.sendToAll(file);
        successSendEmpty(file);
    }
}
