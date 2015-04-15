/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao.daoImpl;

import com.pustaka.common.GenericDaoImpl;
import com.pustaka.dao.OrdersDao;
import com.pustaka.model.Customer;
import com.pustaka.model.Orders;
import com.pustaka.vo.ReportOmzetVo;
import com.pustaka.vo.ReportPenjualanByCustomerVo;
import com.pustaka.vo.ReportPenjualanByProdukVo;
import com.pustaka.vo.ReportPiutangByCustomerVo;
import com.pustaka.vo.ReportPiutangByFakturVo;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.Date;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dwi
 */
@Repository("ordersDao")
public class OrdersDaoImpl extends GenericDaoImpl<Orders, Integer> implements OrdersDao {

    @Override
    public Orders findById(Integer id) {
        Criteria crit = getCurrentSession().createCriteria(Orders.class);
        crit.add(Restrictions.eq("id_customer", id));
        return (Orders) crit.uniqueResult();
    }

    @Override
    public List<Orders> getOrderList() {
        Criteria crit = getCurrentSession().createCriteria(Orders.class);
        return crit.list();
    }

    @Override
    public List<Orders> findBySearch(String anything) {
        StringBuilder sb = new StringBuilder("SELECT id_customer, nama, alamat, telepon "
                + " FROM CUSTOMER WHERE id_customer like '%" + anything + "%'"
                + " OR nama like '%" + anything + "%' "
                + " OR alamat like '%" + anything + "%' "
                + " OR telepon like '%" + anything + "%'");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] result, String[] aliases) {
                Customer customer = null;

                try {
                    customer = new Customer();
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
    public List<Orders> getCustomerOrderList(int idCustomer) {
        Criteria crit = getCurrentSession().createCriteria(Orders.class);
        crit.add(Restrictions.eq("idCustomer", idCustomer));
        crit.addOrder(Order.desc("tglOder"));
        return crit.list();
    }

    @Override
    public List<ReportOmzetVo> getCustomerOrderListForReportOmzet(int idCustomer, int year) {
        StringBuilder sb = new StringBuilder("SELECT o.id_orders, nama, bruto, diskon, "
                + "netto,(SELECT SUM(bayar) FROM pembayaran p WHERE p.id_orders = o.id_orders) AS bayar , "
                + "(SELECT sisa FROM pembayaran p WHERE p.id_orders = o.id_orders ORDER BY tgl_bayar DESC LIMIT 1) AS sisa ,"
                + "(SELECT tgl_bayar FROM pembayaran p WHERE p.id_orders = o.id_orders ORDER BY tgl_bayar DESC LIMIT 1) AS tgl_bayar, "
                + "keterangan , o.id_customer FROM orders o JOIN customer c ON o.id_customer = c.id_customer "
                + "WHERE o.id_customer = " + idCustomer + " AND YEAR(tgl_order) = " + year + " ");

        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] os, String[] strings) {
                ReportOmzetVo vo = null;
                try {
                    vo = new ReportOmzetVo();
                    vo.setNoFaktur((Integer) os[0]);
                    vo.setNamaCustomer((String) os[1]);
                    vo.setBruto(((Double) os[2]).longValue());
                    vo.setDiskon(((Integer) os[3]).intValue());
                    vo.setNetto(((Double) os[4]).longValue());
                    vo.setBayar(os[5] != null ? ((Double) os[5]).longValue() : 0);
                    vo.setSisa(os[6] != null ? ((Double) os[6]).longValue() : 0);
                    vo.setTglBayar((Date) os[7]);
                    vo.setKeterangan(os[8] != null ? (String) os[8] : "");
                    vo.setIdCustomer((Integer) os[9]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return vo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });

        return result.list();
    }

    @Override
    public List<ReportOmzetVo> getOrderByYearList(int year) {
        String sb = "SELECT o.id_orders, nama, tgl_order, bruto, diskon, "
                + "netto,(SELECT SUM(bayar) FROM pembayaran p WHERE p.id_orders = o.id_orders) AS bayar,"
                + "(SELECT sisa FROM pembayaran p WHERE p.id_orders = o.id_orders ORDER BY tgl_bayar DESC LIMIT 1) AS sisa, "
                + "(SELECT tgl_bayar FROM pembayaran p WHERE p.id_orders = o.id_orders ORDER BY tgl_bayar DESC LIMIT 1) AS tgl_bayar, "
                + "keterangan ,o.id_customer FROM orders o "
                + "JOIN customer c ON o.id_customer = c.id_customer "
                + "WHERE YEAR(tgl_order) = " + year + "";

        System.out.println("query data == " + sb);
        SQLQuery result = getCurrentSession().createSQLQuery(sb);
        result.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] os, String[] strings) {
                ReportOmzetVo vo = null;
                try {
                    vo = new ReportOmzetVo();
                    vo.setNoFaktur((Integer) os[0]);
                    vo.setNamaCustomer((String) os[1]);
                    vo.setTglOrder((Date) os[2]);
                    vo.setBruto(((Double) os[3]).longValue());
                    vo.setDiskon(((Integer) os[4]).intValue());
                    vo.setNetto(((Double) os[5]).longValue());
                    vo.setBayar(os[6] != null ? ((Double) os[6]).longValue() : 0);
                    vo.setSisa(os[7] != null ? ((Double) os[7]).longValue() : 0);
                    vo.setTglBayar((Date) os[8]);
                    vo.setKeterangan(os[9] != null ? (String) os[9] : "");
                    vo.setIdCustomer((Integer) os[10]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return vo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });

        return result.list();
    }

    @Override
    public List<ReportPenjualanByProdukVo> getReportByProdukList(String kodeProduk, String year) {
        StringBuilder sb = new StringBuilder("SELECT nama , tgl_order, jumlah FROM "
                + " order_detail od join orders o on od.id_orders = o.id_orders "
                + " JOIN customer c on c.id_customer = o.id_customer  where od.kode_produk = '" + kodeProduk + "' "
                + " and YEAR(o.tgl_order) = " + year + " ORDER BY o.tgl_order DESC");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] os, String[] strings) {
                ReportPenjualanByProdukVo vo = null;
                try {
                    vo = new ReportPenjualanByProdukVo();
                    vo.setNamaCustomer((String) os[0]);
                    vo.setTglOrder((Date) os[1]);
                    vo.setQuantity((Integer) os[2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return vo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });
        return result.list();
    }

    @Override
    public List<ReportPenjualanByCustomerVo> getReportByCustomerList(int idCustomer, String year) {
        StringBuilder sb = new StringBuilder("SELECT p.kode_produk, nama_produk, jumlah, subtotal "
                + ", o.tgl_order FROM orders o join customer c on o.id_customer = c.id_customer "
                + "join order_detail od on od.id_orders = o.id_orders "
                + "join produk p on p.kode_produk = od.kode_produk where c.id_customer = " + idCustomer + " "
                + "and YEAR(o.tgl_order) = " + year + " ORDER BY o.tgl_order DESC");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] os, String[] strings) {
                ReportPenjualanByCustomerVo vo = null;
                try {
                    vo = new ReportPenjualanByCustomerVo();
                    vo.setKodeProduk((String) os[0]);
                    vo.setNamaProduk((String) os[1]);
                    vo.setQuantity((Integer) os[2]);
                    vo.setSubTotal(((Double) os[3]).longValue());
                    vo.setTglOrder((Date) os[4]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return vo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });
        return result.list();
    }

    @Override
    public List<ReportPiutangByFakturVo> getReportPiutangByFakturList(int idOrders, String year) {
        StringBuilder sb = new StringBuilder("SELECT o.id_orders,tgl_order, nama, nama_produk, harga, status , jumlah, subtotal "
                + "FROM orders o join customer c on o.id_customer = c.id_customer "
                + "join order_detail od on od.id_orders = o.id_orders "
                + "join produk p on p.kode_produk = od.kode_produk where o.id_orders = " + idOrders + " "
                + "and YEAR(o.tgl_order) = " + year + " AND status = '" + Orders.BELUM_LUNAS + "' ");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] os, String[] strings) {
                ReportPiutangByFakturVo vo = null;
                try {
                    vo = new ReportPiutangByFakturVo();
                    vo.setIdOrder((Integer) os[0]);
                    vo.setTglOrder((Date) os[1]);
                    vo.setNamaCustomer((String) os[2]);
                    vo.setProduk((String) os[3]);
                    vo.setHarga(((Double) os[4]).longValue());
                    vo.setStatus((String) os[5]);
                    vo.setJumlah(((Integer) os[6]).longValue());
                    vo.setSubTotal(((Double) os[7]).longValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return vo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });
        return result.list();
    }

    @Override
    public List<ReportPiutangByCustomerVo> getReportPiutangByCustomerList(int idCustomer, String year) {
//        StringBuilder sb= new StringBuilder("SELECT o.id_orders, tgl_order, netto, status "
//                + "FROM orders o join customer c on o.id_customer = c.id_customer JOIN pembayaran p ON p.id_orders = o.id_orders"
//                + "where c.id_customer = "+idCustomer+" "
//                + "and YEAR(o.tgl_order) = "+year+" AND status = '"+Orders.BELUM_LUNAS+"' ");
        StringBuilder sb = new StringBuilder("SELECT o.id_orders, tgl_order, netto, bayar, sisa, status "
                + "FROM orders o join customer c on o.id_customer = c.id_customer JOIN pembayaran p ON p.id_orders = o.id_orders "
                + "where c.id_customer = " + idCustomer + " "
                + "and YEAR(o.tgl_order) = " + year + " AND status = '" + Orders.BELUM_LUNAS + "' ORDER BY p.tgl_bayar DESC ");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] os, String[] strings) {
                ReportPiutangByCustomerVo vo = null;
                try {
                    vo = new ReportPiutangByCustomerVo();
                    vo.setKodeFaktur((Integer) os[0]);
                    vo.setTglOrder((Date) os[1]);
                    vo.setNetto(((Double) os[2]).longValue());
                    vo.setBayar(os[3] != null ? ((Double) os[3]).longValue() : 0);
                    vo.setSisa(os[4] != null ? ((Double) os[4]).longValue() : 0);
                    vo.setStatus((String) os[5]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return vo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });
        return result.list();
    }

    @Override
    public Connection getConnection() {
        try {
            SessionFactoryImpl sfi = (SessionFactoryImpl) getSessionFactory();
            Connection conn = sfi.getConnectionProvider().getConnection();
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int persist(Orders orders) {
        getCurrentSession().persist(orders);
        return orders.getIdCustomer();
    }

    @Override
    public int getNextPrimaryValue(String dbSchema, String tableName) {
        StringBuilder sb = new StringBuilder("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = '" + dbSchema + "' AND TABLE_NAME = '" + tableName + "' ");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        
        return ((BigInteger) result.uniqueResult()).intValue();
    }
}
