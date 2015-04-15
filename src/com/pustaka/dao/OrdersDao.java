/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao;

import com.pustaka.common.GenericDao;
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
public interface OrdersDao extends GenericDao<Orders, Integer> {
    public Orders findById(Integer id);
    public List<Orders> getOrderList();
    public List<ReportOmzetVo> getOrderByYearList(int years);
    public List<ReportPenjualanByProdukVo> getReportByProdukList(String kodeProduk, String year);
    public List<ReportPenjualanByCustomerVo> getReportByCustomerList(int idCustomer, String year);
    public List<ReportPiutangByFakturVo> getReportPiutangByFakturList(int idOrders, String year);
    public List<ReportPiutangByCustomerVo> getReportPiutangByCustomerList(int idCustomer, String year);
    public List<Orders> findBySearch(String anything);
    public List<Orders> getCustomerOrderList(int idCustomer);
    public Connection getConnection();
    public int persist(Orders orders);
    public List<ReportOmzetVo> getCustomerOrderListForReportOmzet(int idCustomer, int year);
    public int getNextPrimaryValue(String dbSchema, String tableName);
}
