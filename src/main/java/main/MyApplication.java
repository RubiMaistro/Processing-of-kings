package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        primaryStage.setTitle("GameMode");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
