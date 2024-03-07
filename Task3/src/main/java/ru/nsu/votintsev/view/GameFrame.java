package ru.nsu.votintsev.view;

import ru.nsu.votintsev.controller.Controller;
import ru.nsu.votintsev.model.Changes;
import ru.nsu.votintsev.model.ModelFacade;
import ru.nsu.votintsev.model.Observer;
import ru.nsu.votintsev.model.directions.PlayerDirection;
import ru.nsu.votintsev.view.entity.label.DoorLabel;
import ru.nsu.votintsev.view.entity.label.EnemyLabel;
import ru.nsu.votintsev.view.entity.label.PlayerLabel;
import ru.nsu.votintsev.view.entity.label.WallsLabel;
import ru.nsu.votintsev.view.sprite.state.EnemySpriteState;
import ru.nsu.votintsev.view.sprite.state.PlayerSpriteState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame implements Observer, ActionListener {

    private final ViewScaleInator viewScaleInator = new ViewScaleInator();

    private final PlayerLabel playerLabel = new PlayerLabel(viewScaleInator);
    private final List<EnemyLabel> enemiesLabels = new ArrayList<>();

    private final ModelFacade modelFacade;

    private final Timer animationTimer = new Timer(100, this);

    private int runPlayerAnimationCount = 0;
    private int standAnimationCount = 0;
    private int runEnemyAnimationCount = 0;

    private PlayerDirection lastPlayerDirection = PlayerDirection.RIGHT;

    public GameFrame(ModelFacade modelFacade, Controller controller) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.getSize());

        modelFacade.setScalePercentage(viewScaleInator.getScalePercentWidth(), viewScaleInator.getScalePercentHeight());

        GameMenu gameMenu = new GameMenu();

        this.addKeyListener(controller);

        this.modelFacade = modelFacade;
        modelFacade.setPlayerStartCords(100,100);
        this.modelFacade.addObserver(this);

        BackgroundLabel backgroundLabel = new BackgroundLabel(this.getWidth(), this.getHeight());
        modelFacade.setGameFieldDimensions(this.getWidth(), this.getHeight());

        for (int i = 0; i < modelFacade.getWallsCount(); ++i) {
            WallsLabel wallPanel = new WallsLabel(modelFacade.getWallRect(i), viewScaleInator);
            this.add(wallPanel);
        }

        for (int i = 0; i < modelFacade.getEnemiesCount(); ++i) {
            EnemyLabel enemyLabel = new EnemyLabel(viewScaleInator);
            enemyLabel.setLocation(modelFacade.getEnemyX(i), modelFacade.getEnemyY(i));
            modelFacade.setEnemySize(i, enemyLabel.getWidth(), enemyLabel.getHeight());
            enemiesLabels.add(enemyLabel);
            this.add(enemyLabel);
        }

        modelFacade.setPlayerSize(playerLabel.getWidth(), playerLabel.getHeight());
        DoorLabel doorLabel = new DoorLabel(viewScaleInator);
        modelFacade.setDoorSize(doorLabel.getWidth(), doorLabel.getHeight());

        playerLabel.setBounds(modelFacade.getPlayerX(), modelFacade.getPlayerY(),
                playerLabel.getWidth(), playerLabel.getHeight());

        setPlayerLocation();
        setEnemyLocation();
        doorLabel.setLocation(modelFacade.getDoorX(), modelFacade.getDoorY());
        
        animationTimer.start();

        this.add(playerLabel, 0);
        this.add(doorLabel);
        this.add(backgroundLabel);
        this.setJMenuBar(gameMenu);
        this.setVisible(true);

        modelFacade.startModelTimer();
    }

    private void setPlayerLocation() {
        playerLabel.setLocation(modelFacade.getPlayerX(), modelFacade.getPlayerY());
    }

    private void setEnemyLocation() {
        for (int i = 0; i < modelFacade.getEnemiesCount(); ++i) {
            enemiesLabels.get(i).setLocation(modelFacade.getEnemyX(i), modelFacade.getEnemyY(i));
        }
    }

    @Override
    public void update(Changes change) {
        switch (change) {
            case CHANGE_PLAYER_CORDS -> setPlayerLocation();
            case PLAYER_REACH_DOOR, PLAYER_REACH_SCREEN_SIDE -> {}
            case CHANGE_ENEMY_CORDS -> setEnemyLocation();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == animationTimer) {
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
                    case LEFT -> enemiesLabels.get(i).updateEnemySprite(getEnemyRunLeftSprite());
                    case RIGHT -> enemiesLabels.get(i).updateEnemySprite(getEnemyRunRightSprite());
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
