/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.vo;

/**
 *
 * @author dwi
 */
public class ReportProdukVo {
    private Long qty;
    private Long harga;
    private Long jumlah;
    
    public ReportProdukVo(){
        
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getHarga() {
        return harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public Long getJumlah() {
        return jumlah;
    }

    public void setJumlah(Long jumlah) {
        this.jumlah = jumlah;
    }
    
}
