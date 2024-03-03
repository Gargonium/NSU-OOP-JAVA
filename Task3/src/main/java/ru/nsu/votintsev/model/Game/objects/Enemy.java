package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.Wall;
import ru.nsu.votintsev.model.directions.EnemyDirection;
import ru.nsu.votintsev.model.GameContext;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Enemy extends GameObject implements ActionListener {

    private final Timer timer = new Timer(10, this);

    private final int xVelocity = 7;

    private int width;
    private int height;

    private final GameContext ctx;

    private EnemyDirection enemyDirection = EnemyDirection.RIGHT;

    public Enemy(GameContext context, int id, int x, int y) {
        timer.start();
        ctx = context;

        X = x;
        Y = y;
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
            boolean findLeft = true;
            boolean findRight = true;
            for (Wall wall : ctx.getWalls()) {
                if (wall.getY() < Y + height)
                    if ((X + width + xVelocity >= wall.getX()) || (X - xVelocity <= wall.getX() + wall.getWidth()))
                        enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;

                if (Y + height == wall.getY())
                    if ((X >= wall.getX()) && (X + width <= wall.getX() + wall.getWidth())) {
                        if ((X - xVelocity <= wall.getX()) && (enemyDirection == EnemyDirection.LEFT))
                            findLeft = false;
                        if ((X + width + xVelocity >= wall.getX() + wall.getWidth()) && (enemyDirection == EnemyDirection.RIGHT))
                            findRight = false;
                    }
            }

            if (!findLeft) {
                enemyDirection = EnemyDirection.RIGHT;
                System.out.println(1);
            }
            if (!findRight) {
                enemyDirection = EnemyDirection.LEFT;
                System.out.println(2);
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
