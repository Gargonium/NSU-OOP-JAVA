package ru.nsu.votintsev.View;

import ru.nsu.votintsev.Controller;
import ru.nsu.votintsev.Model.ModelFacade;
import ru.nsu.votintsev.Observer;

import javax.swing.*;

public class GameFrame extends JFrame implements Observer {

    PlayerPanel playerPanel;

    public GameFrame(ModelFacade modelFacade, Controller controller) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(null);

        this.addKeyListener(controller);

        modelFacade.addObserver(this);
        playerPanel = new PlayerPanel();

        playerPanel.setBounds(modelFacade.getPlayerX(), modelFacade.getPlayerY(),
              playerPanel.getPlayerWidth(), playerPanel.getPlayerHeight());

        this.add(playerPanel);
        this.setVisible(true);
    }

    @Override
    public void update(String changes) {
        switch (changes) {
            case "Change Cords": repaint(); break;
        }
    }
}
