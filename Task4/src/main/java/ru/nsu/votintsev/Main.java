package ru.nsu.votintsev;

import ru.nsu.votintsev.factory.FactoryController;
import ru.nsu.votintsev.view.Controller;
import ru.nsu.votintsev.view.FactoryFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FactoryController factoryController = new FactoryController();
        Controller controller = new Controller();
        SwingUtilities.invokeLater(() -> new FactoryFrame(controller, factoryController));
    }
}

