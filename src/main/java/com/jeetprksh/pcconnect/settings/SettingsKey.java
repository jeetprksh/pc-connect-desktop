package com.jeetprksh.pcconnect.settings;

public enum SettingsKey {

  downloadDirectory("downloadDirectory");

  private final String settingKey;

  SettingsKey(String settingKey) {
    this.settingKey = settingKey;
  }

  public String getSettingKey() {
    return settingKey;
  }
}
