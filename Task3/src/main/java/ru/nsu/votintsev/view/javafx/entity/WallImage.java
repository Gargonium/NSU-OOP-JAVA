package ru.nsu.votintsev.view.javafx.entity;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.nsu.votintsev.model.ModelRectangle;
import ru.nsu.votintsev.view.javafx.FXViewScaleInator;

import java.util.Objects;

public class WallImage {
    public WallImage(ModelRectangle wall, Group root, FXViewScaleInator fxViewScaleInator) {

        int x = wall.x();
        int y = wall.y();
        int width = wall.width();
        int height = wall.height();

        Image grassIcon = fxViewScaleInator.scaleImage(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/landSprites/grass.png")))).getImage();
        Image dirtIcon = fxViewScaleInator.scaleImage(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/landSprites/dirt.png")))).getImage();

        ImageView grass = new ImageView(grassIcon);
        grass.setX(x);
        grass.setY(y);
        grass.setFitWidth(width);
        grass.setFitHeight(height);
        root.getChildren().add(grass);

//        for (int i = 0; i < width /grassIcon.getWidth() + 1; ++i) {
//            ImageView grass = new ImageView(grassIcon);
//            grass.setX(i * grassIcon.getWidth() + x);
//            grass.setY(y);
//            grass.setFitWidth(grassIcon.getWidth());
//            grass.setFitHeight(grassIcon.getHeight());
//            root.getChildren().add(grass);
//        }
//
//        for (int i = 0; i < width / dirtIcon.getWidth(); ++i) {
//            for (int j = 1; j < height / dirtIcon.getHeight(); ++j) {
//                ImageView dirt = new ImageView(dirtIcon);
//                dirt.setX(i * dirtIcon.getWidth() + x);
//                dirt.setY(j * dirtIcon.getWidth() + y);
//                dirt.setFitWidth(dirtIcon.getWidth());
//                dirt.setFitHeight(dirtIcon.getHeight());
//                root.getChildren().add(dirt);
//            }
//        }
    }
}
