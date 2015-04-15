/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.service.serviceImpl;

import com.pustaka.common.GenericService;
import com.pustaka.dao.ProdukDao;
import com.pustaka.model.Produk;
import com.pustaka.service.ProdukService;
import com.pustaka.vo.ProdukVo;
import com.pustaka.vo.ReportProdukVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dwi
 */
@Service("produkService")
public class ProdukServiceImpl extends GenericService implements ProdukService{
    
    @Autowired
    @Qualifier("produkDao")
    private ProdukDao produkDao;

    @Override
    @Transactional(readOnly=true)
    public Produk findById(String kode) {
        return produkDao.load(kode);
    }
    
    @Override
    @Transactional(readOnly=true)
    public Produk getById(String id) {
        return produkDao.get(id);
    }

    @Override
    @Transactional
    public void save(Produk saveEntity, String user) {
        updateCreationInfo(saveEntity, user);
        produkDao.save(saveEntity);
    }

    @Override
    @Transactional
    public void update(Produk updateEntity, String user) {
        updateLastModInfo(updateEntity, user);
        produkDao.update(updateEntity);
    }

    @Override
    @Transactional
    public void delete(String kode) {
        produkDao.delete(kode);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Produk> getProdukList() {
        return produkDao.getProdukList();
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Produk> findBySearch(String anything) {
        return produkDao.findBySearch(anything);
    }

    public ProdukDao getProdukDao() {
        return produkDao;
    }

    public void setProdukDao(ProdukDao produkDao) {
        this.produkDao = produkDao;
    }

    @Override
    @Transactional(readOnly=true)
    public List<ProdukVo> getAllProductForVo() {
        return produkDao.getAllProductForVo();
    }

    @Override
    @Transactional(readOnly=true)
    public ReportProdukVo getProdukForReport(String kodeProduk, String mode) {
        return produkDao.getProdukForReport(kodeProduk, mode);
    }

}
