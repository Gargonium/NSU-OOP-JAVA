package ru.nsu.votintsev.factory.storage.auto;

import lombok.RequiredArgsConstructor;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AutoStorageController implements Observable, Observer {

    private List<Observer> observers = new ArrayList<>();
    private final AutoStorage autoStorage;

    public void firstStart() {
        notifyObservers(Changes.NEED_NEW_AUTO);
    }

    @Override
    public void notifyObservers(Changes change) {
        for (Observer observer : observers) {
            observer.update(change);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
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
