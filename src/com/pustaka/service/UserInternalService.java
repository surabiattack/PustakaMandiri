/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service;

import com.pustaka.model.UserInternal;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface UserInternalService {
    public UserInternal findById(int id);
    public UserInternal findByUsernameAndPassword(String username, String password);
    public List<UserInternal> getUserList();
    public List<UserInternal> findBySearch(String anything);
    public void save(UserInternal saveEntity, String user);
    public void update(UserInternal updateEntity, String user);
    public void delete(Integer id);
}
