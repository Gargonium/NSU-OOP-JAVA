package ru.nsu.votintsev.view;

import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observable;
import ru.nsu.votintsev.factory.pattern.observer.Observer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ChangeListener, Observable {

    private JSlider accessoriesSlider;
    private JSlider enginesSlider;
    private JSlider bodySlider;
    private JSlider requestSlider;

    private final List<Observer> observers = new ArrayList<>();

    private FactoryFrame factoryFrame;

    public void setFactoryFrame(FactoryFrame factoryFrame) {
        this.factoryFrame = factoryFrame;
        addObserver(factoryFrame);
    }

    public void setSliders(JSlider accessoriesSlider, JSlider enginesSlider, JSlider bodySlider, JSlider requestSlider) {
        this.accessoriesSlider = accessoriesSlider;
        this.enginesSlider = enginesSlider;
        this.bodySlider = bodySlider;
        this.requestSlider = requestSlider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == accessoriesSlider)
            notifyObservers(Changes.UPDATE_ACCESSORIES_SPEED);
        else if (e.getSource() == enginesSlider)
            notifyObservers(Changes.UPDATE_MOTORS_SPEED);
        else if (e.getSource() == bodySlider)
            notifyObservers(Changes.UPDATE_BODY_SPEED);
        else if (e.getSource() == requestSlider)
            notifyObservers(Changes.UPDATE_REQUEST_SPEED);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Changes change) {
        for (Observer observer : observers) {
            observer.update(change);
        }
    }
}
