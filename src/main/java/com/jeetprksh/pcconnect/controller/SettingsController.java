package com.jeetprksh.pcconnect.controller;

import com.jeetprksh.pcconnect.persistence.dao.SettingsDao;
import com.jeetprksh.pcconnect.persistence.dto.SettingDTO;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/**
 * @author Jeet Prakash
 */
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
    logger.info("Saving/Updating settings");

    if (Objects.isNull(downloadDirectory.getText()) || downloadDirectory.getText().isEmpty()) return;

    Optional<SettingDTO> settingsOpt = settingsDao.findAll().stream().findFirst();
    if (settingsOpt.isPresent()) {
      updateSettings(settingsOpt);
    } else {
      saveNewSettings();
    }
  }

  @FXML
  private void initialize() {
    settingsDao = new SettingsDao();
    populateSavedSettings();
  }

  private void updateSettings(Optional<SettingDTO> settingsOpt) {
    SettingDTO setting = settingsOpt.get();
    setting.setDownloadDirectory(downloadDirectory.getText());
    settingsDao.update(setting);
  }

  private void saveNewSettings() {
    SettingDTO setting = new SettingDTO();
    setting.setDownloadDirectory(downloadDirectory.getText());
    settingsDao.save(setting);
  }

  private void populateSavedSettings() {
    Optional<SettingDTO> settingsOpt = settingsDao.findAll().stream().findFirst();
    settingsOpt.ifPresent(settingDTO -> downloadDirectory.setText(settingDTO.getDownloadDirectory()));
  }

}
