package ru.nsu.votintsev.factory;

import ru.nsu.votintsev.factory.dealer.AutoDealer;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FactoryController {

    private int accessoriesSupplierCount;
    private int dealerCount;
    private int workerCount;
    private boolean logAll;

    private ScheduledExecutorService supplierTP;
    private ScheduledExecutorService workerTP;
    private ScheduledExecutorService dealerTP;

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

    public FactoryController() {
        readConfig();
        motorSupplier = new MotorSupplier(motorStorage, logAll);
        bodySupplier = new BodySupplier(bodyStorage, logAll);
    }

    public void startFactory() {
        supplierTP = Executors.newScheduledThreadPool(accessoriesSupplierCount + 2);
        workerTP = Executors.newScheduledThreadPool(workerCount);
        dealerTP = Executors.newScheduledThreadPool(dealerCount);

        for (int i = 0; i < accessoriesSupplierCount; i++) {
            AccessorySupplier accessorySupplier = new AccessorySupplier(i, accessoryStorage, logAll);
            accessorySuppliers.add(accessorySupplier);
            supplierTP.scheduleAtFixedRate(accessorySupplier, 0, 100, TimeUnit.MILLISECONDS);
        }
        supplierTP.scheduleAtFixedRate(motorSupplier, 0, 100, TimeUnit.MILLISECONDS);
        supplierTP.scheduleAtFixedRate(bodySupplier, 0, 100, TimeUnit.MILLISECONDS);
        for (int i = 0; i < workerCount; i++) {
            AutoWorker autoWorker = new AutoWorker(bodyStorage, accessoryStorage, motorStorage, autoStorage, i, logAll);
            autoStorageController.addObserver(autoWorker);
            autoWorkers.add(autoWorker);
            workerTP.scheduleAtFixedRate(autoWorker, 0, 100, TimeUnit.MILLISECONDS);
            //workerTP.execute(new AutoWorker(bodyStorage, accessoryStorage, motorStorage, autoStorage));
        }
        for (int i = 0; i < dealerCount; i++) {
            AutoDealer autoDealer = new AutoDealer(autoStorage, i);
            autoDealer.addObserver(autoStorageController);
            autoDealers.add(autoDealer);
            dealerTP.scheduleAtFixedRate(autoDealer, 0, 100, TimeUnit.MILLISECONDS);
            //dealerTP.execute(new AutoDealer(autoStorage, autoStorageController, i));
        }
        autoStorageController.firstStart();
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
            e.printStackTrace();
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
        if (supplierTP != null)
            supplierTP.shutdown();
        if (dealerTP != null)
            dealerTP.shutdown();
        if (workerTP != null)
            workerTP.shutdown();
    }
}
