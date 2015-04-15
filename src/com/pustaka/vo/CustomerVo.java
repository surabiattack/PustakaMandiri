/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.vo;

/**
 *
 * @author dwi
 */
public class CustomerVo {
    private int customerId;
    
    private String nama;
    
    private String alamat;
    
    private String telepon;
    
    public CustomerVo(){
        
    }
    
    public CustomerVo(int customerId, String nama, String alamat, String telepon){
        this.customerId = customerId;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
    
    
}
