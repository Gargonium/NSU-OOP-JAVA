package ru.nsu.votintsev.view.javafx.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.nsu.votintsev.view.javafx.FXViewScaler;
import ru.nsu.votintsev.view.states.PlayerSpriteState;

import java.util.Objects;

public class PlayerImage extends ImageView {

    private Image playerStandLeft0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerStand0.png"))));
    private Image playerStandLeft1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerStand1.png"))));
    private Image playerRunLeft0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerRun0.png"))));
    private Image playerRunLeft1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerRun1.png"))));
    private Image playerRunLeft2 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerRun2.png"))));

    private Image playerStandRight0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerStand0.png"))));
    private Image playerStandRight1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerStand1.png"))));
    private Image playerRunRight0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerRun0.png"))));
    private Image playerRunRight1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerRun1.png"))));
    private Image playerRunRight2 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerRun2.png"))));

    private Image playerSprite;

    private PlayerSpriteState playerSpriteState = PlayerSpriteState.STAND_RIGHT_0;

    public PlayerImage(FXViewScaler fxViewScaler) {

        playerStandLeft0 = fxViewScaler.scaleImage(playerStandLeft0).getImage();
        playerStandLeft1 = fxViewScaler.scaleImage(playerStandLeft1).getImage();
        playerRunLeft0 = fxViewScaler.scaleImage(playerRunLeft0).getImage();
        playerRunLeft1 = fxViewScaler.scaleImage(playerRunLeft1).getImage();
        playerRunLeft2 = fxViewScaler.scaleImage(playerRunLeft2).getImage();

        playerStandRight0 = fxViewScaler.scaleImage(playerStandRight0).getImage();
        playerStandRight1 = fxViewScaler.scaleImage(playerStandRight1).getImage();
        playerRunRight0 = fxViewScaler.scaleImage(playerRunRight0).getImage();
        playerRunRight1 = fxViewScaler.scaleImage(playerRunRight1).getImage();
        playerRunRight2 = fxViewScaler.scaleImage(playerRunRight2).getImage();

        setPlayerSprite();

        this.setFitWidth(playerSprite.getWidth());
        this.setFitHeight(playerSprite.getHeight());

        this.setImage(playerSprite);
    }

    private void setPlayerSprite() {
        switch (playerSpriteState) {
            case STAND_LEFT_0 -> playerSprite = playerStandLeft0;
            case STAND_LEFT_1 -> playerSprite = playerStandLeft1;
            case RUN_LEFT_0 -> playerSprite = playerRunLeft0;
            case RUN_LEFT_1 -> playerSprite = playerRunLeft1;
            case RUN_LEFT_2 -> playerSprite = playerRunLeft2;

            case STAND_RIGHT_0 -> playerSprite = playerStandRight0;
            case STAND_RIGHT_1 -> playerSprite = playerStandRight1;
            case RUN_RIGHT_0 -> playerSprite = playerRunRight0;
            case RUN_RIGHT_1 -> playerSprite = playerRunRight1;
            case RUN_RIGHT_2 -> playerSprite = playerRunRight2;
        }
        this.setImage(playerSprite);
    }

    public void updatePlayerSprite(PlayerSpriteState playerSpriteState) {
        this.playerSpriteState = playerSpriteState;
        setPlayerSprite();
    }

}
