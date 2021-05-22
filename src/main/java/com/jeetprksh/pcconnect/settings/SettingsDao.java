package com.jeetprksh.pcconnect.settings;

import com.jeetprksh.pcconnect.persistence.CommonDao;

import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.List;

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
