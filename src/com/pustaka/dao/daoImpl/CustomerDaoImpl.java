/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao.daoImpl;

import com.pustaka.common.GenericDaoImpl;
import com.pustaka.dao.CustomerDao;
import com.pustaka.model.Customer;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dwi
 */
@Repository("customerDao")
public class CustomerDaoImpl extends GenericDaoImpl<Customer, Integer> implements CustomerDao{

    @Override
    public Customer findById(Integer id) {
        Criteria crit = getCurrentSession().createCriteria(Customer.class);
        crit.add(Restrictions.eq("id_customer", id).ignoreCase());
        return (Customer)crit.uniqueResult();
    }
    
    @Override
    public List<Customer> findByName(String nama) {
        Criteria crit = getCurrentSession().createCriteria(Customer.class);
        crit.add(Restrictions.eq("nama", nama));
        return crit.list();
    }

    @Override
    public List<Customer> getProdukList() {
        Criteria crit = getCurrentSession().createCriteria(Customer.class);
        return crit.list();
    }

    @Override
    public List<Customer> findBySearch(String anything) {
        StringBuilder sb= new StringBuilder("SELECT id_customer, nama, alamat, telepon "
                + " FROM CUSTOMER WHERE id_customer like '%"+anything+"%'"
                + " OR nama like '%"+anything+"%' "
                + " OR alamat like '%"+anything+"%' "
                + " OR telepon like '%"+anything+"%'");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] result, String[] aliases) {
                Customer customer= null;
                
                try {
                    customer= new Customer();
                    customer.setCustomerId((Integer) result[0]);
                    customer.setNama((String) result[1]);
                    customer.setAlamat((String) result[2]);
                    customer.setTelepon((String) result[3]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return customer;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }); 
        
        return result.list();
    }
    
    @Override
    public List<Customer> findBySearch(String nama,String anything) {
        StringBuilder sb= new StringBuilder("SELECT id_customer, nama, alamat, telepon "
                + " FROM CUSTOMER WHERE nama like '%"+nama+"%' "
                + " OR alamat like '%"+anything+"%' "
                + " OR telepon like '%"+anything+"%'");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] result, String[] aliases) {
                Customer customer= null;
                
                try {
                    customer= new Customer();
                    customer.setCustomerId((Integer) result[0]);
                    customer.setNama((String) result[1]);
                    customer.setAlamat((String) result[2]);
                    customer.setTelepon((String) result[3]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return customer;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }); 
        
        return result.list();
    }
}
