package ru.nsu.votintsev.factory.storage.auto;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.storage.Storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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

    public boolean addToStorage(Auto product) {
        try {
            boolean result = storage.offer(product, 4000, TimeUnit.MILLISECONDS);
            if (result) notifyObservers(Changes.MOTOR_STORAGE_UPDATE);
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFull() {
        return storage.remainingCapacity() == 0;
    }

    public Auto getAuto() throws InterruptedException {
        Auto auto = storage.poll(4000, TimeUnit.MILLISECONDS);
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
