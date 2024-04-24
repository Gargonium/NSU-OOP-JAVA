package ru.nsu.votintsev.factory;

import ru.nsu.votintsev.factory.dealer.AutoDealer;
import ru.nsu.votintsev.factory.storage.AccessoryStorage;
import ru.nsu.votintsev.factory.storage.AutoStorage;
import ru.nsu.votintsev.factory.storage.BodyStorage;
import ru.nsu.votintsev.factory.storage.MotorStorage;
import ru.nsu.votintsev.factory.supplier.AccessorySupplier;
import ru.nsu.votintsev.factory.supplier.BodySupplier;
import ru.nsu.votintsev.factory.supplier.MotorSupplier;
import ru.nsu.votintsev.factory.worker.AutoWorker;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FactoryController {
    private final ExecutorService supplierTP;
    private final ExecutorService workerTP;
    private final ExecutorService dealerTP;

    private final AccessoryStorage accessoryStorage = new AccessoryStorage();
    private final BodyStorage bodyStorage = new BodyStorage();
    private final MotorStorage motorStorage = new MotorStorage();
    private final AutoStorage autoStorage = new AutoStorage();

    private int accessoriesSupplierCount;
    private int dealerCount;
    private int workerCount;

    public FactoryController() {
        readConfig();

        supplierTP = Executors.newFixedThreadPool(accessoriesSupplierCount + 2);
        workerTP = Executors.newFixedThreadPool(workerCount);
        dealerTP = Executors.newFixedThreadPool(dealerCount);

        for (int i = 0; i < accessoriesSupplierCount; i++) {
            supplierTP.submit(new AccessorySupplier(i, accessoryStorage));
        }
        supplierTP.submit(new MotorSupplier(motorStorage));
        supplierTP.submit(new BodySupplier(bodyStorage));
        for (int i = 0; i < workerCount; i++) {
            workerTP.submit(new AutoWorker());
        }
        for (int i = 0; i < dealerCount; i++) {
            dealerTP.submit(new AutoDealer());
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

            accessoriesSupplierCount = Integer.parseInt(prop.getProperty("AccessorySuppliers"));
            dealerCount = Integer.parseInt(prop.getProperty("Dealers"));
            workerCount = Integer.parseInt(prop.getProperty("Workers"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
