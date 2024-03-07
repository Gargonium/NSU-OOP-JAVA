package ru.nsu.votintsev.model.game.objects;

import ru.nsu.votintsev.model.GameContext;

public class Wall extends BaseGameObject {

    public Wall(GameContext ctx, int x, int y, int width, int height) {
        this.ctx = ctx;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
}
