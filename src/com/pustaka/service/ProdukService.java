/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service;

import com.pustaka.model.Produk;
import com.pustaka.vo.ProdukVo;
import com.pustaka.vo.ReportProdukVo;
import java.util.List;

/**
 *
 * @author dwi
 */
public interface ProdukService {
    public Produk findById(String id);
    public Produk getById(String id);
    public List<Produk> getProdukList();
    public List<ProdukVo> getAllProductForVo();
    public List<Produk> findBySearch(String anything);
    public void save(Produk saveEntity, String user);
    public void update(Produk updateEntity, String user);
    public void delete(String kodeProduk);
    public ReportProdukVo getProdukForReport(String kodeProduk, String mode);
}
