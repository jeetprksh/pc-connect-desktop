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
    return saveEntity(dto);
  }

  public void update(SettingDTO dto) throws HibernateException {
    updateEntity(dto);
  }

  public List<SettingDTO> findAll() throws HibernateException {
    String namedQuery = "setting.findAll";
    return list(namedQuery, SettingDTO.class, new HashMap<>());
  }
}
