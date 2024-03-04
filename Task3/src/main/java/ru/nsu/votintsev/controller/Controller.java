package ru.nsu.votintsev.controller;

import ru.nsu.votintsev.model.ModelFacade;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    ModelFacade modelFacade;

    boolean moveUp = false;
    boolean moveRight = false;
    boolean moveLeft = false;

    public Controller(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_E: modelFacade.playerInteract(); break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_SPACE: moveUp = true; break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: moveLeft = true; break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: moveRight = true; break;
            default: break;
        }
        modelFacade.movePlayer(moveUp, moveRight, moveLeft);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_SPACE:moveUp = false; break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: moveLeft = false; break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: moveRight = false; break;
            default: break;
        }
        modelFacade.movePlayer(moveUp, moveRight, moveLeft);
    }
}
