package com.jeetprksh.pcconnect.settings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Jeet Prakash
 */
@Entity
@Table(name="setting")
@NamedQueries({
  @NamedQuery(name = "setting.findAll", query = "select p from SettingDTO p")
})
public class SettingDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String settingKey;

  private String settingValue;

  public SettingDTO() { }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSettingKey() {
    return settingKey;
  }

  public void setSettingKey(String settingKey) {
    this.settingKey = settingKey;
  }

  public String getSettingValue() {
    return settingValue;
  }

  public void setSettingValue(String settingValue) {
    this.settingValue = settingValue;
  }

  @Override
  public String toString() {
    return "SettingDTO{" +
            "id=" + id +
            ", settingKey='" + settingKey + '\'' +
            ", settingValue='" + settingValue + '\'' +
            '}';
  }
}
