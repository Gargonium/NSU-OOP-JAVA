package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.product.Accessory;
import ru.nsu.votintsev.factory.storage.AccessoryStorage;

public class AccessorySupplier extends BasicSupplier implements Runnable {
    private final int id;
    private final AccessoryStorage accessoriesStorage;
    protected static Integer productId = 0;

    public AccessorySupplier(int id, AccessoryStorage accessoriesStorage) {
        this.id = id;
        this.accessoriesStorage = accessoriesStorage;
        this.speed = 1000;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= speed) {
            synchronized (productId) {
                accessoriesStorage.addToStorage(new Accessory(productId));
                //System.out.println("AccessorySupplier #" + id + " add accessory #" + productId);
                productId++;
            }
            lastTime = currentTime;
        }
    }
}
