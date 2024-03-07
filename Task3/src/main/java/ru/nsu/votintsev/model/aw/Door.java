package ru.nsu.votintsev.model.aw;

import ru.nsu.votintsev.model.GameContext;

public class Door extends BaseGameObject {
    public Door(GameContext ctx) {
        this.ctx = ctx;
        x = 1000;
        y = 386;
    }
}
