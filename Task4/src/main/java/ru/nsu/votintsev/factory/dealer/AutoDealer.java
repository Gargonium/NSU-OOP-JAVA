package ru.nsu.votintsev.factory.dealer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.product.Auto;
import ru.nsu.votintsev.factory.storage.auto.AutoStorage;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class AutoDealer implements Dealer, Runnable, Observable {

    private final AutoStorage autoStorage;
    private Observer observer;
    private int speed = 1000;
    private final int id;

    private boolean isRunning = true;

    public void shutdown() {isRunning = false;}

    @SneakyThrows
    @Override
    public void run() {
        while (isRunning) {
            Thread.sleep(speed);
            Auto auto = autoStorage.getAuto();
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss a");
            System.out.println(
                    formatForDateNow.format(dateNow) + ": Dealer " + id + ": Auto " + auto.getId() +
                            " (Body: " + auto.getBodyId() + ", Motor: " + auto.getMotorId() + ", Accessory: " + auto.getAccessoryId() + ")");
            notifyObservers(Changes.AUTO_SEND);
        }
    }

    @Override
    public void notifyObservers(Changes change) {
        observer.update(Changes.AUTO_SEND);
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void changeSpeed(int newSpeed) {
        this.speed = newSpeed;
    }
}
