package ru.nsu.votintsev.factory.storage;

import ru.nsu.votintsev.factory.product.Body;

public class BodyStorage extends BasicStorage {
    public Body getBody() {
        try {
            return (Body) storage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
