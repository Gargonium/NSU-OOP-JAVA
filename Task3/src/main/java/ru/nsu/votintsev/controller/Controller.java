package ru.nsu.votintsev.controller;

import ru.nsu.votintsev.model.ModelFacade;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    private final ModelFacade modelFacade;

    private boolean moveUp = false;
    private boolean moveRight = false;
    private boolean moveLeft = false;

    public Controller(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_E -> modelFacade.playerInteract();
            case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_SPACE -> moveUp = true;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> moveLeft = true;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> moveRight = true;
        }
        modelFacade.movePlayer(moveUp, moveRight, moveLeft);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_SPACE -> moveUp = false;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> moveLeft = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> moveRight = false;
        }
        modelFacade.movePlayer(moveUp, moveRight, moveLeft);
    }
}
