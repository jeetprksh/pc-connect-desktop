package com.jeetprksh.pcconnect.persistence.dto;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="setting")
@NamedQueries({
  @NamedQuery(name = "setting.findByName", query = "select p from SettingDTO p where p.name = :name"),
  @NamedQuery(name = "setting.findAll", query = "select p from SettingDTO p")
})
public class SettingDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String value;

  public SettingDTO() {}

  public SettingDTO(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SettingDTO)) return false;
    SettingDTO that = (SettingDTO) o;
    return Objects.equals(name, that.name) && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }

  @Override
  public String toString() {
    return "SettingDTO{" +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            '}';
  }
}
