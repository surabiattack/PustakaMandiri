/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.common;

import java.io.Serializable;

/**
 *
 * @author dwi
 */
public interface GenericDao <T, K extends Serializable>{
     /**
     * might not hit db, return proxy placeholder, throw ex if no rec found
     */
    T load(K key);
    
    /**
     * always hit db, might return null if no rec found
     */
    T get(K key);
    	
    void delete(K key);
        
    void save(T newEntity);
    void save(BaseAudit newEntity, String user);
    
    
    boolean update(T editedEntity);
    boolean update(BaseAudit editedEntity, String user);
    
    void merge(T newEntity);
    
    void evict(T entity);
    
    void flush();
    
    void clear();
    
    void refresh(T entity);
    
}
