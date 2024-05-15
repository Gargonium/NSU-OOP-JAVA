package ru.nsu.votintsev.client.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegistrationFrame extends JFrame implements ActionListener {

    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JPasswordField passwordConfirmField = new JPasswordField();
    private JButton signUpButton = new JButton("Sign Up");
    private JButton signInButton = new JButton("Sign In");
    private JLabel usernameLabel = new JLabel("Username: ");
    private JLabel passwordLabel = new JLabel("Password: ");
    private JLabel passwordConfirmLabel = new JLabel("Confirm Password: ");
    private JLabel errorLabel = new JLabel();

    public RegistrationFrame() {
        setTitle("Registration");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        signInButton.addActionListener(this);
        signUpButton.addActionListener(this);

        usernameField.setBounds(110, 10, 100, 20);
        passwordField.setBounds(110, 40, 100, 20);
        passwordConfirmField.setBounds(110, 80, 100, 20);
        usernameLabel.setBounds(10, 10, 100, 20);
        passwordLabel.setBounds(10, 40, 100, 20);
        passwordConfirmLabel.setBounds(10, 80, 100, 20);
        signUpButton.setBounds(10, 80, 100, 20);
        signInButton.setBounds(10, 100, 100, 20);
        add(usernameLabel);
        add(passwordLabel);
        add(signUpButton);
        add(signInButton);
        add(usernameField);
        add(passwordField);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            if (Arrays.equals(passwordField.getPassword(), passwordConfirmField.getPassword())) {
                new ChatFrame();
                dispose();
            } else {
                errorLabel.setText("Passwords do not match!");
            }
        }
        else if (e.getSource() == signInButton) {
            new EnterFrame();
            dispose();
        }
    }
}
