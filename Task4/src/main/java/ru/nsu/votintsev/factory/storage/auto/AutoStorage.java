package ru.nsu.votintsev.factory.storage.auto;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.storage.Storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AutoStorage implements Storage, Observable {

    private BlockingQueue<Auto> storage;
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

    public void addToStorage(Auto product) {
        try {
            storage.put(product);
            notifyObservers(Changes.AUTO_STORAGE_UPDATE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFull() {
        return storage.remainingCapacity() == 0;
    }

    public Auto getAuto() throws InterruptedException {
        Auto auto = storage.take();
        notifyObservers(Changes.AUTO_STORAGE_UPDATE);
        return auto;
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
