package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Body;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BodyStorage implements Storage, Observable {

    private BlockingQueue<Body> storage;
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

    public void addToStorage(Body product) {
        try {
            storage.put(product);
            notifyObservers(Changes.BODY_STORAGE_UPDATE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Body getBody() {
        try {
            Body body = storage.take();
            notifyObservers(Changes.BODY_STORAGE_UPDATE);
            return body;
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
