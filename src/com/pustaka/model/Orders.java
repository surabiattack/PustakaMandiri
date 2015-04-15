/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.model;

import com.pustaka.common.BaseAudit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author dwi
 */
@Table(name="orders")
@Entity
public class Orders extends BaseAudit implements Serializable{
    
    public final static String TUNAI = "TUNAI";
    public final static String KREDIT = "KREDIT";
    public final static String LUNAS = "LUNAS";
    public final static String BELUM_LUNAS = "BELUM_LUNAS";
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_orders")
    private int idOrders;
    
    @Column(name="id_customer")
    private int idCustomer;
    
    @Column(name="tgl_order")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tglOder;
    
    @Column(name="status")
    private String status;
    
    @Column(name="cara_beli")
    private String caraBeli;
    
    @Column(name="bruto")
    private Long bruto;
    
    @Column(name="diskon")
    private int diskon;
    
    @Column(name="netto")
    private Long netto;
    
    @Column(name="keterangan")
    private String keterangan;
    
    @OneToMany(mappedBy="orders",cascade= CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
    
    @OneToMany(mappedBy="orders",cascade= CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Pembayaran> pembayaranList = new ArrayList<Pembayaran>();

    public int getIdOrders() {
        return idOrders;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setIdOrders(int idOrders) {
        this.idOrders = idOrders;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Date getTglOder() {
        return tglOder;
    }

    public void setTglOder(Date tglOder) {
        this.tglOder = tglOder;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaraBeli() {
        return caraBeli;
    }

    public void setCaraBeli(String caraBeli) {
        this.caraBeli = caraBeli;
    }

    public Long getBruto() {
        return bruto;
    }

    public void setBruto(Long bruto) {
        this.bruto = bruto;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public Long getNetto() {
        return netto;
    }

    public void setNetto(Long netto) {
        this.netto = netto;
    }

    public List<Pembayaran> getPembayaranList() {
        return pembayaranList;
    }

    public void setPembayaranList(List<Pembayaran> pembayaranList) {
        this.pembayaranList = pembayaranList;
    }
    
}
