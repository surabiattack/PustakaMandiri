/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.model;

import com.pustaka.common.BaseAudit;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author dwi
 */
@Table(name="produk")
@Entity
public class Produk extends BaseAudit implements Serializable{
    @Id
    @Column(name="kode_produk")
    private String kodeProduk;
    
    @Column(name="nama_produk")
    private String namaProduk;
    
    @Column(name="harga")
    private Long harga;
    
    @Column(name="stok")
    private Long stok;
    
    public Produk(){
        
    }
    
    public Produk(String kodeProduk, String namaProduk,Long harga, Long Stok){
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = Stok;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public Long getHarga() {
        return harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public Long getStok() {
        return stok;
    }

    public void setStok(Long stok) {
        this.stok = stok;
    }

}
