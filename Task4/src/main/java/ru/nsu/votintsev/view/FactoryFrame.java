package ru.nsu.votintsev.view;

import ru.nsu.votintsev.factory.FactoryController;
import ru.nsu.votintsev.factory.pattern.observer.Changes;
import ru.nsu.votintsev.factory.pattern.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FactoryFrame extends JFrame implements Observer {

    private final JLabel taskToBeCompleteLabel = new JLabel("Task to be Complete: 0");

    private final JLabel requestSpeedLabel = new JLabel("Request: onStorage: 0 Produced: 0");
    private final JSlider requestSlider = new JSlider(0,100,5000,1000);
    private final JLabel requestSliderValue = new JLabel(requestSlider.getValue() + "ms");

    private final JLabel deliverySpeed = new JLabel("Delivery Speed");

    private final JLabel bodyLabel = new JLabel("Body: onStorage: 0 Produced: 0");
    private final JSlider bodySlider = new JSlider(0,100,5000,1000);
    private final JLabel bodySliderValue = new JLabel(bodySlider.getValue() + "ms");

    private final JLabel accessoriesLabel = new JLabel("Accessory: onStorage: 0 Produced: 0");
    private final JSlider accessoriesSlider = new JSlider(0,100,5000,1000);
    private final JLabel accessoriesSliderValue = new JLabel(accessoriesSlider.getValue() + "ms");

    private final JLabel motorsLabel = new JLabel("Motor: onStorage: 0 Produced: 0");
    private final JSlider motorsSlider = new JSlider(0,100,5000,1000);
    private final JLabel motorsSliderValue = new JLabel(motorsSlider.getValue() + "ms");

    private final JButton factoryControlButton = new JButton("Start Factory");

    private final Controller controller;
    private final FactoryController factoryController;

    public FactoryFrame(Controller controller, FactoryController factoryController) {
        this.controller = controller;
        this.factoryController = factoryController;
        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Factory");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                factoryController.shutdownFactory();
                System.exit(0);
            }
        });

        requestSlider.addChangeListener(controller);
        bodySlider.addChangeListener(controller);
        accessoriesSlider.addChangeListener(controller);
        motorsSlider.addChangeListener(controller);

        factoryControlButton.addActionListener(controller);
        factoryControlButton.setFocusable(false);

        controller.setSliders(accessoriesSlider, motorsSlider, bodySlider, requestSlider);
        controller.addObserver(this);
        controller.setFactoryController(factoryController);
        controller.setButton(factoryControlButton);

        factoryControlButton.setBounds(400, 400, 150, 50);

        bodyLabel.setBounds(0, 0, 400, 50);
        bodySlider.setBounds(0, 50, 400, 50);
        bodySliderValue.setBounds(400, 50, 200, 50);

        motorsLabel.setBounds(0, 100, 400, 50);
        motorsSlider.setBounds(0, 150, 400, 50);
        motorsSliderValue.setBounds(400, 150, 200, 50);

        accessoriesLabel.setBounds(0, 200, 400, 50);
        accessoriesSlider.setBounds(0, 250, 400, 50);
        accessoriesSliderValue.setBounds(400, 250, 200, 50);

        requestSpeedLabel.setBounds(0, 300, 400, 50);
        requestSlider.setBounds(0, 350, 400, 50);
        requestSliderValue.setBounds(400, 350,200, 50);

        taskToBeCompleteLabel.setBounds(0, 400, 400, 50);

        this.add(factoryControlButton);

        this.add(bodyLabel);
        this.add(bodySlider);
        this.add(bodySliderValue);

        this.add(motorsLabel);
        this.add(motorsSlider);
        this.add(motorsSliderValue);

        this.add(accessoriesLabel);
        this.add(accessoriesSlider);
        this.add(accessoriesSliderValue);

        this.add(requestSpeedLabel);
        this.add(requestSlider);
        this.add(requestSliderValue);

        this.add(taskToBeCompleteLabel);

        this.setVisible(true);
    }

    @Override
    public void update(Changes change) {
        switch (change) {
            case UPDATE_BODY_SPEED -> bodySliderValue.setText(bodySlider.getValue() + "ms");
            case UPDATE_MOTORS_SPEED -> motorsSliderValue.setText(motorsSlider.getValue() + "ms");
            case UPDATE_ACCESSORIES_SPEED -> accessoriesSliderValue.setText(accessoriesSlider.getValue() + "ms");
            case UPDATE_REQUEST_SPEED -> requestSliderValue.setText(requestSlider.getValue() + "ms");
        }
    }
}
