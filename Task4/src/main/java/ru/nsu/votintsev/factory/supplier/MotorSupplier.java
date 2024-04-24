package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.product.Motor;
import ru.nsu.votintsev.factory.storage.MotorStorage;

public class MotorSupplier extends BasicSupplier implements Runnable {

    private final MotorStorage motorStorage;
    protected static int productId = 0;

    public MotorSupplier(MotorStorage motorStorage) {
        this.motorStorage = motorStorage;
        speed = 2000;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= speed) {
            motorStorage.addToStorage(new Motor(productId));
            System.out.println("MotorSupplier add motor#" + productId);
            productId++;
            lastTime = currentTime;
        }
    }
}
