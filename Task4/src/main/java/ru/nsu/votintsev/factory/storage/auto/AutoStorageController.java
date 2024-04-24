package ru.nsu.votintsev.factory.storage.auto;

import lombok.AllArgsConstructor;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;
import ru.nsu.votintsev.factory.worker.AutoWorker;

import java.util.List;

@AllArgsConstructor
public class AutoStorageController implements Observable, Observer {

    private List<AutoWorker> autoWorkers;
    private AutoStorage autoStorage;

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(Changes change) {
        for (AutoWorker autoWorker : autoWorkers) {
            autoWorker.update(change);
        }
    }

    @Override
    public void update(Changes change) {
        if (change == Changes.AUTO_SEND) {
            if (!autoStorage.isFull()) {
                notifyObservers(Changes.NEED_NEW_AUTO);
            }
        }
    }
}
