/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service.serviceImpl;

import com.pustaka.common.GenericService;
import com.pustaka.dao.CustomerDao;
import com.pustaka.model.Customer;
import com.pustaka.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dwi
 */
@Service("customerService")
public class CustomerServiceImpl extends GenericService implements CustomerService{
    
    @Autowired
    @Qualifier("customerDao")
    private CustomerDao customerDao;

    @Override
    @Transactional(readOnly=true)
    public Customer findById(int id) {
        return customerDao.get(id);
    }

    @Override
    @Transactional
    public void save(Customer saveEntity, String user) {
        updateCreationInfo(saveEntity, user);
        customerDao.save(saveEntity);
    }

    @Override
    @Transactional
    public void update(Customer updateEntity, String user) {
        updateLastModInfo(updateEntity, user);
        customerDao.update(updateEntity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        customerDao.delete(id);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Customer> getCustomerList() {
        return customerDao.getProdukList();
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Customer> findBySearch(String anything) {
        return customerDao.findBySearch(anything);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Customer> findBySearch(String nama,String anything) {
        return customerDao.findBySearch(nama,anything);
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Customer> findByName(String nama) {
        return customerDao.findByName(nama);
    }

}
