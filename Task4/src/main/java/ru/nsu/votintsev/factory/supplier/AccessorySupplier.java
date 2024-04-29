package ru.nsu.votintsev.factory.supplier;

import lombok.SneakyThrows;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Accessory;
import ru.nsu.votintsev.factory.storage.AccessoryStorage;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessorySupplier extends BasicSupplier implements Runnable, Observable {
    private final int id;
    private final AccessoryStorage accessoriesStorage;
    private int productId;
    private static final AtomicInteger lastProductId = new AtomicInteger(-1);

    private Observer observer;

    private final boolean logging;
    private boolean isRunning = true;

    public AccessorySupplier(int id, AccessoryStorage accessoriesStorage, boolean logging) {
        this.id = id;
        this.accessoriesStorage = accessoriesStorage;
        this.logging = logging;
        this.speed = 1000;
        productId = lastProductId.incrementAndGet();
    }

    public void shutdown() {isRunning = false;}

    @SneakyThrows
    @Override
    public void run() {
        while (isRunning) {
            Thread.sleep(speed);
            accessoriesStorage.addToStorage(new Accessory(productId));
            if (logging)
                System.out.println("AccessorySupplier #" + id + " add accessory #" + productId);
            productId = lastProductId.incrementAndGet();
            notifyObservers(Changes.ACCESSORY_PRODUCED);
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
