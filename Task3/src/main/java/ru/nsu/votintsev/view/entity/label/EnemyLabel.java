package ru.nsu.votintsev.view.entity.label;

import ru.nsu.votintsev.view.sprite.state.EnemySpriteState;

import javax.swing.*;
import java.util.Objects;

public class EnemyLabel extends JLabel {
    
    private final Icon enemyRunLeft0 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyLeft/enemyRun0.png")));
    private final Icon enemyRunLeft1 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyLeft/enemyRun1.png")));
    private final Icon enemyRunLeft2 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyLeft/enemyRun2.png")));
    
    private final Icon enemyRunRight0 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyRight/enemyRun0.png")));
    private final Icon enemyRunRight1 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyRight/enemyRun1.png")));
    private final Icon enemyRunRight2 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Sprites/enemySprites/enemyRight/enemyRun2.png")));

    private Icon enemySprite;

    private EnemySpriteState enemySpriteState = EnemySpriteState.RUN_RIGHT_0;

    public EnemyLabel() {

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
