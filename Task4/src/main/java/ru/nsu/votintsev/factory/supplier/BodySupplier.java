package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.product.Body;
import ru.nsu.votintsev.factory.storage.BodyStorage;

public class BodySupplier extends BasicSupplier implements Runnable {

    private final BodyStorage bodyStorage;
    protected static int productId = 0;

    private final boolean logging;

    public BodySupplier(BodyStorage bodyStorage, boolean logging) {
        this.bodyStorage = bodyStorage;
        this.logging = logging;
        speed = 2000;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= speed) {
            bodyStorage.addToStorage(new Body(productId));
            if (logging)
                System.out.println("BodySupplier add body#" + productId);
            productId++;
            lastTime = currentTime;
        }
    }
}
