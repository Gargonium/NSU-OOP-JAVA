package ru.nsu.votintsev.client.view;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class ClientView extends JFrame {
    private static final String LOGIN_PAGE = "login";
    private static final String CHAT_PAGE = "chat";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private JButton sendFileButton;
    private JButton sendButton;
    private JButton userListButton;
    private JTextField messageField;

    private JPanel messagePanel;

    private JScrollPane scrollPane;

    enum MessageType {
        SERVER_MESSAGE,
        USER_MESSAGE
    }

    public ClientView() {
        setSize(600,400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(new SubstanceGraphiteAquaLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        initComponents();
        addActionListeners();
        showLoginPanel();
        setVisible(true);
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        messageField = new JTextField(30);
        sendFileButton = new JButton("Send File");
        sendButton = new JButton("Send");
        userListButton = new JButton("User List");

        loginButton.setFocusable(false);
        sendFileButton.setFocusable(false);
        sendButton.setFocusable(false);
        userListButton.setFocusable(false);

        messagePanel = new JPanel();
        messagePanel.setLayout(new ReverseBoxLayout());

        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel messageControlPanel = new JPanel();
        messageControlPanel.add(sendFileButton);
        messageControlPanel.add(userListButton);
        messageControlPanel.add(messageField);
        messageControlPanel.add(sendButton);

        chatPanel.add(messageControlPanel, BorderLayout.SOUTH);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(loginButton, gbc);

        getContentPane().setLayout(new CardLayout());
        getContentPane().add(loginPanel, LOGIN_PAGE);
        getContentPane().add(chatPanel, CHAT_PAGE);
    }

    // TODO: Вызывать, когда пришло новое сообщение
    private void displayMessage(String message, MessageType messageType) {
        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(messageType.equals(MessageType.USER_MESSAGE) ? Color.BLACK : Color.RED);
        messagePanel.add(messageLabel, 0);
        messagePanel.revalidate();
        messagePanel.repaint();

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }

    private void sendMessage() {
        String message = messageField.getText();
        // TODO: Отправить сообщение на сервер
        displayMessage(message, MessageType.USER_MESSAGE);
        messageField.setText("");
    }

    private boolean authenticate(String userName, String password) {
        // TODO: Реализовать это
        return userName.equals("admin") && password.equals("admin");
    }

    private void login() {
        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());
        boolean isAuthenticated = authenticate(userName, password);
        if (isAuthenticated) {
            showChatPanel();
            // TODO: Отправить join команду
        }
    }

    private void showLoginPanel() {
        setTitle("Login");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), LOGIN_PAGE);
    }

    private void showChatPanel() {
        setTitle("Chat");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), CHAT_PAGE);
    }

    private void addActionListeners() {
        loginButton.addActionListener(_ -> login());

        sendFileButton.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(ClientView.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // TODO: Отправить файл серверу
            }
        });

        sendButton.addActionListener(_ -> sendMessage());

        userListButton.addActionListener(_ -> {
            // TODO: Вызвать список пользователей
            JOptionPane.showMessageDialog(ClientView.this,
                    "User List:\nUser 1\nUser 2\nUser 3", "User List", JOptionPane.INFORMATION_MESSAGE);
        });

        KeyListener loginKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        };

        usernameField.addKeyListener(loginKeyAdapter);
        passwordField.addKeyListener(loginKeyAdapter);

        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
    }

    public static void main() {
        SwingUtilities.invokeLater(ClientView::new);
    }
}
//никита лох
