/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.model;

import com.pustaka.common.BaseAudit;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dwi
 */
@Table(name="pembayaran")
@Entity
public class Pembayaran extends BaseAudit implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_pembayaran")
    private int idPembayaran;
    
    @ManyToOne(targetEntity=Orders.class)
    @JoinColumn(name="id_orders",referencedColumnName="id_orders")
    private Orders orders;
    
//    @Column(name="id_orders")
//    private int idOrders;
    
    @Column(name="tgl_bayar")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tglBayar;
    
    @Column(name="bayar")
    private Long bayar;
    
    @Column(name="sisa")
    private Long sisa;
    
    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

//    public int getIdOrders() {
//        return idOrders;
//    }
//
//    public void setIdOrders(int idOrders) {
//        this.idOrders = idOrders;
//    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Date getTglBayar() {
        return tglBayar;
    }

    public void setTglBayar(Date tglBayar) {
        this.tglBayar = tglBayar;
    }

    public Long getBayar() {
        return bayar;
    }

    public void setBayar(Long bayar) {
        this.bayar = bayar;
    }

    public Long getSisa() {
        return sisa;
    }

    public void setSisa(Long sisa) {
        this.sisa = sisa;
    }

}
