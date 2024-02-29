package ru.nsu.votintsev.View;

import ru.nsu.votintsev.Controller;
import ru.nsu.votintsev.Model.ModelFacade;
import ru.nsu.votintsev.Observer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractList;
import java.util.ArrayList;

public class GameFrame extends JFrame implements Observer, ActionListener {

    PlayerPanel playerPanel;
    ModelFacade modelFacade;
    Timer timer = new Timer(1, this);

    GameMenu gameMenu;

    public GameFrame(ModelFacade modelFacade, Controller controller) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);

        gameMenu = new GameMenu();

        this.addKeyListener(controller);

        this.modelFacade = modelFacade;
        this.modelFacade.addObserver(this);
        playerPanel = new PlayerPanel();

        for (int i = 0; i < modelFacade.getWallsCount(); ++i) {
            WallsPanel wallPanel = new WallsPanel(modelFacade.getWall(i));
            this.add(wallPanel);
        }

        timer.start();

        modelFacade.setPlayerSize(playerPanel.getPlayerWidth(), playerPanel.getPlayerHeight());

        playerPanel.setBounds(modelFacade.getPlayerX(), modelFacade.getPlayerY(),
                playerPanel.getPlayerWidth(), playerPanel.getPlayerHeight());

        setPlayerLocation();

        this.setJMenuBar(gameMenu);
        this.add(playerPanel);
        this.setVisible(true);
    }

    private void setPlayerLocation() {
        playerPanel.setLocation(modelFacade.getPlayerX(), modelFacade.getPlayerY());
    }

    @Override
    public void update(String changes) {
        switch (changes) {
            case "Change Cords":
                setPlayerLocation();
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            setPlayerLocation();
        }
    }
}
