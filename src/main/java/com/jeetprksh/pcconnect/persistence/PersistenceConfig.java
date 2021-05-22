package com.jeetprksh.pcconnect.persistence;

import com.jeetprksh.pcconnect.settings.SettingDTO;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Jeet Prakash
 */
public class PersistenceConfig {

  private final static Logger logger = Logger.getLogger(PersistenceConfig.class.getName());

  private static final SessionFactory sessionFactory;

  static {
    try {
      logger.info("Initializing Hibernate Session Factory");
      StandardServiceRegistry standardRegistry =
              new StandardServiceRegistryBuilder().applySettings(getConfig()).build();
      Metadata metaData = new MetadataSources(standardRegistry)
              .addAnnotatedClass(SettingDTO.class)
              .getMetadataBuilder()
              .build();
      sessionFactory = metaData.getSessionFactoryBuilder().build();
    } catch (Exception ex) {
      logger.severe("Unable to create Hibernate Session Factory " + ex.getMessage());
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  private static Map<String, String> getConfig() {
    Map<String, String> settings = new HashMap<>();
    settings.put(Environment.DRIVER, "org.h2.Driver");
    settings.put(Environment.URL, "jdbc:h2:file:~/.pc-connect/db");
    settings.put(Environment.USER, "sa");
    settings.put(Environment.PASS, "");
    settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
    settings.put(Environment.HBM2DDL_AUTO, "update");
    settings.put(Environment.AUTOCOMMIT, "true");
    return settings;
  }
}
