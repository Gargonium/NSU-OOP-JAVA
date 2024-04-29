package ru.nsu.votintsev.factory;

import lombok.Getter;
import ru.nsu.votintsev.factory.dealer.AutoDealer;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.storage.AccessoryStorage;
import ru.nsu.votintsev.factory.storage.BodyStorage;
import ru.nsu.votintsev.factory.storage.MotorStorage;
import ru.nsu.votintsev.factory.storage.auto.AutoStorage;
import ru.nsu.votintsev.factory.storage.auto.AutoStorageController;
import ru.nsu.votintsev.factory.supplier.AccessorySupplier;
import ru.nsu.votintsev.factory.supplier.BodySupplier;
import ru.nsu.votintsev.factory.supplier.MotorSupplier;
import ru.nsu.votintsev.factory.worker.AutoWorker;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FactoryController implements Observer, Observable {

    private int accessoriesSupplierCount;
    private int dealerCount;
    private int workerCount;
    private boolean logAll;

    private ExecutorService supplierTP;
    private ExecutorService workerTP;
    private ExecutorService dealerTP;

    private final AccessoryStorage accessoryStorage = new AccessoryStorage();
    private final BodyStorage bodyStorage = new BodyStorage();
    private final MotorStorage motorStorage = new MotorStorage();
    private final AutoStorage autoStorage = new AutoStorage();

    private final MotorSupplier motorSupplier;
    private final BodySupplier bodySupplier;
    private final List<AccessorySupplier> accessorySuppliers = new ArrayList<>();

    private final List<AutoWorker> autoWorkers = new ArrayList<>();
    private final AutoStorageController autoStorageController = new AutoStorageController(autoStorage);

    private final List<AutoDealer> autoDealers = new ArrayList<>();

    private Observer observer;

    @Getter
    private AtomicInteger autoProduced = new AtomicInteger(0);
    @Getter
    private AtomicInteger bodyProduced = new AtomicInteger(0);
    @Getter
    private AtomicInteger accessoryProduced = new AtomicInteger(0);
    @Getter
    private AtomicInteger motorProduced = new AtomicInteger(0);
    @Getter
    private AtomicInteger taskInProceed = new AtomicInteger(0);

    public FactoryController() {
        readConfig();
        motorSupplier = new MotorSupplier(motorStorage, logAll);
        motorSupplier.addObserver(this);
        bodySupplier = new BodySupplier(bodyStorage, logAll);
        bodySupplier.addObserver(this);

        autoStorage.addObserver(this);
        accessoryStorage.addObserver(this);
        motorStorage.addObserver(this);
        bodyStorage.addObserver(this);
    }

    public void startFactory() {
        supplierTP = Executors.newFixedThreadPool(accessoriesSupplierCount + 2);
        workerTP = Executors.newFixedThreadPool(workerCount);
        dealerTP = Executors.newFixedThreadPool(dealerCount);

        for (int i = 0; i < accessoriesSupplierCount; i++) {
            AccessorySupplier accessorySupplier = new AccessorySupplier(i, accessoryStorage, logAll);
            accessorySuppliers.add(accessorySupplier);
            accessorySupplier.addObserver(this);
            supplierTP.execute(accessorySupplier);
        }
        supplierTP.execute(motorSupplier);
        supplierTP.execute(bodySupplier);
        for (int i = 0; i < workerCount; i++) {
            AutoWorker autoWorker = new AutoWorker(bodyStorage, accessoryStorage, motorStorage, autoStorage, i, logAll);
            autoWorkers.add(autoWorker);
            autoWorker.addObserver(this);
            workerTP.execute(autoWorker);
        }
        autoStorageController.setAutoWorkers(autoWorkers);
        for (int i = 0; i < dealerCount; i++) {
            AutoDealer autoDealer = new AutoDealer(autoStorage, i);
            autoDealer.addObserver(autoStorageController);
            autoDealers.add(autoDealer);
            dealerTP.execute(autoDealer);
        }
    }

    private void readConfig() {
        Properties prop = new Properties();
        try (BufferedInputStream file = new BufferedInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream("/config.ini")))) {
            prop.load(file);

            accessoryStorage.setSize(Integer.parseInt(prop.getProperty("AccessoryStorageSize")));
            bodyStorage.setSize(Integer.parseInt(prop.getProperty("BodyStorageSize")));
            motorStorage.setSize(Integer.parseInt(prop.getProperty("MotorStorageSize")));
            autoStorage.setSize(Integer.parseInt(prop.getProperty("AutoStorageSize")));

            accessoriesSupplierCount = Integer.parseInt(prop.getProperty("AccessorySuppliersCount"));
            dealerCount = Integer.parseInt(prop.getProperty("DealersCount"));
            workerCount = Integer.parseInt(prop.getProperty("WorkersCount"));

            logAll = Boolean.parseBoolean(prop.getProperty("LogAll"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBodySpeed(int speed) {
        bodySupplier.changeSpeed(speed);
    }

    public void setMotorSpeed(int speed) {
        motorSupplier.changeSpeed(speed);
    }

    public void setAccessorySpeed(int speed) {
        for (AccessorySupplier accessorySupplier : accessorySuppliers) {
            accessorySupplier.changeSpeed(speed);
        }
    }

    public void setDealerSpeed(int speed) {
        for (AutoDealer autoDealer : autoDealers) {
            autoDealer.changeSpeed(speed);
        }
    }

    public void shutdownFactory() {
        for (AccessorySupplier accessorySupplier : accessorySuppliers) {
            accessorySupplier.shutdown();
        }
        motorSupplier.shutdown();
        bodySupplier.shutdown();
        for (AutoWorker autoWorker : autoWorkers) {
            autoWorker.shutdown();
        }
        for (AutoDealer autoDealer : autoDealers) {
            autoDealer.shutdown();
        }

        if (supplierTP != null)
            supplierTP.shutdown();
        if (dealerTP != null)
            dealerTP.shutdown();
        if (workerTP != null)
            workerTP.shutdown();
    }

    @Override
    public void update(Changes change) {
        switch (change) {
            case AUTO_PRODUCED -> {
                autoProduced.incrementAndGet();
                taskInProceed.decrementAndGet();
            }
            case BODY_PRODUCED -> bodyProduced.incrementAndGet();
            case MOTOR_PRODUCED -> motorProduced.incrementAndGet();
            case ACCESSORY_PRODUCED -> accessoryProduced.incrementAndGet();
            case NEED_NEW_AUTO -> taskInProceed.incrementAndGet();
        }
        notifyObservers(change);
    }

    @Override
    public void notifyObservers(Changes change) {
        observer.update(change);
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    public int getAutoStorageOccupancy() {
        return autoStorage.onStorage();
    }

    public int getBodyStorageOccupancy() {
        return bodyStorage.onStorage();
    }

    public int getAccessoryStorageOccupancy() {
        return accessoryStorage.onStorage();
    }

    public int getMotorStorageOccupancy() {
        return motorStorage.onStorage();
    }
}
