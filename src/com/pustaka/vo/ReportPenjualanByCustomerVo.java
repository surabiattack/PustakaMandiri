/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.vo;

import java.util.Date;

/**
 *
 * @author dwi
 */
public class ReportPenjualanByCustomerVo {
    private String kodeProduk;
    private String namaProduk;
    private int quantity;
    private Date tglOrder;
    private Long subTotal;

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getSubTotal() {
        return subTotal;
    }

    public Date getTglOrder() {
        return tglOrder;
    }

    public void setTglOrder(Date tglOrder) {
        this.tglOrder = tglOrder;
    }

    public void setSubTotal(Long subTotal) {
        this.subTotal = subTotal;
    }
    
}
