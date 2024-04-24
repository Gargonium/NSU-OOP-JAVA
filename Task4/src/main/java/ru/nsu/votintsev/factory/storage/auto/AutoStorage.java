package ru.nsu.votintsev.factory.storage.auto;

import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.storage.BasicStorage;

public class AutoStorage extends BasicStorage {
    public boolean isFull() {
        return storage.remainingCapacity() == 0;
    }

    public Auto getAuto() {
        try {
            return (Auto) storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
