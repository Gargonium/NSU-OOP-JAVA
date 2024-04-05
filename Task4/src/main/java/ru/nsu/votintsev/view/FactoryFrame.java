package ru.nsu.votintsev.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class FactoryFrame extends JFrame implements ChangeListener {

    private final JPanel infoPanel = new JPanel();
    private final JLabel carsDone = new JLabel("Cars Done: 0");
    private final JLabel taskToBeComplete = new JLabel("Task to be Complete: 0");

    private final JPanel requestPanel = new JPanel();
    private final JLabel requestSpeed = new JLabel("Request Speed");
    private final JSlider requestSlider = new JSlider(0,0,1000,0);
    private final JLabel requestSliderValue = new JLabel("0");

    private final JPanel deliveryPanel = new JPanel();
    private final JLabel deliverySpeed = new JLabel("Delivery Speed");

    private final JPanel bodyworksPanel = new JPanel();
    private final JLabel bodyworks = new JLabel("BodyWorks");
    private final JSlider bodyworksSlider = new JSlider(0,0,1000,0);
    private final JLabel bodyworksSliderValue = new JLabel("0");

    private final JPanel accessoriesPanel = new JPanel();
    private final JLabel accessories = new JLabel("Accessories");
    private final JSlider accessoriesSlider = new JSlider(0,0,1000,0);
    private final JLabel accessoriesSliderValue = new JLabel("0");

    private final JPanel enginesPanel = new JPanel();
    private final JLabel engines = new JLabel("Engines");
    private final JSlider enginesSlider = new JSlider(0,0,1000,0);
    private final JLabel enginesSliderValue = new JLabel("0");

    public FactoryFrame() {
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridLayout(2,2));

        requestSlider.addChangeListener(this);
        bodyworksSlider.addChangeListener(this);
        accessoriesSlider.addChangeListener(this);
        enginesSlider.addChangeListener(this);

        infoPanel.setLayout(new GridLayout(2,1));
        infoPanel.add(carsDone);
        infoPanel.add(taskToBeComplete);

        requestPanel.add(requestSpeed);
        requestPanel.add(requestSlider);
        requestPanel.add(requestSliderValue);

        deliveryPanel.setLayout(new GridLayout(4, 1));
        deliveryPanel.add(deliverySpeed);

        bodyworksPanel.add(bodyworks);
        bodyworksPanel.add(bodyworksSlider);
        bodyworksPanel.add(bodyworksSliderValue);

        accessoriesPanel.add(accessories);
        accessoriesPanel.add(accessoriesSlider);
        accessoriesPanel.add(accessoriesSliderValue);

        enginesPanel.add(engines);
        enginesPanel.add(enginesSlider);
        enginesPanel.add(enginesSliderValue);

        deliveryPanel.add(bodyworksPanel);
        deliveryPanel.add(accessoriesPanel);
        deliveryPanel.add(enginesPanel);

        this.add(infoPanel);
        this.add(requestPanel);
        this.add(deliveryPanel);
        this.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == accessoriesSlider)
            accessoriesSliderValue.setText(String.valueOf(accessoriesSlider.getValue()));
        else if (e.getSource() == enginesSlider)
            enginesSliderValue.setText(String.valueOf(enginesSlider.getValue()));
        else if (e.getSource() == bodyworksSlider)
            bodyworksSliderValue.setText(String.valueOf(bodyworksSlider.getValue()));
        else if (e.getSource() == requestSlider)
            requestSliderValue.setText(String.valueOf(requestSlider.getValue()));
    }
}
