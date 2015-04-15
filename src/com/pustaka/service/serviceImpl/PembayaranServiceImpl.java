/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service.serviceImpl;

import com.pustaka.common.GenericService;
import com.pustaka.dao.PembayaranDao;
import com.pustaka.model.Pembayaran;
import com.pustaka.service.PembayaranService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dwi
 */
@Service("pembayaranService")
@Transactional
public class PembayaranServiceImpl extends GenericService implements PembayaranService{
    
    @Autowired
    @Qualifier("pembayaranDao")
    private PembayaranDao pembayaranDao;

    @Override
    public void save(Pembayaran pembayaran, String user) {
        pembayaranDao.save(pembayaran, user);
    }

    @Override
    public void update(Pembayaran pembayaran, String user) {
        pembayaranDao.update(pembayaran, user);
    }

    @Override
    public void delete(Integer id) {
        pembayaranDao.delete(id);
    }

    public PembayaranDao getPembayaranDao() {
        return pembayaranDao;
    }

    public void setPembayaranDao(PembayaranDao pembayaranDao) {
        this.pembayaranDao = pembayaranDao;
    }

    @Override
    public List<Pembayaran> getListPembayaranByOrder(int idOrders) {
        return pembayaranDao.getListPembayaranByOrder(idOrders);
    }

    @Override
    public Pembayaran getLastPembayaran(Integer id) {
        return pembayaranDao.getLastPembayaran(id);
    }
    
}
