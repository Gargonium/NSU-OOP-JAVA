package ru.nsu.votintsev.View;

import ru.nsu.votintsev.Controller.Controller;
import ru.nsu.votintsev.Model.ModelFacade;
import ru.nsu.votintsev.Model.PlayerDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements Observer, ActionListener {

    PlayerPanel playerPanel;
    DoorPanel doorPanel;
    ModelFacade modelFacade;
    Timer timer = new Timer(1, this);
    Timer playerAnimation = new Timer(100, this);

    private int runAnimationCount = 0;
    private int standAnimationCount = 0;

    GameMenu gameMenu;

    private PlayerDirection lastPlayerDirection = PlayerDirection.RIGHT;

    public GameFrame(ModelFacade modelFacade, Controller controller) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.getSize());

        gameMenu = new GameMenu();

        this.addKeyListener(controller);

        this.modelFacade = modelFacade;
        modelFacade.setPlayerStartCords(0,400);
        this.modelFacade.addObserver(this);
        playerPanel = new PlayerPanel();
        doorPanel = new DoorPanel();

        for (int i = 0; i < modelFacade.getWallsCount(); ++i) {
            WallsPanel wallPanel = new WallsPanel(modelFacade.getWall(i));
            this.add(wallPanel);
        }

        timer.start();
        playerAnimation.start();

        modelFacade.setPlayerSize(playerPanel.getPlayerWidth(), playerPanel.getPlayerHeight());
        modelFacade.setDoorSize(doorPanel.getWidth(), doorPanel.getHeight());
        modelFacade.setGameFieldDimensions(this.getWidth(), this.getHeight());


        playerPanel.setBounds(modelFacade.getPlayerX(), modelFacade.getPlayerY(),
                playerPanel.getPlayerWidth(), playerPanel.getPlayerHeight());

        setPlayerLocation();

        doorPanel.setLocation(modelFacade.getDoorX(), modelFacade.getDoorY());

        this.setJMenuBar(gameMenu);
        this.add(playerPanel);
        this.add(doorPanel);
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
        }  else if (e.getSource() == playerAnimation) {
            switch (modelFacade.getPlayerDirection()) {
                case STAND -> {
                    PlayerSpriteState playerSpriteState = getStandSprite();
                    standAnimationCount = standAnimationCount == 10 ? 0 : standAnimationCount + 1;
                    playerPanel.updatePlayerSprite(playerSpriteState);
                }
                case LEFT -> {
                    playerPanel.updatePlayerSprite(getRunLeftSprite());
                    lastPlayerDirection = PlayerDirection.LEFT;
                }
                case RIGHT -> {
                    playerPanel.updatePlayerSprite(getRunRightSprite());
                    lastPlayerDirection = PlayerDirection.RIGHT;
                }
            }
        }
    }

    private PlayerSpriteState getStandSprite() {
        PlayerSpriteState playerSpriteState = null;

        if (standAnimationCount <= 5) {
            if (lastPlayerDirection == PlayerDirection.LEFT) {
                playerSpriteState = PlayerSpriteState.STAND_LEFT_0;
            } else if (lastPlayerDirection == PlayerDirection.RIGHT) {
                playerSpriteState = PlayerSpriteState.STAND_RIGHT_0;
            }
        } else {
            if (lastPlayerDirection == PlayerDirection.LEFT) {
                playerSpriteState = PlayerSpriteState.STAND_LEFT_1;
            } else if (lastPlayerDirection == PlayerDirection.RIGHT) {
                playerSpriteState = PlayerSpriteState.STAND_RIGHT_1;
            }
        }
        return playerSpriteState;
    }

    private PlayerSpriteState getRunRightSprite() {
        PlayerSpriteState playerSpriteState = null;
        switch (runAnimationCount) {
            case 0 -> {
                playerSpriteState = PlayerSpriteState.RUN_RIGHT_0;
                runAnimationCount = 1;
            }
            case 1 -> {
                playerSpriteState = PlayerSpriteState.RUN_RIGHT_1;
                runAnimationCount = 2;
            }
            case 2 -> {
                playerSpriteState = PlayerSpriteState.RUN_RIGHT_2;
                runAnimationCount = 0;
            }
        }
        return playerSpriteState;
    }

    private PlayerSpriteState getRunLeftSprite() {
        PlayerSpriteState playerSpriteState = null;
        switch (runAnimationCount) {
            case 0 -> {
                playerSpriteState = PlayerSpriteState.RUN_LEFT_0;
                runAnimationCount = 1;
            }
            case 1 -> {
                playerSpriteState = PlayerSpriteState.RUN_LEFT_1;
                runAnimationCount = 2;
            }
            case 2 -> {
                playerSpriteState = PlayerSpriteState.RUN_LEFT_2;
                runAnimationCount = 0;
            }
        }
        return playerSpriteState;
    }
}
