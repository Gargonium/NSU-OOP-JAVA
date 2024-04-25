package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Motor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MotorStorage implements Storage, Observable {
    private BlockingQueue<Motor> storage;
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

    public void addToStorage(Motor product) {
        try {
            storage.put(product);
            notifyObservers(Changes.MOTOR_STORAGE_UPDATE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Motor getMotor() {
        try {
            Motor motor = storage.take();
            notifyObservers(Changes.MOTOR_STORAGE_UPDATE);
            return motor;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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
