package ru.nsu.votintsev.controller.javafx;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import ru.nsu.votintsev.model.ModelFacade;

public class FXController {

    private final ModelFacade modelFacade;

    private boolean moveUp = false;
    private boolean moveRight = false;
    private boolean moveLeft = false;

    public FXController(Scene scene, ModelFacade modelFacade) {
        this.modelFacade = modelFacade;

        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        scene.setOnKeyReleased(event -> handleKeyRelease(event.getCode()));
    }

    private void handleKeyPress(KeyCode code) {
        switch (code) {
            case KeyCode.E -> modelFacade.playerInteract();
            case KeyCode.UP, KeyCode.W, KeyCode.SPACE -> moveUp = true;
            case KeyCode.LEFT, KeyCode.A -> moveLeft = true;
            case KeyCode.RIGHT, KeyCode.D -> moveRight = true;
            case KeyCode.ESCAPE -> System.exit(0);
        }
        modelFacade.movePlayer(moveUp, moveRight, moveLeft);
    }

    private void handleKeyRelease(KeyCode code) {
        switch (code) {
            case KeyCode.UP, KeyCode.W, KeyCode.SPACE -> moveUp = false;
            case KeyCode.LEFT, KeyCode.A -> moveLeft = false;
            case KeyCode.RIGHT, KeyCode.D -> moveRight = false;
        }
        modelFacade.movePlayer(moveUp, moveRight, moveLeft);
    }

}
