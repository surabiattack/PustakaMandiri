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
public class ReportPenjualanByProdukVo {
    private String namaCustomer;
    private Date tglOrder;
    private int quantity;

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public Date getTglOrder() {
        return tglOrder;
    }

    public void setTglOrder(Date tglOrder) {
        this.tglOrder = tglOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
