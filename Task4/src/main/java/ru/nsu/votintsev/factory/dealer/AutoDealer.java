package ru.nsu.votintsev.factory.dealer;

import lombok.RequiredArgsConstructor;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.product.Product;
import ru.nsu.votintsev.factory.storage.auto.AutoStorage;
import ru.nsu.votintsev.factory.storage.auto.AutoStorageController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class AutoDealer implements Dealer, Runnable, Observable {

    private final AutoStorage autoStorage;
    private final AutoStorageController autoStorageController;
    private int speed = 1000;
    private long lastTime = System.currentTimeMillis();
    private final int id;

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= speed) {
            Auto auto = autoStorage.getAuto();
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss a");
            System.out.println(
                    formatForDateNow.format(dateNow) + ": Dealer " + id + ": Auto " + auto.getId() +
                            " (Body: " + auto.getBodyId() + ", Motor: " + auto.getMotorId() + ", Accessory: " + auto.getAccessoryId() + ")");
            notifyObservers(Changes.AUTO_SEND);
            lastTime = currentTime;
        }
    }

    @Override
    public void addObserver(Observer observer) {
    }

    @Override
    public void notifyObservers(Changes change) {
        autoStorageController.update(Changes.AUTO_SEND);
    }

    @Override
    public void changeSpeed(int newSpeed) {
        this.speed = newSpeed;
    }
}
