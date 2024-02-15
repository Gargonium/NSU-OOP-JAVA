package ru.nsu.votintsev;

import ru.nsu.votintsev.Model.ModelFacade;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    ModelFacade modelFacade;

    boolean moveUp = false;
    boolean moveDown = false;
    boolean moveRight = false;
    boolean moveLeft = false;

    public Controller(ModelFacade modelFacade) {

    }

    private void move(ModelFacade modelFacade) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w': moveUp = true;
                System.out.println("Up"); break;
            case 'a': moveLeft = true; break;
            case 's': moveDown = true; break;
            case 'd': moveRight = true; break;
            default: break;
        }
        modelFacade.movePlayer(moveUp, moveDown, moveRight, moveLeft);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
