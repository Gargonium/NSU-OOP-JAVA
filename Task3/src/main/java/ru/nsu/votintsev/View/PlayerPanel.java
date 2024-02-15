package ru.nsu.votintsev.View;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {

    JLabel playerLabel;
    Icon image;

    int PLAYER_WIDTH;
    int PLAYER_HEIGHT;

    public PlayerPanel() {
        playerLabel = new JLabel();
        image = new ImageIcon("src/main/resources/player.png");

        PLAYER_WIDTH = image.getIconWidth();
        PLAYER_HEIGHT = image.getIconHeight();

        playerLabel.setSize(PLAYER_WIDTH,PLAYER_HEIGHT);
        playerLabel.setIcon(image);

        this.setSize(new Dimension(PLAYER_WIDTH,PLAYER_HEIGHT));
        this.add(playerLabel);
    }

    public int getPlayerWidth() {
        return PLAYER_WIDTH;
    }

    public int getPlayerHeight() {
        return PLAYER_HEIGHT;
    }
}
