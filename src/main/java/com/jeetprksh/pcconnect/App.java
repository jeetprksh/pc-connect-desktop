package com.jeetprksh.pcconnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Jeet Prakash
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pc-connect.fxml"));
        primaryStage.setScene(new Scene(root, 600, 275));
        primaryStage.setTitle("PC Connect");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
