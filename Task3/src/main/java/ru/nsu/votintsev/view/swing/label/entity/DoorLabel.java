package ru.nsu.votintsev.view.swing.label.entity;

import ru.nsu.votintsev.view.swing.SwingViewScaler;

import javax.swing.*;
import java.util.Objects;

public class DoorLabel extends JLabel {

    public DoorLabel(SwingViewScaler swingViewScaler) {
        ImageIcon doorIcon = swingViewScaler.scaleImage(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/door.png"))));
        this.setIcon(doorIcon);
        this.setSize(doorIcon.getIconWidth(), doorIcon.getIconHeight());
    }
}
