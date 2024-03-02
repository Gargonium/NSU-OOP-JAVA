package ru.nsu.votintsev.View;

import ru.nsu.votintsev.Controller.Controller;
import ru.nsu.votintsev.Model.ModelFacade;
import ru.nsu.votintsev.Model.PlayerDirection;
import ru.nsu.votintsev.View.entity.label.DoorLabel;
import ru.nsu.votintsev.View.entity.label.EnemyLabel;
import ru.nsu.votintsev.View.entity.label.PlayerLabel;
import ru.nsu.votintsev.View.entity.label.WallsPanel;
import ru.nsu.votintsev.View.sprite.state.EnemySpriteState;
import ru.nsu.votintsev.View.sprite.state.PlayerSpriteState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GameFrame extends JFrame implements Observer, ActionListener {

    private final PlayerLabel playerLabel = new PlayerLabel();
    private final DoorLabel doorLabel = new DoorLabel();

    private final Vector<EnemyLabel> enemiesLabel = new Vector<>();

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

        for (int i = 0; i < modelFacade.getWallsCount(); ++i) {
            WallsPanel wallPanel = new WallsPanel(modelFacade.getWallRect(i));
            this.add(wallPanel);
        }

        for (int i = 0; i < modelFacade.getEnemiesCount(); ++i) {
            EnemyLabel enemyLabel = new EnemyLabel();
            enemyLabel.setLocation(modelFacade.getEnemyX(i), modelFacade.getEnemyY(i));
            modelFacade.setEnemySize(i, enemyLabel.getWidth(), enemyLabel.getHeight());
            enemiesLabel.add(enemyLabel);
            this.add(enemyLabel);
        }

        modelFacade.setPlayerSize(playerLabel.getWidth(), playerLabel.getHeight());
        modelFacade.setDoorSize(doorLabel.getWidth(), doorLabel.getHeight());
        modelFacade.setGameFieldDimensions(this.getHeight(), this.getWidth());

        playerLabel.setBounds(modelFacade.getPlayerX(), modelFacade.getPlayerY(),
                playerLabel.getWidth(), playerLabel.getHeight());

        setPlayerLocation();

        doorLabel.setLocation(modelFacade.getDoorX(), modelFacade.getDoorY());

        gameTimer.start();
        animationTimer.start();

        this.setJMenuBar(gameMenu);
        this.add(playerLabel);
        this.add(doorLabel);
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
