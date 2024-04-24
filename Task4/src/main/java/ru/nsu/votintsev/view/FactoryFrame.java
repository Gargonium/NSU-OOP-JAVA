package ru.nsu.votintsev.view;

import ru.nsu.votintsev.factory.FactoryController;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class FactoryFrame extends JFrame implements Observer {

    private final JPanel infoPanel = new JPanel();
    private final JLabel carsDoneLabel = new JLabel("Cars Done: 0");
    private final JLabel taskToBeCompleteLabel = new JLabel("Task to be Complete: 0");

    private final JPanel requestPanel = new JPanel();
    private final JLabel requestSpeedLabel = new JLabel("Request Speed");
    private final JSlider requestSlider = new JSlider(0,10,5000,1000);
    private final JLabel requestSliderValue = new JLabel(requestSlider.getValue() + "ms");

    private final JPanel deliveryPanel = new JPanel();
    private final JLabel deliverySpeed = new JLabel("Delivery Speed");

    private final JPanel bodyPanel = new JPanel();
    private final JLabel bodyLabel = new JLabel("Body       ");
    private final JSlider bodySlider = new JSlider(0,10,5000,1000);
    private final JLabel bodySliderValue = new JLabel(bodySlider.getValue() + "ms");

    private final JPanel accessoriesPanel = new JPanel();
    private final JLabel accessoriesLabel = new JLabel("Accessories");
    private final JSlider accessoriesSlider = new JSlider(0,10,5000,1000);
    private final JLabel accessoriesSliderValue = new JLabel(accessoriesSlider.getValue() + "ms");

    private final JPanel motorsPanel = new JPanel();
    private final JLabel motorsLabel = new JLabel("Motors   ");
    private final JSlider motorsSlider = new JSlider(0,10,5000,1000);
    private final JLabel motorsSliderValue = new JLabel(motorsSlider.getValue() + "ms");

    private final Controller controller;
    private final FactoryController factoryController;

    public FactoryFrame(Controller controller, FactoryController factoryController) {
        this.controller = controller;
        this.factoryController = factoryController;
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridLayout(2,2));

        requestSlider.addChangeListener(controller);
        bodySlider.addChangeListener(controller);
        accessoriesSlider.addChangeListener(controller);
        motorsSlider.addChangeListener(controller);

        controller.setSliders(accessoriesSlider, motorsSlider, bodySlider, requestSlider);
        controller.setFactoryFrame(this);

        infoPanel.setLayout(new GridLayout(2,1));
        infoPanel.add(carsDoneLabel);
        infoPanel.add(taskToBeCompleteLabel);

        requestPanel.add(requestSpeedLabel);
        requestPanel.add(requestSlider);
        requestPanel.add(requestSliderValue);

        deliveryPanel.setLayout(new GridLayout(4, 1));
        deliveryPanel.add(deliverySpeed);

        bodyPanel.add(bodyLabel);
        bodyPanel.add(bodySlider);
        bodyPanel.add(bodySliderValue);

        accessoriesPanel.add(accessoriesLabel);
        accessoriesPanel.add(accessoriesSlider);
        accessoriesPanel.add(accessoriesSliderValue);

        motorsPanel.add(motorsLabel);
        motorsPanel.add(motorsSlider);
        motorsPanel.add(motorsSliderValue);

        deliveryPanel.add(bodyPanel);
        deliveryPanel.add(accessoriesPanel);
        deliveryPanel.add(motorsPanel);

        this.add(infoPanel);
        this.add(requestPanel);
        this.add(deliveryPanel);
        this.setVisible(true);
    }

    @Override
    public void update(Changes change) {
        switch (change) {
            case UPDATE_BODY_SPEED -> {
                bodySliderValue.setText(bodySlider.getValue() + "ms");
                factoryController.setBodySpeed(bodySlider.getValue());
            }
            case UPDATE_MOTORS_SPEED -> {
                motorsSliderValue.setText(motorsSlider.getValue() + "ms");
                factoryController.setMotorSpeed(motorsSlider.getValue());
            }
            case UPDATE_ACCESSORIES_SPEED -> {
                accessoriesSliderValue.setText(accessoriesSlider.getValue() + "ms");
                factoryController.setAccessorySpeed(accessoriesSlider.getValue());
            }
            case UPDATE_REQUEST_SPEED -> {
                requestSliderValue.setText(requestSlider.getValue() + "ms");
                factoryController.setDealerSpeed(requestSlider.getValue());
            }
        }
    }
}
