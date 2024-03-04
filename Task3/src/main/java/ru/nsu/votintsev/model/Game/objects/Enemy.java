package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.directions.EnemyDirection;
import ru.nsu.votintsev.model.GameContext;

public class Enemy implements GameObject {

    private int X;
    private int Y;

    private final static int SPEED = 5;

    private int width;
    private int height;

    private final GameContext ctx;

    private EnemyDirection enemyDirection = EnemyDirection.RIGHT;

    public Enemy(GameContext context, int x, int y) {
        ctx = context;

        X = x;
        Y = y;
    }

    @Override
    public int getX() {
        return X;
    }

    @Override
    public void setX(int x) {
        X = x;
    }

    @Override
    public int getY() {
        return Y;
    }

    @Override
    public void setY(int y) {
        Y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EnemyDirection getEnemyDirection() {
        return enemyDirection;
    }

    public void checkForCollisionsAndMove() {

        for (Wall wall : ctx.getWalls()) {
            if (X < wall.getX() + wall.getWidth() && X + width > wall.getX() &&
                    Y < wall.getY() + wall.getHeight() && Y + height > wall.getY())
                enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
            if ((X - SPEED <= wall.getX() && X >= wall.getX())
                    || (X + width + SPEED >= wall.getX() + wall.getWidth())  && (X + width <= wall.getX() + wall.getWidth())) {
                enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
            }
        }

        if ((X <= 0) || (X + width >= ctx.getGameFieldWidth())){
            enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
        }

        if (enemyDirection == EnemyDirection.LEFT)
            X -= SPEED;
        else
            X += SPEED;
    }
}
