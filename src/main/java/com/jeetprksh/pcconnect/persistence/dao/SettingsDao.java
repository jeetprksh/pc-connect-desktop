package com.jeetprksh.pcconnect.persistence.dao;

import com.jeetprksh.pcconnect.persistence.dto.SettingDTO;

import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author Jeet Prakash
 * */
public class SettingsDao extends CommonDao {

  public Integer save(SettingDTO dto) throws HibernateException {
    return saveEntity(dto).intValue();
  }

  public void update(SettingDTO dto) throws HibernateException {
    updateEntity(dto);
  }

  public SettingDTO findByName(String name) throws HibernateException {
    String namedQuery = "setting.findByName";
    Map<String, String> param = new HashMap<>();
    param.put("name", name);
    return getSingle(namedQuery, SettingDTO.class, param);
  }

  public List<SettingDTO> findAll() throws HibernateException {
    String namedQuery = "setting.findAll";
    return list(namedQuery, SettingDTO.class, new HashMap<>());
  }
}
