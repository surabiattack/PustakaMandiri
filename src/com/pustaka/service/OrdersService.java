/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service;

import com.pustaka.model.Orders;
import com.pustaka.vo.ReportOmzetVo;
import com.pustaka.vo.ReportPenjualanByCustomerVo;
import com.pustaka.vo.ReportPenjualanByProdukVo;
import com.pustaka.vo.ReportPiutangByCustomerVo;
import com.pustaka.vo.ReportPiutangByFakturVo;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface OrdersService {
    public Orders findById(int id);
    public List<Orders> getOrderList();
    public List<Orders> findBySearch(String anything);
    public List<Orders> getCustomerOrderList(int idCustomer);
    public List<ReportOmzetVo> getOrderByYearList(int month);
    public List<ReportPenjualanByProdukVo> getReportByProdukList(String kodeProduk, String year);
    public List<ReportPenjualanByCustomerVo> getReportByCustomerList(int idCustomer, String year);
    public List<ReportPiutangByFakturVo> getReportPiutangByFakturList(int idOrders, String year);
    public List<ReportPiutangByCustomerVo> getReportPiutangByCustomerList(int idCustomer, String year);
    public void save(Orders saveEntity, String user);
    public void update(Orders updateEntity, String user);
    public void delete(Integer id);
    public Connection getConnection();
    public int persist(Orders orders);
    public List<ReportOmzetVo> getCustomerOrderListForReportOmzet(int idCustomer, int month);
    public int getNextPrimaryValue(String dbSchema, String tableName);
}
