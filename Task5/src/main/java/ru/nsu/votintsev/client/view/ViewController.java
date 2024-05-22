package ru.nsu.votintsev.client.view;

import ru.nsu.votintsev.client.ClientController;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class ViewController implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private JButton sendFileButton;
    private JButton sendButton;
    private JButton userListButton;
    private JTextField messageField;

    private ClientView clientView;
    private ClientController clientController;

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
                    login();
                }
            }
        };

        KeyListener messageKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
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
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton)
            login();
        else if (e.getSource() == sendFileButton)
            sendFile();
        else if (e.getSource() == sendButton)
            sendMessage();
        else if (e.getSource() == userListButton)
            requestUserList();
    }

    private void login() {
        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());
        boolean isAuthenticated = authenticate(userName, password);
        if (isAuthenticated) {
            clientView.showChatPanel();
            // TODO: Отправить join команду
        }
        else {
            clientView.setErrorLoginLabel("Invalid username or password");
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        // TODO: Отправить сообщение серверу
        messageField.setText("");
    }

    private boolean authenticate(String userName, String password) {
        // TODO: Понять как понимать саксесс или ерор?
        return userName.equals("admin") && password.equals("admin");
    }

    private void requestUserList() {
        // TODO: Отправить запрос на юзер лист
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
