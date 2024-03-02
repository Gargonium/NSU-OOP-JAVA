package ru.nsu.votintsev.View.entity.label;

import javax.swing.*;
import java.util.Objects;

public class DoorLabel extends JLabel {

    public DoorLabel() {
        Icon doorIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/door.png")));
        this.setIcon(doorIcon);
        this.setSize(doorIcon.getIconWidth(), doorIcon.getIconHeight());
    }
}
