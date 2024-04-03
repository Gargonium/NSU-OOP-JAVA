package ru.nsu.votintsev.view.entity.label;

import ru.nsu.votintsev.model.ModelRectangle;
import ru.nsu.votintsev.view.ViewScaleInator;

import javax.swing.*;
import java.util.Objects;

public class WallsLabel extends JPanel {

    public WallsLabel(ModelRectangle wall, ViewScaleInator viewScaleInator) {

        int x = wall.getX();
        int y = wall.getY();
        int width = wall.getWidth();
        int height = wall.getHeight();

        ImageIcon grassIcon = viewScaleInator.scaleImage(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/landSprites/grass.png"))));
        ImageIcon dirtIcon = viewScaleInator.scaleImage(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/landSprites/dirt.png"))));

        this.setLayout(null);

        for (int i = 0; i < width /grassIcon.getIconWidth() + 1; ++i) {
            JLabel label = new JLabel(grassIcon);
            label.setBounds(i * grassIcon.getIconWidth(), 0, grassIcon.getIconWidth(), grassIcon.getIconHeight());
            this.add(label);
        }
        for (int i = 0; i < width / dirtIcon.getIconWidth() + 1; ++i) {
            for (int j = 1; j < height / dirtIcon.getIconHeight() + 1; ++j) {
                JLabel label = new JLabel(dirtIcon);
                label.setBounds(i * dirtIcon.getIconWidth(), j * dirtIcon.getIconHeight(), dirtIcon.getIconWidth(), dirtIcon.getIconHeight());
                this.add(label);
            }
        }

        this.setBounds(x, y, width, height);
    }
}
