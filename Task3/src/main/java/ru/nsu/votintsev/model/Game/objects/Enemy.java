package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;
import ru.nsu.votintsev.model.directions.EnemyDirection;

public class Enemy extends BaseGameObject {

    private int speed = 5;

    private EnemyDirection enemyDirection = EnemyDirection.RIGHT;

    public Enemy(GameContext context, int x, int y) {
        ctx = context;

        this.x = x;
        this.y = y;
    }

    @Override
    public void scaleMe() {
        x = ctx.modelScaleInator.scaleByX(x);
        y = ctx.modelScaleInator.scaleByY(y);
        speed = ctx.modelScaleInator.scaleByX(speed);
    }

    public EnemyDirection getEnemyDirection() {
        return enemyDirection;
    }

    @Override
    public void checkForCollisionsAndMove() {
        for (Wall wall : ctx.getWalls()) {
            if (x < wall.getX() + wall.getWidth() && x + width > wall.getX() &&
                    y < wall.getY() + wall.getHeight() && y + height > wall.getY())
                enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
            if ((x - speed <= wall.getX() && x >= wall.getX())
                    || (x + width + speed >= wall.getX() + wall.getWidth())  && (x + width <= wall.getX() + wall.getWidth())) {
                enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
            }
        }

        if ((x <= 0) || (x + width >= ctx.getGameFieldWidth())){
            enemyDirection = (enemyDirection == EnemyDirection.LEFT) ? EnemyDirection.RIGHT : EnemyDirection.LEFT;
        }

        if (enemyDirection == EnemyDirection.LEFT)
            x -= speed;
        else
            x += speed;
    }
}
