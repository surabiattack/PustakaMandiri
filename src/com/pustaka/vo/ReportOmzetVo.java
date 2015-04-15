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
public class ReportOmzetVo {
    private int noFaktur;
    private int idCustomer;
    private String namaCustomer;
    private Date tglOrder;
    private Date tglBayar;
    private Long bruto;
    private int diskon;
    private Long netto;
    private Long bayar;
    private Long sisa;
    private String keterangan;

    public Date getTglBayar() {
        return tglBayar;
    }

    public void setTglBayar(Date tglBayar) {
        this.tglBayar = tglBayar;
    }

    public int getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(int noFaktur) {
        this.noFaktur = noFaktur;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public Long getBruto() {
        return bruto;
    }

    public Date getTglOrder() {
        return tglOrder;
    }

    public void setTglOrder(Date tglOrder) {
        this.tglOrder = tglOrder;
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }


}
