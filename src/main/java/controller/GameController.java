package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import processkings.state.Player;
import processkings.state.Position;
import processkings.state.TableState;

import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class GameController{

    private String userName_own;
    private String userName_enemy;
    private int stepCount;
    private List<Image> boardImages;
    private Instant beginGame;

    private Player P1 = new Player(new Position(2,0));
    private Player P2 = new Player(new Position(3,7));

    private TableState gameState;

    @FXML
    private Label
            usernameLabelOwn,
            usernameLabelEnemy;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepLabel;

    @FXML
    private Label solvedLabel;

    @FXML
    private Button doneButton;


    private void drawGameState() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 8 + j);
                view.setImage(boardImages.get(gameState.getValueFromBoardTable(i,j)));

                System.out.print(gameState.getValueFromBoardTable(i,j) + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }

    public void initdata(String userName_own, String userName_enemy) {
        this.userName_own = userName_own;
        this.userName_enemy = userName_enemy;
        usernameLabelOwn.setText("Lion King: " + this.userName_own);
        usernameLabelEnemy.setText("Hyena: " + this.userName_enemy);
    }

    @FXML
    public void initialize() {

        gameState = new TableState(P1.getPosition(),P2.getPosition());

        gameState.setDefaultPositions();
        gameState.setInitialTable();

        stepCount = 0;

        beginGame = Instant.now();

        boardImages = Arrays.asList(
                new Image(getClass().getResource("/pictures/field.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/grass.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/lionking.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/hiena.png").toExternalForm()));

        drawGameState();

        P1.setPosition(gameState.getOwnPosition());
        P2.setPosition(gameState.getEnemyPosition());
    }

    private boolean isClickDefault = false;
    private boolean isSaveClick = false;
    private boolean isMoved = false;

    @FXML
    private void fieldClick(MouseEvent mouseEvent) {

        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());

        if(!gameState.isSolved()) {
            if (gameState.isEqualOwnPosition(clickedRow, clickedColumn)
                    && isSaveClick == false
                    && isClickDefault == false) {
                gameState.setSavedClickPosition(new Position(clickedRow, clickedColumn));
                isSaveClick = true;
                System.out.println("Save");

            } else if (gameState.canMove(clickedRow, clickedColumn) == true && isSaveClick == true && isMoved == false) {
                gameState.move(clickedRow, clickedColumn);
                isMoved = true;
                System.out.println("Move");

            } else if (isMoved == true && isClickDefault == false && !gameState.isPlayer(clickedRow,clickedColumn)){
                gameState.addNewDefaultPosition(new Position(clickedRow,clickedColumn));
                isSaveClick = false;
                isMoved = false;
                gameState.changeNextPlayer();
                System.out.println("Default");
            }
        }

        drawGameState();
    }

}
