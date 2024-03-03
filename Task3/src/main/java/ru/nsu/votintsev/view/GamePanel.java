package ru.nsu.votintsev.view;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private int x;
    private int y;

    public GamePanel(int width, int height) {
        x = 0;
        y = 0;
        this.setBounds(x,y, width, height);
        this.setBackground(Color.gray);
        this.setOpaque(true);
        this.setLayout(null);
    }



    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
