/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service;

import com.pustaka.model.Customer;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface CustomerService {
    public Customer findById(int id);
    public List<Customer> getCustomerList();
    public List<Customer> findBySearch(String anything);
    public List<Customer> findBySearch(String nama,String anything);
    public void save(Customer saveEntity, String user);
    public void update(Customer updateEntity, String user);
    public void delete(Integer id);
    public List<Customer> findByName(String nama);
}
