package ru.nsu.votintsev.client;

import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.client.view.MessageType;
import ru.nsu.votintsev.client.view.ViewEvents;
import ru.nsu.votintsev.xmlclasses.Error;
import ru.nsu.votintsev.xmlclasses.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientReceiver implements Runnable, Observable {
    private final Socket socket;
    private final XMLParser xmlParser;
    private final FileExchanger fileExchanger;
    private final DataInputStream inputStream;

    private final ClientClasses clientClasses = new ClientClasses();
    private Observer observer;
    private Client client;

    private final Map<String, MessageType> messageBuffer = new HashMap<>();
    private Users users;

    private boolean isLogin = false;

    private byte[] receivedFile;
    private String fileSender;
    private Integer fileId;
    private String fileName;
    private long fileSize;

    public ClientReceiver(Socket socket, XMLParser xmlParser, FileExchanger fileExchanger, DataInputStream inputStream) throws IOException {
        this.socket = socket;
        this.xmlParser = xmlParser;
        this.fileExchanger = fileExchanger;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            System.out.println("CS " + socket.isClosed());
            try {
                String xmlString = fileExchanger.receiveFile(inputStream);
                switch (clientClasses.parseFromXML(xmlParser, xmlString)) {
                    case Event event -> {
                        if (isLogin)
                            switch (event.getEvent()) {
                                case "message" -> messageEvent(event);
                                case "userlogin" -> userLoginEvent(event);
                                case "userlogout" -> userLogoutEvent(event);
                                case "file" -> fileEvent(event);
                                default -> System.out.println("Unknown event: " + event.getEvent());
                            }
                    }
                    case Success success -> {
                        notifyObservers(ViewEvents.SUCCESS);
                        if (!isLogin)
                            isLogin = true;
                        if (success.getUsers() != null)
                            userListReceived(success);
                        else if (success.getContent() != null)
                            fileReceived(success);
                    }
                    case Error error -> {
                        if (Objects.equals(error.getMessage(), "Wrong password") || Objects.equals(error.getMessage(), "Invalid name"))
                            notifyObservers(ViewEvents.LOGIN_ERROR);
                        System.out.printf(error.getMessage());
                    }
                    default -> System.out.println("Unknown xmlBlock");
                }
            } catch (IOException e) {
                System.out.println("CS " + socket.isClosed());
                client.disconnect();
            }
        }
    }

    public Map<String, MessageType> getMessageBuffer() {
        Map<String, MessageType> temp = new HashMap<>(messageBuffer);
        messageBuffer.clear();
        return temp;
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void notifyObservers(ViewEvents change) {
        observer.update(change);
    }

    private void messageEvent(Event event) {
        messageBuffer.put("<" + event.getFrom() + ">: " + event.getMessage(), MessageType.USER_MESSAGE);
        notifyObservers(ViewEvents.MESSAGE_RECEIVED);
    }

    private void userLoginEvent(Event event) {
        messageBuffer.put(event.getName() + " connect", MessageType.SERVER_MESSAGE);
        notifyObservers(ViewEvents.MESSAGE_RECEIVED);
    }

    private void userLogoutEvent(Event event) {
        messageBuffer.put(event.getName() + " disconnect", MessageType.SERVER_MESSAGE);
        notifyObservers(ViewEvents.MESSAGE_RECEIVED);
    }

    private void userListReceived(Success success) {
        users = success.getUsers();
        notifyObservers(ViewEvents.USER_LIST_RECEIVED);
    }

    private void fileEvent(Event event) {
        fileId = event.getId();
        fileSender = event.getFrom();
        fileName = event.getName();
        fileSize = event.getSize();
        notifyObservers(ViewEvents.SOMEONE_SEND_FILE);
    }

    private void fileReceived(Success success) {
        fileId = success.getId();
        fileName = success.getName();
        receivedFile = success.getContent();
        notifyObservers(ViewEvents.FILE_RECEIVED);
    }

    public byte[] getReceivedFile() {
        return receivedFile;
    }

    public String getFileSender() {
        return fileSender;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}