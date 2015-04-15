/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.common;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author dwi
 */
public class ItemChangeListener implements ItemListener{
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            Object item = e.getItem();
        }
    }
    
}
