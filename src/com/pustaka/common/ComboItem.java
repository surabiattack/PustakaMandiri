/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.common;

import javax.swing.JComboBox;

/**
 *
 * @author dwi
 */
public class ComboItem {
    private String key;
    private String value;

    public ComboItem(String key, String value)
    {
        this.key = key;
        this.value = value;
    }
    
    public static ComboItem setSelectedValue(JComboBox comboBox, String value)
    {
        ComboItem item = null;
        for (int i = 0; i < comboBox.getItemCount(); i++)
        {
            item = (ComboItem)comboBox.getItemAt(i);
            if (item.getValue().equals(value))
            {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
        return item;
    }
    
    @Override
    public String toString()
    {
        return key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
