package ru.nsu.votintsev.server;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.xmlclasses.Error;
import ru.nsu.votintsev.xmlclasses.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    private String username;
    private int passwordHash;

    private final Map<String, Integer> usersDataBase;
    private final List<String> connectedUsers;

    public ServerThread(Socket clientSocket, FileExchanger fileExchanger, XMLParser xmlParser, ServerSender serverSender, Map<String, Integer> usersDataBase, List<String> connectedUsers) {
        this.clientSocket = clientSocket;
        this.fileExchanger = fileExchanger;
        this.xmlParser = xmlParser;
        this.serverSender = serverSender;
        this.usersDataBase = usersDataBase;
        this.connectedUsers = connectedUsers;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                    }
                } catch (JAXBException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(username + " dissconnect");
        } catch (IOException e) {
            connectedUsers.remove(username);
            System.out.println(e.getMessage());
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
        username = command.getUserName();
        passwordHash = command.getUserPassword().hashCode();

        if (usersDataBase.containsKey(username)) {
            if (passwordHash == usersDataBase.get(username))
                successSendEmpty();
            else {
                errorSend("Wrong password");
                return;
            }
        }
        else {
            if ((username.equals("Server")) || (username.isEmpty()) || (username.equals("null"))) {
                errorSend("Invalid name");
                return;
            }
            else {
                usersDataBase.put(username, passwordHash);
                connectedUsers.add(username);
                successSendEmpty();
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

            Success success = new Success();
            Users users = new Users();
            users.setUsers(userList);
            success.setUsers(users);

            fileExchanger.sendFile(out, xmlParser.parseToXML(success));
        }
        catch (Exception e) {
            errorSend(e.getMessage());
        }
    }

    private void messageCommand(Command command) throws JAXBException, IOException {
        String message = command.getMessage();
        try {
            Event event = new Event();
            event.setEvent("message");
            event.setMessage(message);
            event.setFrom(username);
            serverSender.sendToAll(xmlParser.parseToXML(event));
        } catch (IOException | JAXBException e) {
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
        serverSender.sendToAll(xmlParser.parseToXML(event));
        successSendEmpty();
        connectedUsers.remove(username);
        Thread.currentThread().interrupt();
    }
}
