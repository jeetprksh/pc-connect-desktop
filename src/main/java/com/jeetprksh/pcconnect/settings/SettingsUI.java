package com.jeetprksh.pcconnect.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Jeet Prakash
 */
public class SettingsUI {

  private final Logger logger = Logger.getLogger(SettingsUI.class.getName());

  private final SettingsDao settingsDao = new SettingsDaoFactory().createSettingsDao();

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
    populateSavedSettings();
  }

  private void updateSettings(Optional<SettingDTO> settingsOpt) {
    SettingDTO setting = settingsOpt.get();
    setting.setSettingValue(downloadDirectory.getText());
    settingsDao.update(setting);
  }

  private void saveNewSettings() {
    SettingDTO setting = new SettingDTO();
    setting.setSettingKey(SettingsKey.downloadDirectory.getSettingKey());
    setting.setSettingValue(downloadDirectory.getText());
    settingsDao.save(setting);
  }

  private void populateSavedSettings() {
    Optional<SettingDTO> settingsOpt = settingsDao.findAll().stream().findFirst();
    settingsOpt.ifPresent(settingDTO -> downloadDirectory.setText(settingDTO.getSettingValue()));
  }

}
