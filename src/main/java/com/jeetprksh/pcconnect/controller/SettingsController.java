package com.jeetprksh.pcconnect.controller;

import com.jeetprksh.pcconnect.persistence.dao.SettingsDao;
import com.jeetprksh.pcconnect.persistence.dto.SettingDTO;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import kotlin.text.StringsKt;

public class SettingsController {

  private final Logger logger = Logger.getLogger(SettingsController.class.getName());

  private SettingsDao settingsDao;

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
    logger.info("Saving settings");
    Optional<SettingDTO> downloadDirSetting = settingsDao.findAll().stream()
            .filter(s -> s.getName().equalsIgnoreCase("downloadDirectory")).findFirst();
    if (!downloadDirectory.getText().isEmpty() && !downloadDirSetting.isPresent()) {
      SettingDTO setting = new SettingDTO("downloadDirectory", downloadDirectory.getText());
      settingsDao.save(setting);
    } else if (!downloadDirectory.getText().isEmpty() && downloadDirSetting.isPresent()) {
      SettingDTO setting = downloadDirSetting.get();
      setting.setValue(downloadDirectory.getText());
      settingsDao.update(setting);
    }
  }

  @FXML
  private void initialize() {
    settingsDao = new SettingsDao();
    populateSavedSettings();
  }

  private void populateSavedSettings() {
    List<SettingDTO> settings = settingsDao.findAll();
    for (SettingDTO setting : settings) {
      if(setting.getName().equalsIgnoreCase("downloadDirectory")) {
        downloadDirectory.setText(setting.getValue());
      }
    }
  }

}
