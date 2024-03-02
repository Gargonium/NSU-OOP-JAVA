package ru.nsu.votintsev.View.entity.label;

import ru.nsu.votintsev.View.sprite.state.PlayerSpriteState;

import javax.swing.*;
import java.util.Objects; 

public class PlayerLabel extends JLabel {

    private final Icon playerStandLeft0 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerLeft/playerStand0.png")));
    private final Icon playerStandLeft1 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerLeft/playerStand1.png")));
    private final Icon playerRunLeft0 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerLeft/playerRun0.png")));
    private final Icon playerRunLeft1 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerLeft/playerRun1.png")));
    private final Icon playerRunLeft2 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerLeft/playerRun2.png")));

    private final Icon playerStandRight0 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerRight/playerStand0.png")));
    private final Icon playerStandRight1 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerRight/playerStand1.png")));
    private final Icon playerRunRight0 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerRight/playerRun0.png")));
    private final Icon playerRunRight1 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerRight/playerRun1.png")));
    private final Icon playerRunRight2 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/playerSprites/playerRight/playerRun2.png")));

    private Icon playerSprite;

    private PlayerSpriteState playerSpriteState = PlayerSpriteState.STAND_RIGHT_0;

    public PlayerLabel() {

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
