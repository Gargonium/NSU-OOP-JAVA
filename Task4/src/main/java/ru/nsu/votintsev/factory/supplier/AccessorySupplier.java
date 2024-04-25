package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.product.Accessory;
import ru.nsu.votintsev.factory.storage.AccessoryStorage;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessorySupplier extends BasicSupplier implements Runnable {
    private final int id;
    private final AccessoryStorage accessoriesStorage;
    private int productId;
    private static AtomicInteger lastProductId = new AtomicInteger(-1);

    private final boolean logging;

    public AccessorySupplier(int id, AccessoryStorage accessoriesStorage, boolean logging) {
        this.id = id;
        this.accessoriesStorage = accessoriesStorage;
        this.logging = logging;
        this.speed = 1000;
        productId = lastProductId.incrementAndGet();
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= speed) {
            accessoriesStorage.addToStorage(new Accessory(productId));
            if (logging)
                System.out.println("AccessorySupplier #" + id + " add accessory #" + productId);
            productId = lastProductId.incrementAndGet();
            lastTime = currentTime;
        }
    }
}
