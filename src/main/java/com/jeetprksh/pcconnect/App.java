package com.jeetprksh.pcconnect;

import com.jeetprksh.pcconnect.controller.RootController;
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

  private RootController rootController;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    logger.info("Starting the PC Connect application");
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pc-connect.fxml"));
    Parent root = loader.load();
    this.rootController = loader.getController();

    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("PC Connect");
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() {
    logger.info("Stopping the PC Connect application");
    PersistenceConfig.getSessionFactory().close();
    this.rootController.close();
  }
}
