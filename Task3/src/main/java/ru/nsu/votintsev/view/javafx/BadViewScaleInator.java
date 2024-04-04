package ru.nsu.votintsev.view.javafx;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class BadViewScaleInator {

    private static final int MY_SCREEN_WIDTH = 2048;
    private static final int MY_SCREEN_HEIGHT = 1152;

    private final double scalePercentWidth;
    private final double scalePercentHeight;

    public BadViewScaleInator() {

        Screen screen = Screen.getPrimary();

        // Получаем прямоугольник, представляющий размер экрана
        Rectangle2D screenSize = screen.getVisualBounds();

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

}
