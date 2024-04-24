package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Body;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BodyStorage implements Storage {

    protected BlockingQueue<Body> storage;
    protected int size;

    @Override
    public void setSize(int size) {
        this.size = size;
        storage = new ArrayBlockingQueue<>(size);
    }

    public void addToStorage(Body product) {
        try {
            storage.put(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Body getBody() {
        try {
            return storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
