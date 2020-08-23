package com.jeetprksh.pcconnect.persistence.dto;

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

  private String downloadDirectory;

  public SettingDTO() { }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDownloadDirectory() {
    return downloadDirectory;
  }

  public void setDownloadDirectory(String downloadDirectory) {
    this.downloadDirectory = downloadDirectory;
  }

  @Override
  public String toString() {
    return "SettingDTO{" +
            "id=" + id +
            ", downloadDirectory='" + downloadDirectory + '\'' +
            '}';
  }
}
