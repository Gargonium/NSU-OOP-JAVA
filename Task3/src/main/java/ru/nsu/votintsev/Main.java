package ru.nsu.votintsev;

import ru.nsu.votintsev.Model.ModelFacade;
import ru.nsu.votintsev.View.GameFrame;

public class Main {
    public static void main(String[] args) {
        ModelFacade modelFacade = new ModelFacade();
        Controller controller = new Controller(modelFacade);
        new GameFrame(modelFacade, controller);
    }
}