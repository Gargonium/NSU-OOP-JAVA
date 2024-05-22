package ru.nsu.votintsev.client.view;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;
import ru.nsu.votintsev.client.ClientController;
import ru.nsu.votintsev.client.Observer;
import ru.nsu.votintsev.xmlclasses.User;
import ru.nsu.votintsev.xmlclasses.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ClientView extends JFrame implements Observer {
    private static final String LOGIN_PAGE = "login";
    private static final String CHAT_PAGE = "chat";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLoginLabel;

    private JButton sendFileButton;
    private JButton sendButton;
    private JButton userListButton;
    private JTextField messageField;

    private JPanel messagePanel;
    private JScrollPane scrollPane;

    private final ClientController clientController;
    private final ViewController viewController;

    private Users userList;

    public ClientView(ClientController clientController, Socket socket) {
        this.clientController = clientController;
        clientController.addObserver(this);
        setSize(600,400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(new SubstanceGraphiteAquaLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        initComponents();

        viewController = new ViewController(usernameField, passwordField, loginButton, sendFileButton, sendButton, userListButton, messageField);
        viewController.setView(this);
        viewController.setClientSender(clientController.getCLientSender());
        viewController.setSocket(socket);

        showLoginPanel();
        setVisible(true);
    }

    public void setErrorLoginLabel(String errorMessage) {
        errorLoginLabel.setText(errorMessage);
    }

    @Override
    public void update(ViewEvents change) {
        switch (change) {
            case MESSAGE_RECEIVED -> SwingUtilities.invokeLater(() -> displayMessages(clientController.getMessages()));
            case USER_LIST_RECEIVED -> {
                userList = clientController.getUserList();
                SwingUtilities.invokeLater(this::displayUserList);
            }
            case SUCCESS -> viewController.successReceived();
            case LOGIN_ERROR -> viewController.loginErrorReceived();
        }
    }

    public void showChatPanel() {
        setTitle("Chat");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), CHAT_PAGE);
    }

    private void showLoginPanel() {
        setTitle("Login");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), LOGIN_PAGE);
    }

    private void displayUserList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : userList.getUsers()) {
            stringBuilder.append(user.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(ClientView.this,
                stringBuilder.toString(), "Users", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayMessages(Map<String, MessageType> messages) {
        Set<String> keys = messages.keySet();
        for (String message : keys) {
            JTextArea messageArea = new JTextArea(message);
            messageArea.setLineWrap(true);
            messageArea.setWrapStyleWord(true);
            messageArea.setEditable(false);
            messageArea.setBackground(messagePanel.getBackground());
            messageArea.setForeground(messages.get(message).equals(MessageType.USER_MESSAGE) ? Color.BLACK : Color.RED);
            messageArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            messagePanel.add(messageArea, 0);
            messagePanel.add(Box.createVerticalStrut(5));
            messagePanel.revalidate();
            messagePanel.repaint();

            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum() + messageArea.getBounds().height));
        }
    }

    private void displayFile(File file, String sender) {
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());

        Icon blackDownloadArrowIcon = new ImageIcon(Objects.requireNonNull(Object.class.getResource("blackDownloadArrow.png")));
        Icon greenDownloadArrowIcon = new ImageIcon(Objects.requireNonNull(Object.class.getResource("greenDownloadArrow.png")));

        JLabel iconLabel = new JLabel(blackDownloadArrowIcon);
        iconLabel.setPreferredSize(new Dimension(50, 50));
        filePanel.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("File: " + file.getName());
        JLabel sizeLabel = new JLabel("Size: " + file.length() / 1024 + " KB");
        JLabel senderLabel = new JLabel("Sent by: " + sender);

        textPanel.add(nameLabel);
        textPanel.add(sizeLabel);
        textPanel.add(senderLabel);

        filePanel.add(textPanel, BorderLayout.CENTER);

        // Добавляем слушатель событий для наведения мыши
        filePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                iconLabel.setIcon(greenDownloadArrowIcon);
                filePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                iconLabel.setIcon(blackDownloadArrowIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO: Переделать. Нужно запрашивать файлы у сервера
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(file.getName()));
                int returnValue = fileChooser.showSaveDialog(ClientView.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File destinationFile = fileChooser.getSelectedFile();
                    try {
                        Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(ClientView.this, "File saved successfully.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ClientView.this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        messagePanel.add(filePanel, 0);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.revalidate();

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        errorLoginLabel = new JLabel();
        errorLoginLabel.setForeground(Color.RED);

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
        loginPanel.add(errorLoginLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        loginPanel.add(loginButton, gbc);

        getContentPane().setLayout(new CardLayout());
        getContentPane().add(loginPanel, LOGIN_PAGE);
        getContentPane().add(chatPanel, CHAT_PAGE);
    }
}