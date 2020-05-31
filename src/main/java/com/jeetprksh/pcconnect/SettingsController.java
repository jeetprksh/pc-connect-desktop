package com.jeetprksh.pcconnect;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class SettingsController {

  private final Logger logger = Logger.getLogger(SettingsController.class.getName());

  @FXML private TextField downloadDirectory;
  @FXML private Button browse;
  @FXML private Button save;

  public void browseDownloadDirectory() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    File selectedDirectory = directoryChooser.showDialog(null);
    if (!Objects.isNull(selectedDirectory)) {
      downloadDirectory.setText(selectedDirectory.getAbsolutePath());
    }
  }

  public void saveSettings() {
    logger.info("saving");
  }

}
