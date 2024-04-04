package ru.nsu.votintsev.view.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class DoorImage extends ImageView {
    public DoorImage() {
        Image doorIcon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/door.png")));
        this.setImage(doorIcon);
        this.setFitHeight(doorIcon.getHeight());
        this.setFitWidth(doorIcon.getWidth());
    }
}
