package ru.nsu.votintsev.view.entity.label;

import ru.nsu.votintsev.view.ViewScaleInator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class WallsLabel extends JPanel {

    public WallsLabel(Rectangle wall, ViewScaleInator viewScaleInator) {

        int x = (int) wall.getX();
        int y = (int) wall.getY();
        int width = (int) wall.getWidth();
        int height = (int) wall.getHeight();

        ImageIcon grassIcon = viewScaleInator.scaleImage(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/dirt.png"))));

        this.setLayout(null);

        for (int i = 0; i < width /grassIcon.getIconWidth() + 1; ++i) {
            JLabel label = new JLabel(grassIcon);
            label.setBounds(i * grassIcon.getIconWidth(), 0, grassIcon.getIconWidth(), grassIcon.getIconHeight());
            this.add(label);
        }

        this.setBounds(x, y, width, height);
    }
}
