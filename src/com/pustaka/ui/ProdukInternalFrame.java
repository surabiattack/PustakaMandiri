/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.ui;

import com.pustaka.common.NumberFormat;
import com.pustaka.model.Produk;
import com.pustaka.model.UserSession;
import com.pustaka.provider.ApplicationContextProvider;
import com.pustaka.service.ProdukService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dwi
 */
public class ProdukInternalFrame extends javax.swing.JInternalFrame{
    
    private ProdukService produkService;
    private static final String DATE_FORMAT = "dd-MMM-yyyy";
    private List<Produk> tempList = new ArrayList<Produk>();
    private boolean isEdit;
    private static ProdukInternalFrame myInstance;

    /**
     * Creates new form ProdukInternalFrame
     */
    public ProdukInternalFrame() {
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        produkService = (ProdukService) appContext.getBean("produkService");
        initComponents();
        setDisplayProdukTable();
    }
    
    public static ProdukInternalFrame getInstance(){
        if(myInstance == null){
            myInstance = new ProdukInternalFrame();
        }
        return myInstance;
    }
    
    private void setDisplayProdukTable(){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        tempList = produkService.getProdukList();
        Object data[][] = new Object[tempList.size()][10];
        int x = 0;
        for (Produk produk : tempList) {
            data[x][0] = produk.getKodeProduk();
            data[x][1] = produk.getNamaProduk();
            data[x][2] = NumberFormat.getInstance().formatNumber(produk.getHarga());
            data[x][3] = produk.getStok();
            ++x;
        }
        
        String[] judul = {"Kode Produk", "Nama Produk", "Harga", "Stok"};
        produkTable.setModel(new DefaultTableModel(data, judul));
        DefaultTableCellRenderer subTotalRightRenderer = new DefaultTableCellRenderer();
        subTotalRightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        DefaultTableCellRenderer dateCenterRenderer = new DefaultTableCellRenderer();
        dateCenterRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        
        produkTable.getColumn("Harga").setCellRenderer(subTotalRightRenderer);
        produkTable.getColumn("Stok").setCellRenderer(subTotalRightRenderer);
        jScrollPane1.setViewportView(produkTable);
        produkTable.setEnabled(true);
    }
    
    private void setDisplayProdukTableAfterSearch(){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//        tempList = produkService.getProdukList();
        Object data[][] = new Object[tempList.size()][10];
        int x = 0;
        for (Produk produk : tempList) {
            data[x][0] = produk.getKodeProduk();
            data[x][1] = produk.getNamaProduk();
            data[x][2] = NumberFormat.getInstance().formatNumber(produk.getHarga());
            data[x][3] = produk.getStok();
            ++x;
        }
        
        String[] judul = {"Kode Produk", "Nama Produk", "Harga", "Stok"};
        produkTable.setModel(new DefaultTableModel(data, judul));
        jScrollPane1.setViewportView(produkTable);
        produkTable.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        namaTextField = new javax.swing.JTextField();
        hargaTextField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        kodeProdukTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        stokTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        addProdukButton = new javax.swing.JButton();
        editProdukButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        closeProdukButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        produkTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false; //Disallow the editing of any cell
            }
        };

        setClosable(true);
        setTitle("Produk");
        setPreferredSize(new java.awt.Dimension(920, 450));

        inputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));

        jLabel1.setText("Nama");

        jLabel2.setText("Harga");

        namaTextField.setEnabled(false);

        hargaTextField.setEnabled(false);

        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("*");

        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("*");

        jLabel14.setText("Rp.");

        resetButton.setText("Reset");
        resetButton.setEnabled(false);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        jLabel15.setText("Kode Produk");

        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setText("*");

        kodeProdukTextField.setEnabled(false);

        jLabel3.setText("Stok");

        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("*");

        stokTextField.setEnabled(false);

        jLabel9.setText("Search Anything ");

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        addProdukButton.setMnemonic('a');
        addProdukButton.setText("Add");
        addProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProdukButtonActionPerformed(evt);
            }
        });

        editProdukButton.setMnemonic('e');
        editProdukButton.setText("Edit");
        editProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProdukButtonActionPerformed(evt);
            }
        });

        deleteButton.setMnemonic('d');
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        closeProdukButton.setMnemonic('c');
        closeProdukButton.setText("Close");
        closeProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeProdukButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15)
                            .addGroup(inputPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)))
                        .addGap(6, 6, 6)
                        .addComponent(jLabel17))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputPanelLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel12))
                        .addGroup(inputPanelLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel11))))
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kodeProdukTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namaTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(inputPanelLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hargaTextField))
                            .addComponent(stokTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(searchTextField)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(addProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        inputPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {kodeProdukTextField, namaTextField, stokTextField});

        inputPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, resetButton, saveButton});

        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(kodeProdukTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel10))
                    .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hargaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(stokTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton)
                    .addComponent(resetButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addProdukButton)
                    .addComponent(editProdukButton)
                    .addComponent(closeProdukButton)
                    .addComponent(deleteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Table Data"));

        produkTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        produkTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(produkTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProdukButtonActionPerformed
         // TODO add your handling code here:
        setInputProduk(Boolean.TRUE);
        addProdukButton.setEnabled(Boolean.FALSE);
        editProdukButton.setEnabled(Boolean.FALSE);
        kodeProdukTextField.requestFocusInWindow();
    }//GEN-LAST:event_addProdukButtonActionPerformed

    private void closeProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeProdukButtonActionPerformed
          // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeProdukButtonActionPerformed

    private void editProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProdukButtonActionPerformed
         // TODO add your handling code here:
        if(produkTable.getSelectedRow() >= 0){
            isEdit = Boolean.TRUE;
            setInputProduk(Boolean.TRUE);
            produkTable.setEnabled(Boolean.FALSE);
            addProdukButton.setEnabled(Boolean.FALSE);
            editProdukButton.setEnabled(Boolean.FALSE);
            kodeProdukTextField.setEnabled(false);
            setFillTextField();
            namaTextField.requestFocusInWindow();
        }else{
            JOptionPane.showMessageDialog(this, "Pilih minimal row yang akan diedit" , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_editProdukButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
         // TODO add your handling code here:
        tempList = produkService.findBySearch(searchTextField.getText());
        setDisplayProdukTableAfterSearch();
//        setDisplayProdukTable();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
         // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(null, "Yakin data dihapus???","Warning",JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            Produk produk = tempList.get(produkTable.getSelectedRow());
            produkService.delete(produk.getKodeProduk());
            setDisplayProdukTable();
            JOptionPane.showMessageDialog(this, "Data telah dihapus" , "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        setReset();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        setInputProduk(Boolean.FALSE);
        setReset();
        addProdukButton.setEnabled(Boolean.TRUE);
        editProdukButton.setEnabled(Boolean.TRUE);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        if(isInputValid()){
            try{
                if(!isEdit){ // SAVE ENTITY PRODUK
                    Produk temp = produkService.getById(kodeProdukTextField.getText());
                    if(temp == null){
                        Produk saveEntity = new Produk(kodeProdukTextField.getText(),namaTextField.getText(), new Long(hargaTextField.getText()),new Long(stokTextField.getText()));

                        produkService.save(saveEntity, UserSession.getUsername());
                    }else{
                        JOptionPane.showMessageDialog(this, "Kode produk sudah ada! " , "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                }else{      // UPDATE ENTITY PRODUK
                    Produk produk = tempList.get(produkTable.getSelectedRow());
                    
                    Produk oldValue = produkService.getById(produk.getKodeProduk());
                    oldValue.setNamaProduk(namaTextField.getText());
                    oldValue.setHarga(Long.valueOf(hargaTextField.getText()));
                    oldValue.setStok(Long.valueOf(stokTextField.getText()));

                    produkService.update(oldValue, UserSession.getUsername());
                    
                }

                setInputProduk(Boolean.FALSE);
                setReset();
                setDisplayProdukTable();
                isEdit = Boolean.FALSE;
                addProdukButton.setEnabled(Boolean.TRUE);
                editProdukButton.setEnabled(Boolean.TRUE);
                JOptionPane.showMessageDialog(this, "Saved successfully ", "Success", JOptionPane.INFORMATION_MESSAGE);

            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error " +e.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void setFillTextField(){
        Produk produk = tempList.get(produkTable.getSelectedRow());
        kodeProdukTextField.setText(produk.getKodeProduk());
        namaTextField.setText(produk.getNamaProduk());
        hargaTextField.setText(String.valueOf(produk.getHarga()));
        stokTextField.setText(String.valueOf(produk.getStok()));
    }
    
    private boolean isInputValid(){
        Boolean isValid = Boolean.TRUE;
        
        if(kodeProdukTextField.getText().isEmpty() || kodeProdukTextField.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Kode tidak boleh kosong" , "Error ", JOptionPane.ERROR_MESSAGE);
            isValid = Boolean.FALSE;
        }
        
        if(namaTextField.getText().isEmpty() || namaTextField.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong" , "Error ", JOptionPane.ERROR_MESSAGE);
            isValid = Boolean.FALSE;
        }
        
        if(hargaTextField.getText().isEmpty() || hargaTextField.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Harga tidak boleh kosong" , "Error ", JOptionPane.ERROR_MESSAGE);
            isValid = Boolean.FALSE;
        }
        
        if(!hargaTextField.getText().isEmpty()){
            try{
                Double harga = new Double(hargaTextField.getText());
                if(harga < 0){
                    JOptionPane.showMessageDialog(this, "Harga tidak boleh kurang dari 0" , "Warning", JOptionPane.WARNING_MESSAGE);
                    hargaTextField.setRequestFocusEnabled(Boolean.TRUE);
                    isValid = Boolean.FALSE;
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "Error harga harus angka !!!" , "Error", JOptionPane.ERROR_MESSAGE);
                hargaTextField.setRequestFocusEnabled(Boolean.TRUE);
                isValid = Boolean.FALSE;
            }
        }
        
        return isValid;
    }
    
    private void setInputProduk(boolean status){
        kodeProdukTextField.setEnabled(status);
        namaTextField.setEnabled(status);
        hargaTextField.setEnabled(status);
        stokTextField.setEnabled(status);
        saveButton.setEnabled(status);
        cancelButton.setEnabled(status);
        resetButton.setEnabled(status);
    }
    
    private void setReset(){
        kodeProdukTextField.setText("");
        namaTextField.setText("");
        hargaTextField.setText("");
        stokTextField.setText("");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProdukButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton closeProdukButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editProdukButton;
    private javax.swing.JTextField hargaTextField;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kodeProdukTextField;
    private javax.swing.JTextField namaTextField;
    private javax.swing.JTable produkTable;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTextField stokTextField;
    // End of variables declaration//GEN-END:variables

    public ProdukService getProdukService() {
        return produkService;
    }

    public void setProdukService(ProdukService produkService) {
        this.produkService = produkService;
    }

    public List<Produk> getTempList() {
        return tempList;
    }

    public void setTempList(List<Produk> tempList) {
        this.tempList = tempList;
    }

    public static ProdukInternalFrame getMyInstance() {
        return myInstance;
    }

    public static void setMyInstance(ProdukInternalFrame myInstance) {
        ProdukInternalFrame.myInstance = myInstance;
    }

}
