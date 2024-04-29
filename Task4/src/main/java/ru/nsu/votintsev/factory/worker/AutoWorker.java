package ru.nsu.votintsev.factory.worker;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Accessory;
import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.product.Body;
import ru.nsu.votintsev.factory.product.Motor;
import ru.nsu.votintsev.factory.storage.AccessoryStorage;
import ru.nsu.votintsev.factory.storage.BodyStorage;
import ru.nsu.votintsev.factory.storage.MotorStorage;
import ru.nsu.votintsev.factory.storage.auto.AutoStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoWorker implements Worker, Runnable, Observable {

    private final BodyStorage bodyStorage;
    private final AccessoryStorage accessoryStorage;
    private final MotorStorage motorStorage;
    private final AutoStorage autoStorage;

    private int productId;
    private static final AtomicInteger lastProductId = new AtomicInteger(-1);
    private final int id;

    private final List<Observer> observers = new ArrayList<>();

    private final boolean logging;
    private boolean isWaiting = false;
    private boolean isRunning = true;

    public AutoWorker(BodyStorage bodyStorage, AccessoryStorage accessoryStorage, MotorStorage motorStorage, AutoStorage autoStorage, int id, boolean logging) {
        this.bodyStorage = bodyStorage;
        this.accessoryStorage = accessoryStorage;
        this.motorStorage = motorStorage;
        this.autoStorage = autoStorage;
        this.id = id;
        this.logging = logging;
        productId = lastProductId.incrementAndGet();
    }

    public boolean isWaiting() {return isWaiting;}

    public void shutdown() {isRunning = false;}

    @Override
    public void run() {
        while (isRunning) {
            isWaiting = false;
            notifyObservers(Changes.START_PRODUCING_AUTO);
            Body body = bodyStorage.getBody();
            Accessory accessory = accessoryStorage.getAccessory();
            Motor motor = motorStorage.getMotor();
            if (logging)
                System.out.println("AutoWorker #" + id + " add auto #" + productId);
            productId = lastProductId.incrementAndGet();
            synchronized (this) {
                isWaiting = true;
                autoStorage.addToStorage(new Auto(body.getId(), motor.getId(), accessory.getId(), productId));
                notifyObservers(Changes.AUTO_PRODUCED);
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void notifyObservers(Changes change) {
        for (Observer observer : observers)
            observer.update(change);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
}