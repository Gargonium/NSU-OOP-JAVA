package ru.nsu.votintsev.View;

import javax.swing.*;
import java.util.Objects;

public class DoorPanel extends JLabel {

    public DoorPanel() {
        Icon doorIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/door.png")));
        this.setIcon(doorIcon);
        this.setSize(doorIcon.getIconWidth(), doorIcon.getIconHeight());
    }
}
