package ru.nsu.votintsev.view;

import ru.nsu.votintsev.controller.Controller;
import ru.nsu.votintsev.model.ModelFacade;
import ru.nsu.votintsev.model.directions.PlayerDirection;
import ru.nsu.votintsev.view.entity.label.*;
import ru.nsu.votintsev.view.sprite.state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GameFrame extends JFrame implements Observer, ActionListener {

    private final PlayerLabel playerLabel = new PlayerLabel();
    private final DoorLabel doorLabel = new DoorLabel();

    private GamePanel gamePanel;

    private final Vector<EnemyLabel> enemiesLabel = new Vector<>();
    private final Vector<WallsPanel> wallsPanels = new Vector<>();

    private final ModelFacade modelFacade;

    private final Timer gameTimer = new Timer(1, this);
    private final Timer animationTimer = new Timer(100, this);

    private int runPlayerAnimationCount = 0;
    private int standAnimationCount = 0;

    private int runEnemyAnimationCount = 0;

    private final GameMenu gameMenu;

    private PlayerDirection lastPlayerDirection = PlayerDirection.RIGHT;

    public GameFrame(ModelFacade modelFacade, Controller controller) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.getSize());

        gameMenu = new GameMenu();

        this.addKeyListener(controller);

        this.modelFacade = modelFacade;
        modelFacade.setPlayerStartCords(100,400);
        this.modelFacade.addObserver(this);

        gamePanel = new GamePanel(this.getWidth(), this.getHeight());
        modelFacade.setGameFieldDimensions(gamePanel.getWidth(), gamePanel.getHeight());

        for (int i = 0; i < modelFacade.getWallsCount(); ++i) {
            WallsPanel wallPanel = new WallsPanel(modelFacade.getWallRect(i));
            wallsPanels.add(wallPanel);
            gamePanel.add(wallPanel);
        }

        for (int i = 0; i < modelFacade.getEnemiesCount(); ++i) {
            EnemyLabel enemyLabel = new EnemyLabel();
            enemyLabel.setLocation(modelFacade.getEnemyX(i), modelFacade.getEnemyY(i));
            modelFacade.setEnemySize(i, enemyLabel.getWidth(), enemyLabel.getHeight());
            enemiesLabel.add(enemyLabel);
            gamePanel.add(enemyLabel);
        }

        modelFacade.setPlayerSize(playerLabel.getWidth(), playerLabel.getHeight());
        modelFacade.setDoorSize(doorLabel.getWidth(), doorLabel.getHeight());

        playerLabel.setBounds(modelFacade.getPlayerX(), modelFacade.getPlayerY(),
                playerLabel.getWidth(), playerLabel.getHeight());

        setPlayerLocation();
        setEnemyLocation();
        doorLabel.setLocation(modelFacade.getDoorX(), modelFacade.getDoorY());

        gameTimer.start();
        animationTimer.start();

        this.add(playerLabel);
        gamePanel.add(doorLabel);

        this.setJMenuBar(gameMenu);
        this.add(gamePanel);
        this.setVisible(true);
    }

    private void setPlayerLocation() {
        playerLabel.setLocation(modelFacade.getPlayerX(), modelFacade.getPlayerY());
    }

    private void setEnemyLocation() {
        for (int i = 0; i < modelFacade.getEnemiesCount(); ++i) {
            enemiesLabel.get(i).setLocation(modelFacade.getEnemyX(i), modelFacade.getEnemyY(i));
        }
    }

    @Override
    public void update(String changes) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTimer) {
            setPlayerLocation();
            setEnemyLocation();
            gamePanel.setX(gamePanel.getX());
            gamePanel.setLocation(gamePanel.getX(), gamePanel.getY());

            modelFacade.setDoorCords(doorLabel.getX() + gamePanel.getX(), doorLabel.getY() + gamePanel.getY());
            for (int i = 0; i < modelFacade.getWallsCount(); ++i) {
                modelFacade.setWallCords(i, wallsPanels.get(i).getX() + gamePanel.getX(), wallsPanels.get(i).getY() + gamePanel.getY());
            }

        }  else if (e.getSource() == animationTimer) {
            switch (modelFacade.getPlayerDirection()) {
                case STAND -> {
                    PlayerSpriteState playerSpriteState = getPlayerStandSprite();
                    standAnimationCount = standAnimationCount == 10 ? 0 : standAnimationCount + 1;
                    playerLabel.updatePlayerSprite(playerSpriteState);
                }
                case LEFT -> {
                    playerLabel.updatePlayerSprite(getPlayerRunLeftSprite());
                    lastPlayerDirection = PlayerDirection.LEFT;
                }
                case RIGHT -> {
                    playerLabel.updatePlayerSprite(getPlayerRunRightSprite());
                    lastPlayerDirection = PlayerDirection.RIGHT;
                }
            }
            for (int i = 0; i < modelFacade.getEnemiesCount(); ++i) {
                switch (modelFacade.getEnemyDirection(i)) {
                    case LEFT -> enemiesLabel.get(i).updateEnemySprite(getEnemyRunLeftSprite());
                    case RIGHT -> enemiesLabel.get(i).updateEnemySprite(getEnemyRunRightSprite());
                }
            }
        }
    }

    private PlayerSpriteState getPlayerStandSprite() {
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

    private PlayerSpriteState getPlayerRunRightSprite() {
        PlayerSpriteState playerSpriteState = null;
        switch (runPlayerAnimationCount) {
            case 0 -> {
                playerSpriteState = PlayerSpriteState.RUN_RIGHT_0;
                runPlayerAnimationCount = 1;
            }
            case 1 -> {
                playerSpriteState = PlayerSpriteState.RUN_RIGHT_1;
                runPlayerAnimationCount = 2;
            }
            case 2 -> {
                playerSpriteState = PlayerSpriteState.RUN_RIGHT_2;
                runPlayerAnimationCount = 0;
            }
        }
        return playerSpriteState;
    }

    private PlayerSpriteState getPlayerRunLeftSprite() {
        PlayerSpriteState playerSpriteState = null;
        switch (runPlayerAnimationCount) {
            case 0 -> {
                playerSpriteState = PlayerSpriteState.RUN_LEFT_0;
                runPlayerAnimationCount = 1;
            }
            case 1 -> {
                playerSpriteState = PlayerSpriteState.RUN_LEFT_1;
                runPlayerAnimationCount = 2;
            }
            case 2 -> {
                playerSpriteState = PlayerSpriteState.RUN_LEFT_2;
                runPlayerAnimationCount = 0;
            }
        }
        return playerSpriteState;
    }

    private EnemySpriteState getEnemyRunRightSprite() {
        EnemySpriteState enemySpriteState = null;
        switch (runEnemyAnimationCount) {
            case 0 -> {
                enemySpriteState = EnemySpriteState.RUN_RIGHT_0;
                runEnemyAnimationCount = 1;
            }
            case 1 -> {
                enemySpriteState = EnemySpriteState.RUN_RIGHT_1;
                runEnemyAnimationCount = 2;
            }
            case 2 -> {
                enemySpriteState = EnemySpriteState.RUN_RIGHT_2;
                runEnemyAnimationCount = 0;
            }
        }
        return enemySpriteState;
    }

    private EnemySpriteState getEnemyRunLeftSprite() {
        EnemySpriteState enemySpriteState = null;
        switch (runEnemyAnimationCount) {
            case 0 -> {
                enemySpriteState = EnemySpriteState.RUN_LEFT_0;
                runEnemyAnimationCount = 1;
            }
            case 1 -> {
                enemySpriteState = EnemySpriteState.RUN_LEFT_1;
                runEnemyAnimationCount = 2;
            }
            case 2 -> {
                enemySpriteState = EnemySpriteState.RUN_LEFT_2;
                runEnemyAnimationCount = 0;
            }
        }
        return enemySpriteState;
    }
}
