package ru.nsu.votintsev.view.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.nsu.votintsev.view.states.PlayerSpriteState;

import java.util.Objects;

public class PlayerImage extends ImageView {

    private final Image playerStandLeft0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerStand0.png"))));
    private final Image playerStandLeft1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerStand1.png"))));
    private final Image playerRunLeft0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerRun0.png"))));
    private final Image playerRunLeft1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerRun1.png"))));
    private final Image playerRunLeft2 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerLeft/playerRun2.png"))));

    private final Image playerStandRight0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerStand0.png"))));
    private final Image playerStandRight1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerStand1.png"))));
    private final Image playerRunRight0 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerRun0.png"))));
    private final Image playerRunRight1 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerRun1.png"))));
    private final Image playerRunRight2 = (new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Sprites/playerSprites/playerRight/playerRun2.png"))));

    private Image playerSprite;

    private PlayerSpriteState playerSpriteState = PlayerSpriteState.STAND_RIGHT_0;

    public PlayerImage() {
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
