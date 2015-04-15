/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao;

import com.pustaka.common.GenericDao;
import com.pustaka.model.Pembayaran;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface PembayaranDao extends GenericDao<Pembayaran, Integer> {
    public List<Pembayaran> getListPembayaranByOrder(int idOrders);

    public Pembayaran getLastPembayaran(Integer id);
}
