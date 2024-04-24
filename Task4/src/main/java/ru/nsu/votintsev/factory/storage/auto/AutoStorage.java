package ru.nsu.votintsev.factory.storage.auto;

import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.storage.Storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AutoStorage implements Storage {

    protected BlockingQueue<Auto> storage;
    protected int size;

    @Override
    public void setSize(int size) {
        this.size = size;
        storage = new ArrayBlockingQueue<>(size);
    }

    public void addToStorage(Auto product) {
        try {
            storage.put(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isFull() {
        return storage.remainingCapacity() == 0;
    }

    public Auto getAuto() {
        try {
            return storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
