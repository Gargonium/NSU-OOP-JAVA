package ru.nsu.votintsev.factory.worker;

import lombok.SneakyThrows;
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

    @SneakyThrows
    @Override
    public void run() {
        while (isRunning) {
            isWaiting = false;
            notifyObservers(Changes.NEED_NEW_AUTO);
            Body body = bodyStorage.getBody();
            Accessory accessory = accessoryStorage.getAccessory();
            Motor motor = motorStorage.getMotor();
            autoStorage.addToStorage(new Auto(body.getId(), motor.getId(), accessory.getId(), productId));
            if (logging)
                System.out.println("AutoWorker #" + id + " add auto #" + productId);
            productId = lastProductId.incrementAndGet();
            notifyObservers(Changes.AUTO_PRODUCED);
            synchronized (this) {
                isWaiting = true;
                this.wait();
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