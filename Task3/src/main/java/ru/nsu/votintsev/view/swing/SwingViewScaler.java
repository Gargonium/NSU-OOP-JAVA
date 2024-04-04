package ru.nsu.votintsev.view.swing;

// Behold Perry the Platypus!

import javax.swing.*;
import java.awt.*;

public class SwingViewScaler {

    private static final int MY_SCREEN_WIDTH = 2048;
    private static final int MY_SCREEN_HEIGHT = 1152;

    private final double scalePercentWidth;
    private final double scalePercentHeight;

    public SwingViewScaler() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double userScreenWidth = screenSize.getWidth();
        double userScreenHeight = screenSize.getHeight();
        scalePercentWidth = userScreenWidth / MY_SCREEN_WIDTH;
        scalePercentHeight = userScreenHeight / MY_SCREEN_HEIGHT;
    }

    public double getScalePercentWidth() {
        return scalePercentWidth;
    }

    public double getScalePercentHeight() {
        return scalePercentHeight;
    }

    public ImageIcon scaleImage(ImageIcon originalIcon) {
        int newImageWidth = (int) (originalIcon.getIconWidth() * scalePercentWidth);
        int newImageHeight = (int) (originalIcon.getIconHeight() * scalePercentHeight);
        Image scaledIcon = originalIcon.getImage().getScaledInstance(newImageWidth, newImageHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledIcon);
    }
}
