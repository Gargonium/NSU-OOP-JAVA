package ru.nsu.votintsev.factory.product;

import lombok.Getter;

@Getter
public class Motor implements Product {

    private final int id;

    public Motor(int id) {
        this.id = id;
    }
}
