package ru.nsu.votintsev;

import ru.nsu.votintsev.controller.Controller;
import ru.nsu.votintsev.model.ModelFacade;
import ru.nsu.votintsev.view.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ModelFacade modelFacade = new ModelFacade();
        Controller controller = new Controller(modelFacade);
        SwingUtilities.invokeLater(() -> new GameFrame(modelFacade, controller));
    }
}