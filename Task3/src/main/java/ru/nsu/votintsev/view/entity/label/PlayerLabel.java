package ru.nsu.votintsev.view.entity.label;

import ru.nsu.votintsev.view.ViewScaleInator;
import ru.nsu.votintsev.view.sprite.state.PlayerSpriteState;

import javax.swing.*;
import java.util.Objects; 

public class PlayerLabel extends JLabel {

    private ImageIcon playerStandLeft0 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerLeft/playerStand0.png"))));
    private ImageIcon playerStandLeft1 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerLeft/playerStand1.png"))));
    private ImageIcon playerRunLeft0 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerLeft/playerRun0.png"))));
    private ImageIcon playerRunLeft1 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerLeft/playerRun1.png"))));
    private ImageIcon playerRunLeft2 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerLeft/playerRun2.png"))));

    private ImageIcon playerStandRight0 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerRight/playerStand0.png"))));
    private ImageIcon playerStandRight1 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerRight/playerStand1.png"))));
    private ImageIcon playerRunRight0 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerRight/playerRun0.png"))));
    private ImageIcon playerRunRight1 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerRight/playerRun1.png"))));
    private ImageIcon playerRunRight2 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/playerSprites/playerRight/playerRun2.png"))));

    private ImageIcon playerSprite;

    private PlayerSpriteState playerSpriteState = PlayerSpriteState.STAND_RIGHT_0;

    public PlayerLabel(ViewScaleInator viewScaleInator) {

        playerStandLeft0 = viewScaleInator.scaleImage(playerStandLeft0);
        playerStandLeft1 = viewScaleInator.scaleImage(playerStandLeft1);
        playerRunLeft0 = viewScaleInator.scaleImage(playerRunLeft0);
        playerRunLeft1 = viewScaleInator.scaleImage(playerRunLeft1);
        playerRunLeft2 = viewScaleInator.scaleImage(playerRunLeft2);

        playerStandRight0 = viewScaleInator.scaleImage(playerStandRight0);
        playerStandRight1 = viewScaleInator.scaleImage(playerStandRight1);
        playerRunRight0 = viewScaleInator.scaleImage(playerRunRight0);
        playerRunRight1 = viewScaleInator.scaleImage(playerRunRight1);
        playerRunRight2 = viewScaleInator.scaleImage(playerRunRight2);


        setPlayerSprite();

        this.setSize(playerSprite.getIconWidth(), playerSprite.getIconHeight());
        this.setIcon(playerSprite);
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
        this.setIcon(playerSprite);
    }

    public void updatePlayerSprite(PlayerSpriteState playerSpriteState) {
        this.playerSpriteState = playerSpriteState;
        setPlayerSprite();
    }
}
