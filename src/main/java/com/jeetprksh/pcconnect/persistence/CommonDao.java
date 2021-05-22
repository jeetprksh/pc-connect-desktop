package com.jeetprksh.pcconnect.persistence;

import com.jeetprksh.pcconnect.persistence.PersistenceConfig;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;

/*
 * @author Jeet Prakash
 * */
public class CommonDao {

  private final SessionFactory sessionFactory;

  public CommonDao() {
    sessionFactory = PersistenceConfig.getSessionFactory();
  }

  protected <T> Integer saveEntity(T obj) throws HibernateException {
    Session session = sessionFactory.openSession();
    Integer id = (Integer) session.save(obj);
    session.close();
    return id;
  }

  protected <T> void updateEntity(T obj) throws HibernateException {
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();
    session.update(obj);
    tx.commit();
    session.close();
  }

  protected <T> void delete(T obj) throws HibernateException {
    Session session = sessionFactory.openSession();
    session.delete(obj);
    session.close();
  }

  protected <T> T getSingle(String namedQuery, Class<T> objectType,
                            Map<String, String> params) throws HibernateException {
    Session session = sessionFactory.openSession();
    T object = session.createNamedQuery(namedQuery, objectType).setProperties(params).getSingleResult();
    session.close();
    return object;
  }

  protected <T> List<T> list(String namedQuery, Class<T> objectType,
                             Map<String, String> params) throws HibernateException {
    Session session = sessionFactory.openSession();
    List<T> list = session.createNamedQuery(namedQuery, objectType).setProperties(params).getResultList();
    session.close();
    return list;
  }
}
