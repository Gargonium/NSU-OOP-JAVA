package ru.nsu.votintsev.server;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.*;
import ru.nsu.votintsev.client.xmlclasses.Event;
import ru.nsu.votintsev.server.xmlclasses.Command;

import java.io.*;
import java.net.Socket;

class ServerThread extends Thread {
    private final Socket clientSocket;

    private final FileExchanger fileExchanger = new FileExchanger();
    private final XMLParser xmlParser = new XMLParser();

    private final File file = new File("src\\main\\resources\\ServerProtocol.xml");

    private final DataOutputStream out;
    private final DataInputStream in;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            while (clientSocket.isConnected()) { // Сервер должен просто ждать прихода сообщения
                try {
                    fileExchanger.receiveFile(in, file);
                    Command command = (Command) xmlParser.parseFromXML(Command.class, file);
                    switch (command.getCommand()) {
                        case "login" -> loginCommand();
                        case "list" -> listCommand();
                        case "message" -> messageCommand(command);
                    }
                } catch (JAXBException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Client Disconnect");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loginCommand() {
        /// Нужен список пользователей
    }

    public void listCommand() {
        /// Тут как-то совсем сложно
    }

    public void messageCommand(Command command) throws JAXBException, IOException {
        String message = command.getMessage();
        Event event = new Event();
        event.setEvent("message");
        event.setMessage(message);
        event.setFrom("UserName"); ////////////////////////////////////////////// Нужно знать от кого это сообщение
        xmlParser.parseToXML(event, file);
        fileExchanger.sendFile(out, file); //////////////////////////// Нужно отправлять всем пользователям
        //////////////// Нужно как-то отправлять success и error
    }

    public void successEmpty() {

    }

    public void error(String reason) {

    }
}
