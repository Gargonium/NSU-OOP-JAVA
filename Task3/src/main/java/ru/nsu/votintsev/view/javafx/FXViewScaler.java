package ru.nsu.votintsev.view.javafx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

public class FXViewScaler {

    private static final int MY_SCREEN_WIDTH = 2048;
    private static final int MY_SCREEN_HEIGHT = 1152;

    private final double scalePercentWidth;
    private final double scalePercentHeight;

    public FXViewScaler() {

        Screen screen = Screen.getPrimary();
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

    public Image scaleImage(Image originalImage) {
        int newImageWidth = (int) (originalImage.getWidth() * scalePercentWidth);
        int newImageHeight = (int) (originalImage.getHeight() * scalePercentHeight);
        ImageView scaledImage = new ImageView(originalImage);
        scaledImage.setFitHeight(newImageHeight);
        scaledImage.setFitWidth(newImageWidth);
        System.out.println(scaledImage.getImage().getHeight());
        return scaledImage.getImage();
    }

}
