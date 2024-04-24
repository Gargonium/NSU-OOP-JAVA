package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Product;

public interface Storage {
    void setSize(int size);
    void addToStorage(Product product);
}
