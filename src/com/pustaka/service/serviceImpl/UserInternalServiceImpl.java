/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service.serviceImpl;

import com.pustaka.dao.UserInternalDao;
import com.pustaka.model.UserInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pustaka.service.UserInternalService;
import java.util.List;

/**
 *
 * @author dwi
 */
@Service("userInternalService")
public class UserInternalServiceImpl implements UserInternalService{

    @Autowired
    @Qualifier("userInternalDao")
    private UserInternalDao userInternalDao;
    
    @Override
    @Transactional(readOnly=true)
    public UserInternal findById(int id) {
        return userInternalDao.findById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public UserInternal findByUsernameAndPassword(String username, String password) {
        return userInternalDao.findByUsernameAndPassword(username, password);
    }

    public UserInternalDao getUserInternalDao() {
        return userInternalDao;
    }

    public void setUserInternalDao(UserInternalDao userInternalDao) {
        this.userInternalDao = userInternalDao;
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserInternal> getUserList() {
        return userInternalDao.getUserList();
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserInternal> findBySearch(String anything) {
        return userInternalDao.findBySearch(anything);
    }

    @Override
    @Transactional
    public void save(UserInternal saveEntity, String user) {
        userInternalDao.save(saveEntity, user);
    }

    @Override
    @Transactional
    public void update(UserInternal updateEntity, String user) {
        userInternalDao.update(updateEntity, user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userInternalDao.delete(id);
    }
    
}
