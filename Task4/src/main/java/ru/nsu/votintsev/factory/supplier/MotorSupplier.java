package ru.nsu.votintsev.factory.supplier;

import ru.nsu.votintsev.factory.product.Accessory;
import ru.nsu.votintsev.factory.storage.MotorStorage;

public class MotorSupplier extends BasicSupplier implements Runnable {

    private final MotorStorage motorStorage;
    protected static int productId = 0;

    public MotorSupplier(MotorStorage motorStorage) {
        this.motorStorage = motorStorage;
        speed = 1000;
    }

    @Override
    public void run() {
        lastTime = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - lastTime == speed) {
                motorStorage.addToStorage(new Accessory(productId));
                System.out.println("MotorSupplier add motor#" + productId);
                productId++;
                lastTime = System.currentTimeMillis();
            }
        }
    }
}
