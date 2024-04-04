package ru.nsu.votintsev.view.javafx.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.nsu.votintsev.view.javafx.FXViewScaleInator;

import java.util.Objects;

public class DoorImage extends ImageView {
    public DoorImage(FXViewScaleInator fxViewScaleInator) {
        Image doorIcon = fxViewScaleInator.scaleImage(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/door.png")))).getImage();
        this.setImage(doorIcon);
        this.setFitHeight(doorIcon.getHeight());
        this.setFitWidth(doorIcon.getWidth());
    }
}
