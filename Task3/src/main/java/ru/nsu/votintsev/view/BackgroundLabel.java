package ru.nsu.votintsev.view;

import javax.swing.*;
import java.awt.*;

public class BackgroundLabel extends JLabel {

    public BackgroundLabel(int width, int height) {
        this.setBounds(0,0, width, height);
        this.setBackground(Color.gray);
        this.setOpaque(true);
        this.setLayout(null);
    }
}
