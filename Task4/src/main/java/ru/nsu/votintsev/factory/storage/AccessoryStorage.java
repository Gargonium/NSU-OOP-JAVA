package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Accessory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AccessoryStorage implements Storage {
    protected BlockingQueue<Accessory> storage;
    protected int size;

    @Override
    public void setSize(int size) {
        this.size = size;
        storage = new ArrayBlockingQueue<>(size);
    }

    public void addToStorage(Accessory product) {
        try {
            storage.put(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Accessory getAccessory() {
        try {
            return storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
