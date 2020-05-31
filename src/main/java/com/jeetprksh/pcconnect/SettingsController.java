package com.jeetprksh.pcconnect;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SettingsController {

  private final Logger logger = Logger.getLogger(SettingsController.class.getName());

  @FXML private TextField downloadDirectory;
  @FXML private Button browse;
  @FXML private Button save;

  public void browseDownloadDirectory() {
    logger.info("browse directory");
  }

  public void saveSettings() {
    logger.info("saving");
  }

}
