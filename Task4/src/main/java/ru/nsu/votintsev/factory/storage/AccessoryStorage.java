package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Accessory;

public class AccessoryStorage extends BasicStorage {
    public Accessory getAccesory() {
        try {
            return (Accessory) storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
