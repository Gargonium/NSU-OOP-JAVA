package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.product.Accessory;
import ru.nsu.votintsev.factory.storage.BodyStorage;

public class BodySupplier extends BasicSupplier implements Runnable {

    private final BodyStorage bodyStorage;
    protected static int productId = 0;

    public BodySupplier(BodyStorage bodyStorage) {
        this.bodyStorage = bodyStorage;
        speed = 2000;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= speed) {
            bodyStorage.addToStorage(new Accessory(productId));
            //System.out.println("BodySupplier add body#" + productId);
            productId++;
            lastTime = currentTime;
        }
    }
}
