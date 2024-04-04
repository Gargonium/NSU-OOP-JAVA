package ru.nsu.votintsev.view.swing.label.entity;

import ru.nsu.votintsev.view.swing.ViewScaleInator;

import javax.swing.*;
import java.util.Objects;

public class DoorLabel extends JLabel {

    public DoorLabel(ViewScaleInator viewScaleInator) {
        ImageIcon doorIcon = viewScaleInator.scaleImage(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/door.png"))));
        this.setIcon(doorIcon);
        this.setSize(doorIcon.getIconWidth(), doorIcon.getIconHeight());
    }
}
