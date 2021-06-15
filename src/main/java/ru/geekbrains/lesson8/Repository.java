package ru.geekbrains.lesson8;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Repository<T> {
    private final SessionFactory sessionFactory;

    public Repository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createOrUpdate(T model) {
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        session.saveOrUpdate(model);
        session.getTransaction().commit();
    }

    public T findOne(Class<T> type, long id) {
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        T entity = session.find(type, id);
        session.getTransaction().commit();
        return entity;
    }

    public void deleteById(Class<T> type, long entityId) {
        T entity = findOne(type, entityId);
        delete(entity);
    }

    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
    }
}
