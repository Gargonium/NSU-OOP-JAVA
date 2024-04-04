package ru.nsu.votintsev.view.javafx;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.nsu.votintsev.controller.javafx.FXController;
import ru.nsu.votintsev.model.Changes;
import ru.nsu.votintsev.model.ModelFacade;
import ru.nsu.votintsev.model.directions.PlayerDirection;
import ru.nsu.votintsev.model.observer.interfaces.Observer;
import ru.nsu.votintsev.view.javafx.entity.DoorImage;
import ru.nsu.votintsev.view.javafx.entity.EnemyImage;
import ru.nsu.votintsev.view.javafx.entity.PlayerImage;
import ru.nsu.votintsev.view.javafx.entity.WallImage;
import ru.nsu.votintsev.view.states.EnemySpriteState;
import ru.nsu.votintsev.view.states.PlayerSpriteState;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameStage extends Stage implements Observer {

    private final FXViewScaler fxViewScaler = new FXViewScaler();

    private final PlayerImage playerImage = new PlayerImage(fxViewScaler);
    private final List<EnemyImage> enemiesImages = new ArrayList<>();

    private final ModelFacade modelFacade;

    private final ScheduledExecutorService animationTimer = Executors.newScheduledThreadPool(1);

    private int runPlayerAnimationCount = 0;
    private int standAnimationCount = 0;
    private int runEnemyAnimationCount = 0;

    private final Text livesText = new Text();
    private final Text timeText = new Text();

    private PlayerDirection lastPlayerDirection = PlayerDirection.RIGHT;

    public GameStage(ModelFacade modelFacade) {
        this.setTitle("VFP");
        this.setResizable(false);

        Screen screen = Screen.getPrimary();
        Rectangle2D screenSize = screen.getVisualBounds();
        this.setWidth(screenSize.getWidth());
        this.setHeight(screenSize.getHeight());

        setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

//        this.setFullScreen(true);
//        this.setFullScreenExitHint(null);
//        this.setForceIntegerRenderScale(true);
//
//        this.fullScreenProperty().addListener((obs, oldValue, newValue) -> {
//            if (!newValue) {
//                this.setFullScreen(true);
//            }
//        });

        this.modelFacade = modelFacade;
        modelFacade.addObserver(this);
        modelFacade.startModelTimer();
        modelFacade.setPlayerStartCords(100, 100);
        playerImage.setLayoutX(100);
        playerImage.setLayoutY(100);
        modelFacade.setScalePercentage(fxViewScaler.getScalePercentWidth(), fxViewScaler.getScalePercentHeight());
        modelFacade.setGameFieldDimensions((int) this.getWidth(), (int) this.getHeight());

        animationTimer.scheduleAtFixedRate(this::timerTask, 0, 100, TimeUnit.MILLISECONDS);

        livesText.setX(10);
        livesText.setY(30);
        livesText.setFont(new Font("Comic Sans", 15));
        timeText.setX(10);
        timeText.setY(50);
        timeText.setFont(new Font("Comic Sans", 15));

        timeText.setText("Time: null");
        livesText.setText("Lives: null");

        modelFacade.setPlayerSize((int) playerImage.getFitWidth(), (int) playerImage.getFitHeight());
        DoorImage doorImage = new DoorImage(fxViewScaler);
        doorImage.setLayoutX(modelFacade.getDoorX());
        doorImage.setLayoutY(modelFacade.getDoorY());
        modelFacade.setDoorSize((int) doorImage.getFitWidth(), (int) doorImage.getFitHeight());

        Group root = new Group();
        root.getChildren().add(livesText);
        root.getChildren().add(timeText);
        root.getChildren().add(playerImage);
        root.getChildren().add(doorImage);

        for (int i = 0; i < modelFacade.getWallsCount(); ++i) {
            WallImage wallImage = new WallImage(modelFacade.getWallRect(i), fxViewScaler);
            root.getChildren().add(wallImage);
        }

        for (int i = 0; i < modelFacade.getEnemiesCount(); ++i) {
            EnemyImage enemyImage = new EnemyImage(fxViewScaler);
            modelFacade.setEnemySize(i, enemyImage.getWidth(), enemyImage.getHeight());
            enemyImage.setLayoutX(modelFacade.getEnemyX(i));
            enemyImage.setLayoutY(modelFacade.getEnemyY(i));
            enemiesImages.add(enemyImage);
            root.getChildren().add(enemyImage);
        }

        Pane paneRoot = new Pane(root);
        Scene scene = new Scene(paneRoot);
        new FXController(scene, modelFacade);
        this.setScene(scene);
    }

    private void setPlayerLocation() {
        playerImage.setLayoutX(modelFacade.getPlayerX());
        playerImage.setLayoutY(modelFacade.getPlayerY());
    }

    private void setEnemyLocation() {
            Platform.runLater(() -> {
                for (int i = 0; i < enemiesImages.size(); ++i) {
                    enemiesImages.get(i).setLayoutX(modelFacade.getEnemyX(i));
                    enemiesImages.get(i).setLayoutY(modelFacade.getEnemyY(i));
                }
            });
    }

    @Override
    public void update(Changes change) {
        switch (change) {
            case CHANGE_PLAYER_CORDS -> setPlayerLocation();
            case CHANGE_ENEMY_CORDS -> setEnemyLocation();
            case PLAYER_REACH_SCREEN_SIDE -> {}
            case PLAYER_REACH_DOOR -> endGame(true);
            case PLAYER_DEAD -> endGame(false);
        }
    }

    private void endGame(boolean isWin) {
        animationTimer.shutdown();
        modelFacade.stopModelTimer();
        Platform.runLater(() -> {
            FinalStage finalStage = new FinalStage(isWin);
            finalStage.show();
            this.close();
        });
    }

    private void timerTask() {
        switch (modelFacade.getPlayerDirection()) {
            case STAND -> {
                PlayerSpriteState playerSpriteState = getPlayerStandSprite();
                standAnimationCount = standAnimationCount == 10 ? 0 : standAnimationCount + 1;
                playerImage.updatePlayerSprite(playerSpriteState);
            }
            case LEFT -> {
                playerImage.updatePlayerSprite(getPlayerRunLeftSprite());
                lastPlayerDirection = PlayerDirection.LEFT;
            }
            case RIGHT -> {
                playerImage.updatePlayerSprite(getPlayerRunRightSprite());
                lastPlayerDirection = PlayerDirection.RIGHT;
            }
        }
        for (int i = 0; i < enemiesImages.size(); ++i) {
            switch (modelFacade.getEnemyDirection(i)) {
                case LEFT -> enemiesImages.get(i).updateEnemySprite(getEnemyRunLeftSprite());
                case RIGHT -> enemiesImages.get(i).updateEnemySprite(getEnemyRunRightSprite());
            }
        }
        livesText.setText("Lives: " + modelFacade.getPlayerLives());
        timeText.setText("Time: " + modelFacade.getGameTime() + " sec");
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