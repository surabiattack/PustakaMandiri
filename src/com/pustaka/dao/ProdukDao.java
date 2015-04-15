/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao;

import com.pustaka.common.GenericDao;
import com.pustaka.model.Produk;
import com.pustaka.vo.ProdukVo;
import com.pustaka.vo.ReportProdukVo;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface ProdukDao extends GenericDao<Produk, String> {
    public Produk findById(String kode);
    public List<Produk> getProdukList();
    public List<Produk> findBySearch(String anything);
    public List<ProdukVo> getAllProductForVo();
    public ReportProdukVo getProdukForReport(String kodeProduk, String mode);
}
