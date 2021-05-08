package controller;

import lombok.extern.slf4j.Slf4j;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import processkings.state.GameState;
import processkings.state.Player;

import java.time.Instant;
import java.util.List;

public class GameController {

    private GameState gameState;
    private String userName;
    private int stepCount;
    private List<Image> cubeImages;
    private Instant beginGame;

    @FXML
    private Label usernameLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepLabel;

    @FXML
    private Label solvedLabel;

    @FXML
    private Button doneButton;

    private void drawGameState() {
        stepLabel.setText(String.valueOf(stepCount));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 3 + j);
                view.setImage(cubeImages.get(gameState.getTray()[i][j].getValue()));
            }
        }
    }

}
