package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LaunchController {

    @FXML
    private TextField usernameTextfield_o;
    @FXML
    private TextField usernameTextfield_e;

    @FXML
    private Label errorLabel;

    public void startAction(ActionEvent actionEvent) throws IOException {
        if (usernameTextfield_o.getText().isEmpty() && usernameTextfield_e.getText().isEmpty()) {
            errorLabel.setText("* Username is empty!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().initdata(usernameTextfield_o.getText(), usernameTextfield_e.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            log.info("Username is set to {}, loading game scene.", usernameTextfield_o.getText() + " AND " + usernameTextfield_e.getText());
        }

    }
}
