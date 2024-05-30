package ru.nsu.votintsev.client.view;

import jakarta.xml.bind.JAXBException;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;
import ru.nsu.votintsev.client.Client;
import ru.nsu.votintsev.client.ClientController;
import ru.nsu.votintsev.client.Observer;
import ru.nsu.votintsev.xmlclasses.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ClientView extends JFrame implements Observer {
    private static final String LOGIN_PAGE = "login";
    private static final String CHAT_PAGE = "chat";
    private static final String CONNECT_PAGE = "connect";

    private JTextField ipField;
    private JTextField portField;
    private JButton connectButton;
    private JLabel errorConnectLabel;

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

    private final Client client;

    private Users userList;

    public ClientView(ClientController clientController, Socket socket, Client client) {
        this.clientController = clientController;
        this.client = client;
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

        viewController = new ViewController(usernameField, passwordField, loginButton, sendFileButton, sendButton, userListButton, messageField, ipField, portField, connectButton, client);
        viewController.setView(this);
        viewController.setClientSender(clientController.getCLientSender());
        viewController.setSocket(socket);

        showConnectPanel();
        setVisible(true);
    }

    public void setErrorLoginLabel(String errorMessage) {
        errorLoginLabel.setText(errorMessage);
    }

    public void setErrorConnectLabel(String errorMessage) {
        errorConnectLabel.setText(errorMessage);
    }

    @Override
    public void update(ViewEvents change) {
        switch (change) {
            case MESSAGE_RECEIVED -> SwingUtilities.invokeLater(() -> displayMessages(clientController.getMessages()));
            case USER_LIST_RECEIVED -> {
                userList = clientController.getUserList();
                SwingUtilities.invokeLater(this::displayUserList);
            }
            case SUCCESS -> {
                try {
                    viewController.successReceived();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case LOGIN_ERROR -> viewController.loginErrorReceived();
            case FILE_RECEIVED -> SwingUtilities.invokeLater(() -> saveFile(clientController.getFile(), clientController.getFileName()));
            case SOMEONE_SEND_FILE -> SwingUtilities.invokeLater(() -> displayFile(clientController.getFileName(), clientController.getFileSize(), clientController.getFrom(), clientController.getId()));
        }
    }

    public void showChatPanel() {
        setTitle("Chat");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), CHAT_PAGE);
    }

    public void showLoginPanel() {
        setTitle("Login");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), LOGIN_PAGE);
    }

    private void showConnectPanel() {
        setTitle("Connect");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), CONNECT_PAGE);
    }

    private void displayUserList() {
        SwingUtilities.invokeLater(() -> new UserListFrame(userList, viewController));
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

    private void saveFile(byte[] bytes, String fileName) {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        file.delete();
    }

    private void displayFile(String fileName, long fileSize, String sender, Integer id) {
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());

        Icon blackDownloadArrowIcon = new ImageIcon("src/main/resources/blackDownloadArrow.png");
        Icon greenDownloadArrowIcon = new ImageIcon("src/main/resources/greenDownloadArrow.png");

        JLabel iconLabel = new JLabel(blackDownloadArrowIcon);
        iconLabel.setPreferredSize(new Dimension(50, 50));
        filePanel.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("File: " + fileName);
        JLabel sizeLabel = new JLabel("Size: " + fileSize / 1024 + " Kb");
        JLabel senderLabel = new JLabel("Sent by: " + sender);

        textPanel.add(nameLabel);
        textPanel.add(sizeLabel);
        textPanel.add(senderLabel);

        filePanel.add(textPanel, BorderLayout.CENTER);

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
                try {
                    viewController.requestFile(id);
                } catch (JAXBException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        messagePanel.add(filePanel, 0);
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.revalidate();

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }

    private void initComponents() {
        ipField = new JTextField(20);
        portField = new JTextField(20);
        connectButton = new JButton("Connect");
        errorConnectLabel = new JLabel();
        errorConnectLabel.setForeground(Color.RED);

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

        JPanel connectPanel = new JPanel();
        connectPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(10, 10, 10, 10);
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        connectPanel.add(new JLabel("Ip:"), gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        connectPanel.add(ipField, gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = 2;
        gbc1.fill = GridBagConstraints.NONE;
        connectPanel.add(new JLabel("Port:"), gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = 3;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        connectPanel.add(portField, gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = 4;
        gbc1.fill = GridBagConstraints.NONE;
        connectPanel.add(errorConnectLabel, gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = 5;
        connectPanel.add(connectButton, gbc1);

        getContentPane().setLayout(new CardLayout());
        getContentPane().add(connectPanel, CONNECT_PAGE);
        getContentPane().add(loginPanel, LOGIN_PAGE);
        getContentPane().add(chatPanel, CHAT_PAGE);
    }
}