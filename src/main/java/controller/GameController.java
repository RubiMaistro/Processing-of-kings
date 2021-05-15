package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import processkings.state.Player;
import processkings.state.Position;
import processkings.state.TableState;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameController{

    private TableState gameState;

    private List<Image> boardImages;
    private List<Image> currentImages;

    private Instant beginGame;

    private Player P1 = new Player(new Position(2,0));
    private Player P2 = new Player(new Position(3,7));

    private int stepCount = 0;

    @FXML
    private GridPane gameGrid;

    @FXML
    private ImageView ActualPlayerLabel;

    @FXML
    private Pane gameOverPane;

    @FXML
    private Label
            usernameLabelOwn,
            usernameLabelEnemy,
            gameOverLabel,
            winnerLabel;

    @FXML
    private Button
            menuButton,
            topPlayersButton;

    private static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    private static final Marker PLAYER_NAME_MARKER = MarkerManager.getMarker("PLAYER_NAME");
    private static final Marker STEPS_MARKER = MarkerManager.getMarker("STEPS");
    private static final Marker DEFAULTS_MARKER = MarkerManager.getMarker("DEFAULTS");

    private void drawGameState() {

        ImageView currentView = ActualPlayerLabel;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 8 + j);
                view.setImage(boardImages.get(gameState.getValueFromBoardTable(i,j)));
                if(gameState.getOwnValue() == 3)
                    currentView.setImage(currentImages.get(1));
                else
                    currentView.setImage(currentImages.get(0));
            }
        }
    }

    public void initdata(String userName_own, String userName_enemy) {
        P1.setUserName(userName_own);
        P2.setUserName(userName_enemy);
        usernameLabelOwn.setText("Lion King: " + P1.getUserName());
        usernameLabelEnemy.setText("Hyena: " + P2.getUserName());
        logger.debug(PLAYER_NAME_MARKER,P1.getUserName());
        logger.debug(PLAYER_NAME_MARKER,P2.getUserName());
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

        currentImages = Arrays.asList(
                new Image(getClass().getResource("/pictures/lionking_launch.png").toExternalForm()),
                new Image(getClass().getResource("/pictures/hiena_launch.png").toExternalForm()));

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


            if (isSaveClick == false
                    && isClickDefault == false
                    && gameState.isEqualOwnPosition(clickedRow, clickedColumn)) {
                gameState.setSavedClickPosition(new Position(clickedRow, clickedColumn));
                isSaveClick = true;

            } else if (gameState.canMove(clickedRow, clickedColumn) == true && isSaveClick == true && isMoved == false) {
                gameState.move(clickedRow, clickedColumn);
                stepCount += 1;
                isMoved = true;
                if(gameState.getOwnPosition().row() == P1.getPosition().row() && gameState.getOwnPosition().col() == P1.getPosition().col())
                    logger.debug(STEPS_MARKER,P1.getUserName() + " move to the (" + clickedRow + "," + clickedColumn + ") position.");

            } else if (isMoved == true && isClickDefault == false && !gameState.isPlayer(clickedRow,clickedColumn)){
                gameState.addNewDefaultPosition(new Position(clickedRow,clickedColumn));
                isSaveClick = false;
                isMoved = false;
                gameState.changeNextPlayer();
                //P1.setPosition(gameState.getOwnPosition());
                //P2.setPosition(gameState.getEnemyPosition());
                logger.debug(STEPS_MARKER,P1.getUserName() + " set default the (" + clickedRow + "," + clickedColumn + ") position.");
                logger.debug(DEFAULTS_MARKER,"(" + clickedRow + "," + clickedColumn + ")");
                changePlayers();
            }

        if(gameState.isSolved()) {
            gameOverPane.setStyle("-fx-background-color: white;");
            gameOverPane.setVisible(true);
            gameOverLabel.setVisible(true);
            winnerLabel.setText(P1.getUserName() + " win!");
            winnerLabel.setVisible(true);
            menuButton.setVisible(true);
            topPlayersButton.setVisible(true);
            initialize();
            logger.debug(STEPS_MARKER, P2.getUserName() + " win the game in " + stepCount + " steps.");
        }


        drawGameState();
    }

    @FXML
    private void finishGame(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/launch.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        initialize();
        logger.debug("Game over.");
        drawGameState();
    }

    @FXML
    private void goToTopTen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/topten.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        logger.debug("Top10 list.");
        gameState = new TableState(P1.getPosition(),P2.getPosition());
    }

    private void changePlayers(){
        Player P0 = P1;
        P1 = P2;
        P2 = P0;
    }

}
