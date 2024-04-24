package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Product;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class BasicStorage implements Storage {

    protected BlockingQueue<Product> storage;
    protected int size;

    @Override
    public void setSize(int size) {
        this.size = size;
        storage = new ArrayBlockingQueue<>(size);
    }

    @Override
    public void addToStorage(Product product) {
        try {
            storage.put(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
