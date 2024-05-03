package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Motor;
import ru.nsu.votintsev.factory.storage.MotorStorage;

public class MotorSupplier extends BasicSupplier implements Runnable, Observable {

    private final MotorStorage motorStorage;
    protected static int productId = 0;

    private Observer observer;

    private final boolean logging;
    private boolean isRunning = true;

    public MotorSupplier(MotorStorage motorStorage, boolean logging) {
        this.motorStorage = motorStorage;
        this.logging = logging;
        speed = 2000;
    }

    public void shutdown() {isRunning = false;}

    @Override
    public void run() {
        while (isRunning && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                break;
            }
            if (!motorStorage.addToStorage(new Motor(productId)))
                continue;
            if (logging)
                System.out.println("MotorSupplier add motor#" + productId);
            productId++;
            notifyObservers(Changes.MOTOR_PRODUCED);
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
