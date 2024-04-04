package ru.nsu.votintsev.view.swing.label.entity;

import ru.nsu.votintsev.view.swing.SwingViewScaler;
import ru.nsu.votintsev.view.states.EnemySpriteState;

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

    public EnemyLabel(SwingViewScaler swingViewScaler) {

        enemyRunLeft0 = swingViewScaler.scaleImage(enemyRunLeft0);
        enemyRunLeft1 = swingViewScaler.scaleImage(enemyRunLeft1);
        enemyRunLeft2 = swingViewScaler.scaleImage(enemyRunLeft2);

        enemyRunRight0 = swingViewScaler.scaleImage(enemyRunRight0);
        enemyRunRight1 = swingViewScaler.scaleImage(enemyRunRight1);
        enemyRunRight2 = swingViewScaler.scaleImage(enemyRunRight2);


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
