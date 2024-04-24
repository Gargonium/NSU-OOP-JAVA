package ru.nsu.votintsev.factory.product;

import lombok.Getter;

public abstract class BasicProduct implements Product {
    @Getter
    protected int id;
}
