/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class GenericDaoImpl <T, K extends Serializable> implements GenericDao<T, K> {
    
    @Autowired()
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;
    
    private Class <T> persistentClass;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
    
    public GenericDaoImpl() {
        persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    @Override
    public T load(K key) {
        return (T) sessionFactory.getCurrentSession().load(persistentClass, key);
    }

    @Override
    public T get(K key) {
        return (T) sessionFactory.getCurrentSession().get(persistentClass, key);
    }
    
    protected Session getCurrentSession() {
    	return sessionFactory.getCurrentSession();
    }
    
    @Override
    public void save(T newEntity) {
        getCurrentSession().save(newEntity);
    }
    
    @Override
    public void save(BaseAudit newEntity, String user) {
    	newEntity.setCreateBy(user);
    	newEntity.setCreateDate(new Date());
        getCurrentSession().save(newEntity);
    }

    @Override
    public void delete(K key) {
        Object entity = getCurrentSession().get(persistentClass, key);
        getCurrentSession().delete(entity);
    }

    @Override
    public boolean update(T editedEntity) {
        getCurrentSession().update(editedEntity);
        return true;
    }

    @Override
    public boolean update(BaseAudit editedEntity, String user) {
        editedEntity.setUpdateBy(user);
    	editedEntity.setUpdateDate(new Date());
        getCurrentSession().update(editedEntity);
        return true;
    }

    @Override
    public void merge(T newEntity) {
        getCurrentSession().merge(newEntity);
    }

    @Override
    public void evict(T entity) {
        getCurrentSession().evict(entity);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void clear() {
        getCurrentSession().clear();
    }

    @Override
    public void refresh(T entity) {
        getCurrentSession().refresh(entity);
    }
    
}
