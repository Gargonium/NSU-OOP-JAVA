package ru.nsu.votintsev.client.view;

import ru.nsu.votintsev.xmlclasses.User;
import ru.nsu.votintsev.xmlclasses.Users;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserListFrame extends JFrame {

    public UserListFrame(Users userList, ViewController viewController) {
        this.setTitle("User List");
        List<JButton> muteButtons = new ArrayList<>();
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
