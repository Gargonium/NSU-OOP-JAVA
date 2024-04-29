package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Accessory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AccessoryStorage implements Storage, Observable {
    private BlockingQueue<Accessory> storage;
    private int size;

    private Observer observer;

    @Override
    public void setSize(int size) {
        this.size = size;
        storage = new ArrayBlockingQueue<>(size);
    }

    @Override
    public int onStorage() {
        return size - storage.remainingCapacity();
    }

    public void addToStorage(Accessory product) {
        try {
            storage.put(product);
            notifyObservers(Changes.ACCESSORY_STORAGE_UPDATE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Accessory getAccessory() {
        try {
            Accessory accessory = storage.take();
            notifyObservers(Changes.ACCESSORY_STORAGE_UPDATE);
            return accessory;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyObservers(Changes change) {
        observer.update(change);
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }
}
