package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Body;
import ru.nsu.votintsev.factory.storage.BodyStorage;

public class BodySupplier extends BasicSupplier implements Runnable, Observable {

    private final BodyStorage bodyStorage;
    protected static int productId = 0;

    private Observer observer;

    private final boolean logging;
    private boolean isRunning = true;

    public BodySupplier(BodyStorage bodyStorage, boolean logging) {
        this.bodyStorage = bodyStorage;
        this.logging = logging;
        speed = 2000;
    }

    public void shutdown() {isRunning = false;}

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bodyStorage.addToStorage(new Body(productId));
            if (logging)
                System.out.println("BodySupplier add body#" + productId);
            productId++;
            notifyObservers(Changes.BODY_PRODUCED);
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
