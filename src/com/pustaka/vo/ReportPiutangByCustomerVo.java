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
public class ReportPiutangByCustomerVo {
    private int kodeFaktur;
    private Date tglOrder;
    private Long netto;
    private Long bayar;
    private Long sisa;
    private String status;

    public int getKodeFaktur() {
        return kodeFaktur;
    }

    public Long getNetto() {
        return netto;
    }

    public void setNetto(Long netto) {
        this.netto = netto;
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

    public void setKodeFaktur(int kodeFaktur) {
        this.kodeFaktur = kodeFaktur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTglOrder() {
        return tglOrder;
    }

    public void setTglOrder(Date tglOrder) {
        this.tglOrder = tglOrder;
    }

}
