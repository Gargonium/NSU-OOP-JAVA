package ru.nsu.votintsev;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.votintsev.model.ModelFacade;
import ru.nsu.votintsev.view.javafx.GameStage;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ModelFacade modelFacade = new ModelFacade();
        GameStage mainFrame = new GameStage(modelFacade);
        mainFrame.show();
    }
}
