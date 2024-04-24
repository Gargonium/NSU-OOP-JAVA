package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Motor;

public class MotorStorage extends BasicStorage {
    public Motor getMotor() {
        try {
            return (Motor) storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
