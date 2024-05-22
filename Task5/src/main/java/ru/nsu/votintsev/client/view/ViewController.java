package ru.nsu.votintsev.client.view;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.client.ClientSender;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ViewController implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    private final JButton sendFileButton;
    private final JButton sendButton;
    private final JButton userListButton;
    private final JTextField messageField;

    private ClientView clientView;
    private ClientSender clientSender;

    private Socket socket;

    private boolean isAuthenticated = false;

    public ViewController(JTextField usernameField, JPasswordField passwordField, JButton loginButton, JButton sendFileButton, JButton sendButton, JButton userListButton, JTextField messageField) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.loginButton = loginButton;
        this.sendFileButton = sendFileButton;
        this.sendButton = sendButton;
        this.userListButton = userListButton;
        this.messageField = messageField;

        KeyListener loginKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        login();
                    } catch (JAXBException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };

        KeyListener messageKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        sendMessage();
                    } catch (JAXBException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };

        usernameField.addKeyListener(loginKeyAdapter);
        passwordField.addKeyListener(loginKeyAdapter);

        messageField.addKeyListener(messageKeyAdapter);

        sendFileButton.addActionListener(this);
        sendButton.addActionListener(this);
        userListButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    public void setView(ClientView clientView) {
        this.clientView = clientView;
        clientView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    clientSender.sendLogoutCommand();
                    clientView.dispose();
                    socket.close();
                } catch (JAXBException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setClientSender (ClientSender clientSender) {
        this.clientSender = clientSender;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == loginButton)
                login();
            else if (e.getSource() == sendFileButton)
                sendFile();
            else if (e.getSource() == sendButton)
                sendMessage();
            else if (e.getSource() == userListButton)
                requestUserList();
        }
        catch (JAXBException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void successReceived() {
        if (!isAuthenticated) {
            clientView.showChatPanel();
            isAuthenticated = true;
        }
    }

    public void loginErrorReceived() {
        clientView.setErrorLoginLabel("Invalid username or password");
    }

    private void login() throws JAXBException, IOException {
        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());
        clientSender.sendLoginCommand(userName, password);
    }

    private void sendMessage() throws JAXBException, IOException {
        String message = messageField.getText();
        messageField.setText("");
        clientSender.sendMessageCommand(message);
    }

    private void requestUserList() throws JAXBException, IOException {
        clientSender.sendListCommand();
    }

    private void sendFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(clientView);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // TODO: Отправить файл серверу
        }
    }
}
