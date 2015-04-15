/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.common;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

/**
 *
 * @author dwi
 */
public class ViewMenuItem extends JMenuItem implements Comparable<JInternalFrame>{
    private JInternalFrame childFrame;  
        ViewMenuItem(String childName, JInternalFrame childFrame){  
            super(childName);  
            this.childFrame = childFrame;  
        }  
        public int compareTo(JInternalFrame o) {  
            if(childFrame.equals(o))  
                return 0;  
            else  
                return -1;  
        }  
}
