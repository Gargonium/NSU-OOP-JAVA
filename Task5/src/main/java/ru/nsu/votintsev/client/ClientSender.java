package ru.nsu.votintsev.client;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.xmlclasses.Command;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSender {
    private final XMLParser xmlParser;
    private final FileExchanger fileExchanger;
    private final DataOutputStream outputStream;

    public ClientSender(Socket socket, XMLParser xmlParser, FileExchanger fileExchanger) throws IOException {
        this.xmlParser = xmlParser;
        this.fileExchanger = fileExchanger;
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendLoginCommand(String username, String password) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("login");
        command.setUserName(username);
        command.setUserPassword(password);
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }

    public void sendLogoutCommand() throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("logout");
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }

    public void sendListCommand() throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("list");
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }

    public void sendMessageCommand(String message) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("message");
        command.setMessage(message);
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }
}
