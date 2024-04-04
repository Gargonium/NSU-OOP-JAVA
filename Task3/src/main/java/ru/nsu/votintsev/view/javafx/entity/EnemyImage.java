package ru.nsu.votintsev.view.javafx.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.nsu.votintsev.view.javafx.FXViewScaleInator;
import ru.nsu.votintsev.view.states.EnemySpriteState;

import java.util.Objects;

public class EnemyImage extends ImageView {
    private Image enemyRunLeft0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/enemySprites/enemyLeft/enemyRun0.png"))));
    private Image enemyRunLeft1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/enemySprites/enemyLeft/enemyRun1.png"))));
    private Image enemyRunLeft2 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/enemySprites/enemyLeft/enemyRun2.png"))));

    private Image enemyRunRight0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/enemySprites/enemyRight/enemyRun0.png"))));
    private Image enemyRunRight1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/enemySprites/enemyRight/enemyRun1.png"))));
    private Image enemyRunRight2 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/enemySprites/enemyRight/enemyRun2.png"))));

    private Image enemySprite;

    private EnemySpriteState enemySpriteState = EnemySpriteState.RUN_RIGHT_0;

    public EnemyImage(FXViewScaleInator fxViewScaleInator) {

        enemyRunLeft0 = fxViewScaleInator.scaleImage(enemyRunLeft0).getImage();
        enemyRunLeft1 = fxViewScaleInator.scaleImage(enemyRunLeft1).getImage();
        enemyRunLeft2 = fxViewScaleInator.scaleImage(enemyRunLeft2).getImage();

        enemyRunRight0 = fxViewScaleInator.scaleImage(enemyRunRight0).getImage();
        enemyRunRight1 = fxViewScaleInator.scaleImage(enemyRunRight1).getImage();
        enemyRunRight2 = fxViewScaleInator.scaleImage(enemyRunRight2).getImage();

        setEnemySprite();
        this.setFitWidth(enemySprite.getWidth());
        this.setFitHeight(enemySprite.getHeight());
        this.setImage(enemySprite);
    }

    private void setEnemySprite() {
        switch (enemySpriteState) {
            case RUN_LEFT_0 -> enemySprite = enemyRunLeft0;
            case RUN_LEFT_1 -> enemySprite = enemyRunLeft1;
            case RUN_LEFT_2 -> enemySprite = enemyRunLeft2;

            case RUN_RIGHT_0 -> enemySprite = enemyRunRight0;
            case RUN_RIGHT_1 -> enemySprite = enemyRunRight1;
            case RUN_RIGHT_2 -> enemySprite = enemyRunRight2;
        }
        this.setImage(enemySprite);
    }

    public void updateEnemySprite(EnemySpriteState enemySpriteState) {
        this.enemySpriteState = enemySpriteState;
        setEnemySprite();
    }

    public int getWidth() {
        return (int) enemySprite.getWidth();
    }

    public int getHeight() {
        return (int) enemySprite.getHeight();
    }
}
