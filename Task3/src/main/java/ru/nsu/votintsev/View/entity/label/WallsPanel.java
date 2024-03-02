package ru.nsu.votintsev.View.entity.label;

import javax.swing.*;
import java.awt.*;

public class WallsPanel extends JPanel {

    int x;
    int y;
    int width;
    int height;

    public WallsPanel(Rectangle wall) {
        this.x = (int) wall.getX();
        this.y = (int) wall.getY();
        this.width = (int) wall.getWidth();
        this.height = (int) wall.getHeight();
        this.setBounds(x, y, width, height);
        this.setBackground(new Color(0, 100, 0));
        this.setOpaque(true);
    }
}
