package ru.nsu.votintsev.factory.worker;

import lombok.RequiredArgsConstructor;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Accessory;
import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.product.Body;
import ru.nsu.votintsev.factory.product.Motor;
import ru.nsu.votintsev.factory.storage.AccessoryStorage;
import ru.nsu.votintsev.factory.storage.BodyStorage;
import ru.nsu.votintsev.factory.storage.MotorStorage;
import ru.nsu.votintsev.factory.storage.auto.AutoStorage;

@RequiredArgsConstructor
public class AutoWorker implements Worker, Runnable, Observer {

    private final BodyStorage bodyStorage;
    private final AccessoryStorage accessoryStorage;
    private final MotorStorage motorStorage;
    private final AutoStorage autoStorage;

    private static Integer productId = 0;
    private final int id;

    @Override
    public void run() {

    }

    @Override
    public void update(Changes change) {
        if (change == Changes.NEED_NEW_AUTO) {
            Body body = bodyStorage.getBody();
            Accessory accessory = accessoryStorage.getAccesory();
            Motor motor = motorStorage.getMotor();
            synchronized (productId) {
                autoStorage.addToStorage(new Auto(body.getId(), motor.getId(), accessory.getId(), productId));
                System.out.println("AutoWorker #" + id + " add auto #" + productId);
                productId++;
            }
        }
    }
}