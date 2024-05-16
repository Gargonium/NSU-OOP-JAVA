package ru.nsu.votintsev.client;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.xmlclasses.Command;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientThreadInput implements Runnable {
    private final XMLParser xmlParser;
    private final FileExchanger fileExchanger;
    private final Scanner scanner = new Scanner(System.in);
    private final DataOutputStream out;
    private final File file;

    private String clientName;
    private String password;

    private final AtomicBoolean isRegistred = new AtomicBoolean(false);

    public ClientThreadInput(XMLParser xmlParser, FileExchanger fileExchanger, DataOutputStream out, File file) {
        this.xmlParser = xmlParser;
        this.fileExchanger = fileExchanger;
        this.out = out;
        this.file = file;
    }

    @Override
    public void run() {
        try {

            if (!isRegistred.get()) {
                System.out.print("Name: ");
                clientName = scanner.nextLine();
                System.out.print("Password: ");
                password = scanner.nextLine();
                isRegistred.set(true);
                sendName();
            }
            else {
                String message;
                if ((message = scanner.nextLine()) != null)
                    if (message.equals("/list"))
                        list(file);
                    else if (Objects.equals(message, "/logout"))
                        logout(file);
                    else
                        sendMessage(message);
            }
        } catch (IOException | JAXBException e) {
            System.out.println(e.getMessage());
            System.out.println("Scanner problem");
        }
    }

    private void sendMessage(String message) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("message");
        command.setMessage(message);
        xmlParser.parseToXML(command, file);
        fileExchanger.sendFile(out, file);
    }

    private void sendName() throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("login");
        command.setUserName(clientName);
        command.setUserPassword(password);
        xmlParser.parseToXML(command, file);
        fileExchanger.sendFile(out, file);
    }

    private void logout(File file) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("logout");
        xmlParser.parseToXML(command, file);
        fileExchanger.sendFile(out, file);
        System.exit(0);
    }

    private void list(File file) throws JAXBException, IOException {
        Command command = new Command();
        command.setCommand("list");
        xmlParser.parseToXML(command, file);
        fileExchanger.sendFile(out, file);
    }
}
