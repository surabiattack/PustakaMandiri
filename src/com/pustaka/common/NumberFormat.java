/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author dwi
 */
public class NumberFormat {
    
    private static NumberFormat numberFormat;
    
    public synchronized static NumberFormat getInstance(){
        NumberFormat temp = null;
        if(numberFormat == null){
            numberFormat = new NumberFormat();
            temp = numberFormat;
        }else {
            temp = numberFormat;
        }
        
        return temp;
    }
    
    public String formatNumber(Long harga){
        Locale localeIndo = new Locale("id","ID");
        DecimalFormat kursIndonesia = (DecimalFormat) java.text.NumberFormat.getCurrencyInstance(localeIndo);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
//        formatRp.setCurrencySymbol("Rp. ");
        kursIndonesia.applyPattern("###,###");
        
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(harga);
    }

    public static NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public static void setNumberFormat(NumberFormat numberFormat) {
        NumberFormat.numberFormat = numberFormat;
    }
    
}
