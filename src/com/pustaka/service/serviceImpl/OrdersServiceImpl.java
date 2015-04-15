/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service.serviceImpl;

import com.pustaka.common.GenericService;
import com.pustaka.dao.OrdersDao;
import com.pustaka.model.Orders;
import com.pustaka.service.OrdersService;
import com.pustaka.vo.ReportOmzetVo;
import com.pustaka.vo.ReportPenjualanByCustomerVo;
import com.pustaka.vo.ReportPenjualanByProdukVo;
import com.pustaka.vo.ReportPiutangByCustomerVo;
import com.pustaka.vo.ReportPiutangByFakturVo;
import java.sql.Connection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dwi
 */
@Service("ordersService")
@Transactional
public class OrdersServiceImpl extends GenericService implements OrdersService{
    
    @Autowired
    @Qualifier("ordersDao")
    private OrdersDao ordersDao;

    @Override
    public Orders findById(int id) {
        return ordersDao.get(id);
    }

    @Override
    public void save(Orders saveEntity, String user) {
        updateCreationInfo(saveEntity, user);
        ordersDao.save(saveEntity);
    }

    @Override
    public void update(Orders updateEntity, String user) {
        updateLastModInfo(updateEntity, user);
        ordersDao.update(updateEntity);
    }

    @Override
    public void delete(Integer id) {
        ordersDao.delete(id);
    }
    
    @Override
    public List<Orders> getOrderList() {
        return ordersDao.getOrderList();
    }
    
    @Override
    public List<Orders> findBySearch(String anything) {
        return ordersDao.findBySearch(anything);
    }
    
    @Override
    public List<Orders> getCustomerOrderList(int idCustomer) {
        return ordersDao.getCustomerOrderList(idCustomer);
    }

    public OrdersDao getOrdersDao() {
        return ordersDao;
    }

    public void setOrdersDao(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    @Override
    public List<ReportOmzetVo> getOrderByYearList(int year) {
        return ordersDao.getOrderByYearList(year);
    }

    @Override
    public List<ReportPenjualanByProdukVo> getReportByProdukList(String kodeProduk, String year) {
        return ordersDao.getReportByProdukList(kodeProduk, year);
    }

    @Override
    public List<ReportPenjualanByCustomerVo> getReportByCustomerList(int idCustomer, String year) {
        return ordersDao.getReportByCustomerList(idCustomer, year);
    }

    @Override
    public List<ReportPiutangByFakturVo> getReportPiutangByFakturList(int idOrders, String year) {
        return ordersDao.getReportPiutangByFakturList(idOrders, year);
    }

    @Override
    public List<ReportPiutangByCustomerVo> getReportPiutangByCustomerList(int idCustomer, String year) {
        return ordersDao.getReportPiutangByCustomerList(idCustomer, year);
    }

    @Override
    public Connection getConnection(){
        return ordersDao.getConnection();
    }

    @Override
    public int persist(Orders orders) {
        return ordersDao.persist(orders);
    }

    @Override
    public List<ReportOmzetVo> getCustomerOrderListForReportOmzet(int idCustomer, int month) {
        return ordersDao.getCustomerOrderListForReportOmzet(idCustomer, month);
    }

    @Override
    public int getNextPrimaryValue(String dbSchema, String tableName) {
        return ordersDao.getNextPrimaryValue(dbSchema, tableName);
    }

}
