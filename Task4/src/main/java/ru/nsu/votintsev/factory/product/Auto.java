package ru.nsu.votintsev.factory.product;

import lombok.Getter;

@Getter
public class Auto extends BasicProduct {

    private final int bodyId;
    private final int motorId;
    private final int accessoryId;

    public Auto(int bodyId, int motorId, int accessoryId,  int id) {
        this.bodyId = bodyId;
        this.motorId = motorId;
        this.accessoryId = accessoryId;
        this.id = id;
    }
}
