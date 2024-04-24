package ru.nsu.votintsev;

import ru.nsu.votintsev.factory.FactoryController;
import ru.nsu.votintsev.view.FactoryFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FactoryFrame::new);
        FactoryController factoryController = new FactoryController();
    }
}

