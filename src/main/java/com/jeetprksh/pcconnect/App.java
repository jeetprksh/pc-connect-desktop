package com.jeetprksh.pcconnect;

import com.jeetprksh.pcconnect.persistence.PersistenceConfig;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Jeet Prakash
 */
public class App extends Application {

  private final Logger logger = Logger.getLogger(App.class.getName());

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pc-connect.fxml"));
    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("PC Connect");
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() {
    logger.info("Stopping the application");
    PersistenceConfig.getSessionFactory().close();
  }
}
