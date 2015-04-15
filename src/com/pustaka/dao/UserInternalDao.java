/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao;

import com.pustaka.common.GenericDao;
import com.pustaka.model.UserInternal;
import java.util.List;

/**
 *
 * @author dwi
 */

public interface UserInternalDao extends GenericDao<UserInternal, Integer> {
    public UserInternal findById(int id);
    public UserInternal findByUsernameAndPassword(String username, String password);
    public List<UserInternal> getUserList();
    public List<UserInternal> findBySearch(String anything);
}
