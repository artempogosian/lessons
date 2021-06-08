package ru.geekbrains.lesson8;

import org.hibernate.Session;

public class Repository<T> {
    private Session session;

    public Repository(Session session) {
        this.session = session;
    }

    public void createOrUpdate(T model) {
        session.getTransaction().begin();
        session.saveOrUpdate(model);
        session.getTransaction().commit();
    }

    public T findOne(Class<T> type, long id) {
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
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
    }
}
