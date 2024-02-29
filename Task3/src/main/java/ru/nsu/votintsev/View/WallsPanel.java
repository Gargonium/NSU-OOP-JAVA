package ru.nsu.votintsev.View;

import javax.swing.*;
import java.awt.*;

public class WallsPanel extends JPanel {

    int x;
    int y;
    int width;
    int height;

    public WallsPanel(int[] dimensions) {
        this.x = dimensions[0];
        this.y = dimensions[1];
        this.width = dimensions[2];
        this.height = dimensions[3];
        this.setBounds(x, y, width, height);
        this.setBackground(new Color(0, 100, 0));
        this.setOpaque(true);
    }
}
