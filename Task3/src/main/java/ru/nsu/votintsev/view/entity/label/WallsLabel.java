package ru.nsu.votintsev.view.entity.label;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class WallsLabel extends JPanel {

    int x;
    int y;
    int width;
    int height;

    public WallsLabel(Rectangle wall) {

        this.x = (int) wall.getX();
        this.y = (int) wall.getY();
        this.width = (int) wall.getWidth();
        this.height = (int) wall.getHeight();

        ImageIcon grassIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/dirt.png")));

        this.setLayout(null);

        for (int i = 0; i < width/grassIcon.getIconWidth() + 1; ++i) {
            JLabel label = new JLabel(grassIcon);
            label.setBounds(i * grassIcon.getIconWidth(), 0, grassIcon.getIconWidth(), grassIcon.getIconHeight());
            this.add(label);
        }

        this.setBounds(x, y, width, height);
    }
}
