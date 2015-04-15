/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service;

import com.pustaka.model.Pembayaran;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface PembayaranService {
    public void save(Pembayaran pembayaran, String user);
    public void update(Pembayaran pembayaran, String user);
    public void delete(Integer id);
    public List<Pembayaran> getListPembayaranByOrder(int idOrders);
    public Pembayaran getLastPembayaran(Integer id);
}
