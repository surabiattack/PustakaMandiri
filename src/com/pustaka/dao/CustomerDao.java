/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao;

import com.pustaka.common.GenericDao;
import com.pustaka.model.Customer;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface CustomerDao extends GenericDao<Customer, Integer> {
    public Customer findById(Integer id);
    public List<Customer> getProdukList();
    public List<Customer> findBySearch(String anything);
    public List<Customer> findBySearch(String nama,String anything);
    public List<Customer> findByName(String nama);
}
