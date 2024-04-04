package ru.nsu.votintsev;

import ru.nsu.votintsev.controller.swing.SwingController;
import ru.nsu.votintsev.model.ModelFacade;
import ru.nsu.votintsev.view.swing.GameFrame;

import javax.swing.*;

public class MainSwing {
    public static void main(String[] args) {
        ModelFacade modelFacade = new ModelFacade();
        SwingController controller = new SwingController(modelFacade);
        SwingUtilities.invokeLater(() -> new GameFrame(modelFacade, controller));
    }
}