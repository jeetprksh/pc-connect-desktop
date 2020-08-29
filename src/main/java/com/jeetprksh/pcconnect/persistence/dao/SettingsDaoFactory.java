package com.jeetprksh.pcconnect.persistence.dao;

import java.util.Objects;

/*
 * @author Jeet Prakash
 * */
public class SettingsDaoFactory {

  private SettingsDao settingsDao;

  public SettingsDao createSettingsDao() {
    if (Objects.isNull(this.settingsDao)) {
      this.settingsDao = new SettingsDao();
    }
    return this.settingsDao;
  }

}
