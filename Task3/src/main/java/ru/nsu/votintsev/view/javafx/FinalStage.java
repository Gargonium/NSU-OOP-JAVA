package ru.nsu.votintsev.view.javafx;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.nsu.votintsev.model.ModelFacade;

public class FinalStage extends Stage {

    public FinalStage(boolean isWin) {
        Text winText = new Text("YOU WIN");
        Text loseText = new Text("YOU LOSE");
        Button restartButton = new Button("RESTART");
        BorderPane pane = new BorderPane();
        loseText.setFont(new Font("Comic Sans", 15));
        winText.setFont(new Font("Comic Sans", 15));

        if (isWin) {
            pane.setTop(winText);
            this.setTitle("You Win :)");
        } else {
            pane.setTop(loseText);
            this.setTitle("You Lose :(");
        }

        pane.setCenter(restartButton);

        this.setResizable(false);

        Screen screen = Screen.getPrimary();
        Rectangle2D screenSize = screen.getVisualBounds();
        this.setWidth(screenSize.getWidth());
        this.setHeight(screenSize.getHeight());

        Scene scene = new Scene(pane);
        this.setScene(scene);

        restartButton.setOnAction(actionEvent -> {
            ModelFacade modelFacade = new ModelFacade();
            GameStage mainFrame = new GameStage(modelFacade);
            mainFrame.show();
            this.close();
        });
    }
}
