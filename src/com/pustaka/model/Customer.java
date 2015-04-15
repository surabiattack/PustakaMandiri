/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.model;

import com.pustaka.common.BaseAudit;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author dwi
 */
@Table(name="customer")
@Entity
public class Customer extends BaseAudit implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_customer")
    private int customerId;
    
    @Column(name="nama")
    private String nama;
    
    @Column(name="alamat")
    private String alamat;
    
    @Column(name="telepon")
    private String telepon;
    
    public Customer(){
        
    }
    
    public Customer(String nama, String alamat, String telepon){
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
