/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao.daoImpl;

import com.pustaka.common.GenericDaoImpl;
import com.pustaka.dao.PembayaranDao;
import com.pustaka.model.Pembayaran;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dwi
 */
@Repository("pembayaranDao")
public class PembayaranDaoImpl extends GenericDaoImpl<Pembayaran, Integer> implements PembayaranDao{

    @Override
    public List<Pembayaran> getListPembayaranByOrder(int idOrders) {
        Criteria crit = getCurrentSession().createCriteria(Pembayaran.class);
        crit.add(Restrictions.eq("orders.idOrders", idOrders));
        return crit.list();
    }

    @Override
    public Pembayaran getLastPembayaran(Integer id) {
        Criteria crit = getCurrentSession().createCriteria(Pembayaran.class);
        crit.add(Restrictions.eq("orders.idOrders", id));
        crit.addOrder(Order.desc("tglBayar"));
        crit.setMaxResults(1);
        return (Pembayaran) crit.uniqueResult();
    }
    
}
