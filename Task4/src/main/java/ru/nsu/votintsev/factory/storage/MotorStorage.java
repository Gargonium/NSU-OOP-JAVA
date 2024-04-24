package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Motor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MotorStorage implements Storage {
    protected BlockingQueue<Motor> storage;
    protected int size;

    @Override
    public void setSize(int size) {
        this.size = size;
        storage = new ArrayBlockingQueue<>(size);
    }

    public void addToStorage(Motor product) {
        try {
            storage.put(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Motor getMotor() {
        try {
            return storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
