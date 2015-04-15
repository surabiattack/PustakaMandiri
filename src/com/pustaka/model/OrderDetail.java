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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author dwi
 */
@Table(name="order_detail")
@Entity
public class OrderDetail extends BaseAudit implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_order_detail")
    private int idOrderDetail;
    
    @ManyToOne(targetEntity=Orders.class)
    @JoinColumn(name="id_orders",referencedColumnName="id_orders")
    private Orders orders;
    
    @Column(name="kode_produk")
    private String kodeProduk;
    
    @Column(name="jumlah")
    private int jumlah;
    
    @Column(name="subtotal")
    private Long subtotal;

    public int getIdOrderDetail() {
        return idOrderDetail;
    }

    public void setIdOrderDetail(int idOrderDetail) {
        this.idOrderDetail = idOrderDetail;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }
    
}
