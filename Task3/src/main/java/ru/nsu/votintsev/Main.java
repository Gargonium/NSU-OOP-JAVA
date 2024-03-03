package ru.nsu.votintsev;

import ru.nsu.votintsev.controller.Controller;
import ru.nsu.votintsev.model.ModelFacade;
import ru.nsu.votintsev.view.GameFrame;

public class Main {
    public static void main(String[] args) {
        ModelFacade modelFacade = new ModelFacade();
        Controller controller = new Controller(modelFacade);
        new GameFrame(modelFacade, controller);
    }
}