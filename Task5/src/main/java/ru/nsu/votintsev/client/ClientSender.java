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
    private DataOutputStream outputStream;

    public ClientSender(XMLParser xmlParser, FileExchanger fileExchanger) {
        this.xmlParser = xmlParser;
        this.fileExchanger = fileExchanger;
    }

    public void sendLoginCommand(String username, String password) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("login");
        command.setName(username);
        command.setPassword(password);
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

    public void sendUploadCommand(String fileName, String mimeType, String encoding, byte[] content) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("upload");
        command.setName(fileName);
        command.setMimeType(mimeType);
        command.setEncoding(encoding);
        command.setContent(content);
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }

    public void sendDownloadCommand(Integer id) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("download");
        command.setId(id);
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }

    public void sendUnmuteCommand(String target) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("unmute");
        command.setName(target);
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }

    public void sendMuteCommand(String target) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("mute");
        command.setName(target);
        fileExchanger.sendFile(outputStream, xmlParser.parseToXML(command));
    }

    public void setOutputStream(DataOutputStream out) {
        this.outputStream = out;
    }
}
