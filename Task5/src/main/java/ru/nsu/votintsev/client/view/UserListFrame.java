package ru.nsu.votintsev.client.view;

import ru.nsu.votintsev.client.Observer;
import ru.nsu.votintsev.xmlclasses.User;
import ru.nsu.votintsev.xmlclasses.Users;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserListFrame extends JFrame {

    private final Users userList;
    private final List<JLabel> userLabels;
    private final List<JButton> muteButtons;

    public UserListFrame(Users userList, ViewController viewController) {
        viewController.setUserListFrame(this);
        this.userList = userList;
        this.setTitle("User List");
        muteButtons = new ArrayList<>();
        userLabels = new ArrayList<>();
        viewController.setMuteButtons(muteButtons);
        JPanel mainPanel = new JPanel();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mainPanel.setLayout(new GridLayout(userList.getUsers().size(), 2, 0,5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        for (User user : userList.getUsers()) {
            JLabel label = new JLabel(user.getName());
            JButton button = new JButton(user.getMute() ? "Unmute" : "Mute");
            button.addActionListener(viewController);
            button.setName(user.getName());

            muteButtons.add(button);
            userLabels.add(label);

            mainPanel.add(label);
            mainPanel.add(button);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        this.add(scrollPane);

        this.pack();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
