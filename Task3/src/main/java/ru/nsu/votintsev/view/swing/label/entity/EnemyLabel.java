package ru.nsu.votintsev.view.swing.label.entity;

import ru.nsu.votintsev.view.swing.ViewScaleInator;
import ru.nsu.votintsev.view.swing.state.sprite.EnemySpriteState;

import javax.swing.*;
import java.util.Objects;

public class EnemyLabel extends JLabel {
    
    private ImageIcon enemyRunLeft0 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyLeft/enemyRun0.png"))));
    private ImageIcon enemyRunLeft1 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyLeft/enemyRun1.png"))));
    private ImageIcon enemyRunLeft2 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyLeft/enemyRun2.png"))));
    
    private ImageIcon enemyRunRight0 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyRight/enemyRun0.png"))));
    private ImageIcon enemyRunRight1 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyRight/enemyRun1.png"))));
    private ImageIcon enemyRunRight2 = (new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyRight/enemyRun2.png"))));

    private ImageIcon enemySprite;

    private EnemySpriteState enemySpriteState = EnemySpriteState.RUN_RIGHT_0;

    public EnemyLabel(ViewScaleInator viewScaleInator) {

        enemyRunLeft0 = viewScaleInator.scaleImage(enemyRunLeft0);
        enemyRunLeft1 = viewScaleInator.scaleImage(enemyRunLeft1);
        enemyRunLeft2 = viewScaleInator.scaleImage(enemyRunLeft2);

        enemyRunRight0 = viewScaleInator.scaleImage(enemyRunRight0);
        enemyRunRight1 = viewScaleInator.scaleImage(enemyRunRight1);
        enemyRunRight2 = viewScaleInator.scaleImage(enemyRunRight2);


        setEnemySprite();

        this.setSize(enemySprite.getIconWidth(), enemySprite.getIconHeight());
        this.setIcon(enemySprite);
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
        this.setIcon(enemySprite);
    }

    public void updateEnemySprite(EnemySpriteState enemySpriteState) {
        this.enemySpriteState = enemySpriteState;
        setEnemySprite();
    }
    
}
