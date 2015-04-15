/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao.daoImpl;

import com.pustaka.common.GenericDaoImpl;
import com.pustaka.dao.ProdukDao;
import com.pustaka.model.Produk;
import com.pustaka.vo.ProdukVo;
import com.pustaka.vo.ReportProdukVo;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dwi
 */
@Repository("produkDao")
public class ProdukDaoImpl extends GenericDaoImpl<Produk, String> implements ProdukDao{

    @Override
    public Produk findById(String id) {
        Criteria crit = getCurrentSession().createCriteria(Produk.class);
        crit.add(Restrictions.eq("kodeProduk", id));
        return (Produk)crit.uniqueResult();
    }

    @Override
    public List<Produk> getProdukList() {
        Criteria crit = getCurrentSession().createCriteria(Produk.class);
        crit.addOrder(Order.asc("namaProduk"));
        return crit.list();
    }

    @Override
    public List<Produk> findBySearch(String anything) {
//        Criteria crit = getCurrentSession().createCriteria(Produk.class);
//        crit.add(
//                Restrictions.or(Restrictions.ilike("idProduk", anything, MatchMode.ANYWHERE), 
//                Restrictions.or(Restrictions.ilike("namaProduk", anything, MatchMode.ANYWHERE),
//                Restrictions.or(Restrictions.ilike("harga", anything, MatchMode.ANYWHERE),Restrictions.ilike("stok", anything, MatchMode.ANYWHERE)))));
//        return crit.list();
        StringBuilder sb= new StringBuilder("SELECT kode_produk, nama_produk, harga, stok "
                + " FROM produk WHERE kode_produk like '%"+anything+"%'"
                + " OR nama_produk like '%"+anything+"%' or harga like '%"+anything+"%' ");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] result, String[] aliases) {
                Produk produk= null;
                
                try {
                    produk= new Produk();
                    produk.setKodeProduk((String) result[0]);
                    produk.setNamaProduk((String) result[1]);
                    produk.setHarga(((Double) result[2]).longValue());
                    produk.setStok(((Integer) result[3]).longValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return produk;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }); 
        
        return result.list();
    }
    
    @Override
    public List<ProdukVo> getAllProductForVo() {
        StringBuilder sb= new StringBuilder("SELECT kode_produk, nama_produk, harga, stok "
                + " FROM produk order by kode_produk+0 asc");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] result, String[] aliases) {
                ProdukVo produkVo= null;
                
                try {
                    produkVo= new ProdukVo();
                    produkVo.setKodeProduk((String) result[0]);
                    produkVo.setNamaProduk((String) result[1]);
                    produkVo.setHarga(((Double) result[2]).longValue());
                    produkVo.setStok(((Integer) result[3]).longValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return produkVo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }); 
        
        return result.list();
    }
    
    @Override
    public ReportProdukVo getProdukForReport(String kodeProduk, String mode) {
        StringBuilder sb= new StringBuilder("");
        
        if(StringUtils.equals(mode, "produksi")){
            sb.append("SELECT SUM(jumlah)+stok, harga, (SUM(jumlah)+stok) * harga "
                + "FROM produk p JOIN order_detail od ON p.kode_produk = od.kode_produk "
                + "WHERE p.kode_produk = '"+kodeProduk+"'");
        }else if(StringUtils.equals(mode, "penjualan")){
            sb.append("SELECT SUM(jumlah), harga, SUM(jumlah) * harga "
                + "FROM produk p JOIN order_detail od ON p.kode_produk = od.kode_produk "
                + "WHERE p.kode_produk = '"+kodeProduk+"'");
        }else{
            sb.append("SELECT STOK, harga, stok*harga FROM produk p "
                + "WHERE p.kode_produk = '"+kodeProduk+"'");
        }
        
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] result, String[] aliases) {
                ReportProdukVo reportProdukVo= new ReportProdukVo();
                
                try {
                    reportProdukVo.setQty((BigDecimal) result[0] == null ? 0L: ((BigDecimal) result[0]).longValue());
                    reportProdukVo.setHarga((Double) result[1] == null ? 0L : ((Double) result[1]).longValue());
                    reportProdukVo.setJumlah((Double) result[2] == null ? 0L:((Double) result[2]).longValue());
                }catch (ClassCastException ce) {
                    reportProdukVo.setQty((Integer) result[0] == null ? 0L: ((Integer) result[0]).longValue());
                    reportProdukVo.setHarga((Double) result[1] == null ? 0L : ((Double) result[1]).longValue());
                    reportProdukVo.setJumlah((Double) result[2] == null ? 0L:((Double) result[2]).longValue());
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return reportProdukVo;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }); 
        
        return (ReportProdukVo) result.uniqueResult();
    }
    
}
