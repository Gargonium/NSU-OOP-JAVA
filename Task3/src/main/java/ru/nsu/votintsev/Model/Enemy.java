package ru.nsu.votintsev.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Enemy extends GameObject implements ActionListener {

    private final Timer timer = new Timer(1, this);

    private final int xVelocity = 7;

    private int width;
    private int height;

    private final GameContext ctx;

    private EnemyDirection enemyDirection = EnemyDirection.RIGHT;

    private int id;

    public Enemy(GameContext context, int id, int x, int y) {
        timer.start();
        ctx = context;

        X = x;
        Y = y;
        this.id = id;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public EnemyDirection getEnemyDirection() {
        return enemyDirection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            for (Wall wall : ctx.getWalls()) {
                if (wall.getY() < Y + height) {
                    if ((X <= wall.getX() + wall.getWidth()) || (X + width >= wall.getX())) {
                        enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
                    }
                } else if ((wall.getY() + 1 == Y + height) &&
                        ((X >= wall.getX()) && (X + width <= wall.getX() + wall.getWidth()))) {
                    if ((X + xVelocity >= wall.getX() + wall.getWidth()) || (X - xVelocity <= wall.getX())) {
                        enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
                    }
                }

            }

            if ((X <= 0) || (X + width >= ctx.getGameFieldWidth())){
                enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
            }

            if (enemyDirection == EnemyDirection.LEFT)
                X -= xVelocity;
            else
                X += xVelocity;
        }
    }
}
