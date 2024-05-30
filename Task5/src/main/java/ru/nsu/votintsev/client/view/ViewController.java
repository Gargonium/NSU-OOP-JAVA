package ru.nsu.votintsev.client.view;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.client.Client;
import ru.nsu.votintsev.client.ClientController;
import ru.nsu.votintsev.client.ClientSender;

import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class ViewController implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    private final JButton sendFileButton;
    private final JButton sendButton;
    private final JButton userListButton;
    private final JTextField messageField;

    private final JTextField ipField;
    private final JTextField portField;
    private final JButton connectButton;

    private final Client client;

    private ClientView clientView;
    private ClientSender clientSender;

    private Socket socket;

    private boolean isAuthenticated = false;
    private boolean needToDispose = false;

    private List<JButton> muteButtons;

    public ViewController(JTextField usernameField, JPasswordField passwordField, JButton loginButton, JButton sendFileButton, JButton sendButton, JButton userListButton, JTextField messageField, JTextField ipField, JTextField portField, JButton connectButton, Client client) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.loginButton = loginButton;
        this.sendFileButton = sendFileButton;
        this.sendButton = sendButton;
        this.userListButton = userListButton;
        this.messageField = messageField;
        this.ipField = ipField;
        this.portField = portField;
        this.connectButton = connectButton;
        this.client = client;

        KeyListener connectKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        client.connect(ipField.getText(), Integer.parseInt(portField.getText()));
                        clientView.showLoginPanel();
                    } catch (IOException ex) {
                        clientView.setErrorConnectLabel(ex.getMessage());
                    }
                }
            }
        };

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

        ipField.addKeyListener(connectKeyAdapter);
        portField.addKeyListener(connectKeyAdapter);

        usernameField.addKeyListener(loginKeyAdapter);
        passwordField.addKeyListener(loginKeyAdapter);

        messageField.addKeyListener(messageKeyAdapter);

        connectButton.addActionListener(this);
        sendFileButton.addActionListener(this);
        sendButton.addActionListener(this);
        userListButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    public void setMuteButtons(List<JButton> muteButtons) {
        this.muteButtons = muteButtons;
    }

    public void setView(ClientView clientView) {
        this.clientView = clientView;
        clientView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isConnected())
                    try {
                        clientSender.sendLogoutCommand();
                        needToDispose = true;
                    } catch (JAXBException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                else
                    System.exit(0);
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
            if (e.getSource() == connectButton)
                try {
                    client.connect(ipField.getText(), Integer.parseInt(portField.getText()));
                    clientView.showLoginPanel();
                } catch (IOException ex) {
                    clientView.setErrorConnectLabel(ex.getMessage());
                }
            else if (e.getSource() == loginButton)
                login();
            else if (e.getSource() == sendFileButton)
                sendFile();
            else if (e.getSource() == sendButton)
                sendMessage();
            else if (e.getSource() == userListButton)
                requestUserList();
            else if (muteButtons.contains(e.getSource())) {
                muteButtonPushed(muteButtons.indexOf(e.getSource()));
            }
        }
        catch (JAXBException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void successReceived() throws IOException {
        if (!isAuthenticated) {
            clientView.showChatPanel();
            isAuthenticated = true;
        }
        if (needToDispose) {
            clientView.dispose();
            System.exit(1);
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

    private void sendFile() throws JAXBException, IOException {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(clientView);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            FileInputStream fis = new FileInputStream(selectedFile);
            byte[] bytes = new byte[(int) selectedFile.length()];
            fis.read(bytes);

            byte[] fileData = new byte[(int) selectedFile.length()];
            DataInputStream dis = new DataInputStream(new FileInputStream(selectedFile));
            dis.readFully(fileData);
            dis.close();

            clientSender.sendUploadCommand(selectedFile.getName(), Files.probeContentType(Path.of(selectedFile.getPath())), "base64", Base64.getEncoder().encode(bytes));
        }
    }

    public void requestFile(Integer id) throws JAXBException, IOException {
        clientSender.sendDownloadCommand(id);
    }

    private void muteButtonPushed(int index) throws JAXBException, IOException {
        JButton button = muteButtons.get(index);
        if (Objects.equals(button.getText(), "Unmute")) {
            clientSender.sendUnmuteCommand(button.getName());
            button.setText("Mute");
        }
        else {
            clientSender.sendMuteCommand(button.getName());
            button.setText("Unmute");
        }
    }
}