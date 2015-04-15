/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.vo;

/**
 *
 * @author dwi
 */
public class ProdukVo {
    private String kodeProduk;
    private String namaProduk;
    private Long harga;
    private Long stok;
    private int jumlah;
    private Long subTotal;
    
    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public Long getHarga() {
        return harga;
    }

    public Long getStok() {
        return stok;
    }

    public void setStok(Long stok) {
        this.stok = stok;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Long subTotal) {
        this.subTotal = subTotal;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

}
