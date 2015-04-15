/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.ui;

import com.pustaka.common.ComboItem;
import com.pustaka.common.CommonConstant;
import com.pustaka.common.NumberFormat;
import com.pustaka.common.TableColumnAdjuster;
import com.pustaka.model.Customer;
import com.pustaka.model.OrderDetail;
import com.pustaka.model.Orders;
import com.pustaka.model.Pembayaran;
import com.pustaka.model.UserSession;
import com.pustaka.provider.ApplicationContextProvider;
import com.pustaka.service.CustomerService;
import com.pustaka.service.OrdersService;
import com.pustaka.service.PembayaranService;
import com.pustaka.service.ProdukService;
import com.pustaka.vo.ProdukVo;
import com.pustaka.vo.ReportPiutangByCustomerVo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dwi
 */
public class PembayaranUi extends javax.swing.JInternalFrame {

    private SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT);
    private static PembayaranUi myInstance;
    private OrdersService ordersService;
    private CustomerService customerService;
    private ProdukService produkService;
    private PembayaranService pembayaranService;
    private List<ProdukVo> produkVoList = new ArrayList<ProdukVo>();
    static Logger logger = Logger.getLogger(PembayaranUi.class);

    /**
     * Creates new form Pembayaran
     */
    public PembayaranUi() {
        initComponents();
        logger.info("Constructing Order UI");
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        ordersService = (OrdersService) appContext.getBean("ordersService");
        customerService = (CustomerService) appContext.getBean("customerService");
        produkService = (ProdukService) appContext.getBean("produkService");
        pembayaranService = (PembayaranService) appContext.getBean("pembayaranService");
    }

    public static PembayaranUi getInstance() {
        if (myInstance == null) {
            myInstance = new PembayaranUi();
        }
        return myInstance;
    }

    private void setDisplayProdukTable(List<OrderDetail> odList) {
        produkVoList = produkService.getAllProductForVo();
        Object data[][] = new Object[produkVoList.size()][10];
        int x = 0;
        for (ProdukVo produk : produkVoList) {
            data[x][0] = produk.getKodeProduk();
            data[x][1] = produk.getNamaProduk();
            data[x][2] = NumberFormat.getInstance().formatNumber(produk.getHarga());
            for (OrderDetail od : odList) {
                if (od.getKodeProduk().equals(produk.getKodeProduk())) {
                    data[x][3] = od.getJumlah();
                    data[x][4] = od.getSubtotal();
                }
            }

            ++x;
        }

        String[] judul = {"Kode Produk", "Nama Produk", "Harga", "Jumlah", "Sub Total"};
        produkListTable.setModel(new DefaultTableModel(data, judul));

        // Align table content 
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        produkListTable.getColumn("Kode Produk").setCellRenderer(centerRenderer);
        produkListTable.getColumn("Harga").setCellRenderer(rightRenderer);
        produkListTable.getColumn("Jumlah").setCellRenderer(centerRenderer);
        produkListTable.getColumn("Sub Total").setCellRenderer(rightRenderer);

//        produkListTable.getColumn("Nama Produk").setCellRenderer(new MyCellRenderer());
        produkScrollPane.setViewportView(produkListTable);
        produkListTable.setEnabled(true);

        // Auto Size for each column
//        produkListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(produkListTable);
        tca.adjustColumns();
        
    }
    
    private void setPembayaranTable(List<Pembayaran> pembayaranList) {
        Object data[][] = new Object[pembayaranList.size()][10];
        int x = 0;
        Long tempJumlahBayar = 0l;
        for (Pembayaran bayar : pembayaranList) {
            data[x][0] = x+1;
            data[x][1] = sdf.format(bayar.getTglBayar());
            data[x][2] = NumberFormat.getInstance().formatNumber(bayar.getBayar());
            data[x][3] = NumberFormat.getInstance().formatNumber(bayar.getSisa());
            tempJumlahBayar += bayar.getBayar();
            ++x;
        }

        totalBayarTF.setText(NumberFormat.getInstance().formatNumber(tempJumlahBayar));
        String[] judul = {"Pembayaran Ke - ", "Tanggal Bayar", "Jumlah Bayar", "Sisa"};
        bayarTable.setModel(new DefaultTableModel(data, judul));

        // Align table content 
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        bayarTable.getColumn("Pembayaran Ke - ").setCellRenderer(centerRenderer);
        bayarTable.getColumn("Tanggal Bayar").setCellRenderer(centerRenderer);
        bayarTable.getColumn("Jumlah Bayar").setCellRenderer(rightRenderer);
        bayarTable.getColumn("Sisa").setCellRenderer(rightRenderer);

//        produkListTable.getColumn("Nama Produk").setCellRenderer(new MyCellRenderer());
        bayarScrollPane.setViewportView(bayarTable);
        bayarScrollPane.setEnabled(true);

        // Auto Size for each column
//        produkListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(bayarTable);
        tca.adjustColumns();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgPembayaran = new javax.swing.ButtonGroup();
        passwordDialog = new javax.swing.JDialog();
        passwordLabel = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        inputBayarButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        produkScrollPane = new javax.swing.JScrollPane();
        produkListTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false; //Disallow the editing of any cell
            }
        };
        tunaiRB = new javax.swing.JRadioButton();
        kreditRB = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        brutoTF = new javax.swing.JTextField("0");
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        diskonTF = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        nettoTF = new javax.swing.JTextField("0");
        diskonValueTF = new javax.swing.JTextField("0");
        jLabel30 = new javax.swing.JLabel();
        totalBayarTF = new javax.swing.JTextField("0");
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        sisaBayarTF = new javax.swing.JTextField("0");
        jLabel33 = new javax.swing.JLabel();
        bayarScrollPane = new javax.swing.JScrollPane();
        bayarTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false; //Disallow the editing of any cell
            }
        };
        jSimpan = new javax.swing.JButton();
        jumlahBayarCicilTf = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        namaCustomerTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        alamatCustomerTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        teleponCustomerTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        noFaktur = new javax.swing.JTextField();
        tglFaktur = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jCari = new javax.swing.JButton();

        passwordLabel.setText("Password");

        inputBayarButton.setText("OK");
        inputBayarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputBayarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout passwordDialogLayout = new javax.swing.GroupLayout(passwordDialog.getContentPane());
        passwordDialog.getContentPane().setLayout(passwordDialogLayout);
        passwordDialogLayout.setHorizontalGroup(
            passwordDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passwordDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputBayarButton)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        passwordDialogLayout.setVerticalGroup(
            passwordDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passwordDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(passwordDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputBayarButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Add Order"));
        jPanel3.setEnabled(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Produk List"));

        produkListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        produkListTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        produkListTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        produkScrollPane.setViewportView(produkListTable);

        bgPembayaran.add(tunaiRB);
        tunaiRB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tunaiRB.setText("Tunai");
        tunaiRB.setEnabled(false);

        bgPembayaran.add(kreditRB);
        kreditRB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        kreditRB.setText("Kredit");
        kreditRB.setEnabled(false);

        jLabel26.setText("Total Bruto");

        jLabel29.setText(":");

        brutoTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        brutoTF.setEnabled(false);
        brutoTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brutoTFActionPerformed(evt);
            }
        });

        jLabel25.setText("Disc %");

        jLabel28.setText(":");

        diskonTF.setEnabled(false);

        jLabel27.setText("Total Netto");

        jLabel24.setText(":");

        nettoTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nettoTF.setEnabled(false);
        nettoTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nettoTFActionPerformed(evt);
            }
        });

        diskonValueTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        diskonValueTF.setEnabled(false);
        diskonValueTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diskonValueTFActionPerformed(evt);
            }
        });

        jLabel30.setText("Total Bayar");

        totalBayarTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalBayarTF.setEnabled(false);
        totalBayarTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalBayarTFActionPerformed(evt);
            }
        });

        jLabel31.setText(":");

        jLabel32.setText("Sisa");

        sisaBayarTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sisaBayarTF.setEnabled(false);
        sisaBayarTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sisaBayarTFActionPerformed(evt);
            }
        });

        jLabel33.setText(":");

        bayarTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Pembayaran Ke", "Tanggal Bayar", "Jumlah Bayar", "Sisa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        bayarScrollPane.setViewportView(bayarTable);

        jSimpan.setText("Simpan");
        jSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(produkScrollPane)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tunaiRB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kreditRB)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jumlahBayarCicilTf, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSimpan)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(bayarScrollPane))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sisaBayarTF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel30)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(totalBayarTF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel26)
                                        .addComponent(jLabel27)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel25)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(diskonTF, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(brutoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel28)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(diskonValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(nettoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tunaiRB)
                    .addComponent(kreditRB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(produkScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(brutoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jSimpan)
                    .addComponent(jumlahBayarCicilTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(diskonTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel25)
                            .addComponent(diskonValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel27)
                            .addComponent(nettoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30)
                            .addComponent(totalBayarTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sisaBayarTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33))
                            .addComponent(jLabel32)))
                    .addComponent(bayarScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Data"));

        jLabel12.setText("Nama");

        jLabel13.setText("Alamat");

        jLabel14.setText("Telepon");

        namaCustomerTextField.setEnabled(false);

        jLabel15.setText(":");

        jLabel16.setText(":");

        alamatCustomerTextField.setEnabled(false);

        jLabel17.setText(":");

        teleponCustomerTextField.setEnabled(false);

        jLabel18.setText("No Faktur");

        jLabel19.setText(":");

        tglFaktur.setEnabled(false);

        jLabel20.setText("Tgl Faktur");

        jLabel21.setText(":");

        jCari.setText("Cari");
        jCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tglFaktur)
                    .addComponent(noFaktur, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCari)
                .addGap(281, 281, 281)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(teleponCustomerTextField))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(alamatCustomerTextField)
                            .addComponent(namaCustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(noFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(jCari))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(namaCustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(tglFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(alamatCustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teleponCustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel14))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void brutoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brutoTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brutoTFActionPerformed

    private void nettoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nettoTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nettoTFActionPerformed

    private void diskonValueTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diskonValueTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diskonValueTFActionPerformed

    private void totalBayarTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalBayarTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalBayarTFActionPerformed

    private void sisaBayarTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sisaBayarTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sisaBayarTFActionPerformed

    private void jCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCariActionPerformed

        Long diskonPersen = null;
        Long diskonHarga = null;
        Long brutoSave = null;

        try {
            Orders vo = ordersService.findById(Integer.valueOf(noFaktur.getText().trim()));
            
            if(vo == null){
                JOptionPane.showMessageDialog(this, "No faktur tidak terdaftar", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                this.tglFaktur.setText(sdf.format(vo.getTglOder()));

                //get customer
                Customer cusVo = customerService.findById(vo.getIdCustomer());
                this.namaCustomerTextField.setText(cusVo.getNama());
                this.alamatCustomerTextField.setText(cusVo.getAlamat());
                this.teleponCustomerTextField.setText(cusVo.getTelepon());

                if (vo.getCaraBeli().equals(CommonConstant.TUNAI)) {
                    tunaiRB.setSelected(true);
                } else {
                    kreditRB.setSelected(true);
                }

                setDisplayProdukTable(vo.getOrderDetailList());
                setPembayaranTable(vo.getPembayaranList());

                this.brutoTF.setText(NumberFormat.getInstance().formatNumber(vo.getBruto()));
                this.diskonTF.setText(String.valueOf(vo.getDiskon()));

                brutoSave = Long.valueOf(vo.getBruto().toString());
                diskonPersen = Long.valueOf(diskonTF.getText());
                diskonHarga = (brutoSave * diskonPersen) / 100;
                this.diskonValueTF.setText(NumberFormat.getInstance().formatNumber(diskonHarga));
                this.nettoTF.setText(NumberFormat.getInstance().formatNumber(vo.getNetto()));

                // Hitung sisa
                // Cek pembayaran apakah lunas
                Pembayaran pembayaran = pembayaranService.getLastPembayaran(Integer.valueOf(noFaktur.getText().trim()));
                sisaBayarTF.setText(NumberFormat.getInstance().formatNumber(pembayaran.getSisa()));

                // Cek status pembayaran
                if(vo.getStatus().equals("LUNAS")){
                    jumlahBayarCicilTf.setEnabled(false);
                                jSimpan.setEnabled(false);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR = " + e.getMessage());
            JOptionPane.showMessageDialog(this, "No faktur harus angka!!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jCariActionPerformed

    private void jSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSimpanActionPerformed
        // TODO add your handling code here:
        passwordDialog.setSize(280,100);
        passwordDialog.setVisible(true);
        passwordDialog.setLocationRelativeTo(null);
        password.setText(null);
    }//GEN-LAST:event_jSimpanActionPerformed

    private void inputBayarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputBayarButtonActionPerformed
        // TODO add your handling code here:
        String passText = new String(password.getPassword());
        String md5Password = getMD5(passText);
        try{
            if(md5Password.equals(UserSession.getPassword())){
                int result = JOptionPane.showConfirmDialog(null, "Data akan tersimpan dalam database, Yakin data disimpan?","Warning",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                        Orders o = ordersService.findById(Integer.valueOf(noFaktur.getText().trim()));
                        Pembayaran pembayaranTerkahir = pembayaranService.getLastPembayaran(Integer.valueOf(noFaktur.getText().trim()));
                        Long sisa = pembayaranTerkahir.getSisa() - Long.valueOf(jumlahBayarCicilTf.getText());
                        Pembayaran bayar = new Pembayaran();
                        bayar.setBayar(Long.valueOf(jumlahBayarCicilTf.getText()));
                        bayar.setOrders(o);
                        bayar.setTglBayar(new Date());
                        bayar.setSisa(sisa);
                        pembayaranService.save(bayar, UserSession.getUsername());
                        
                        // Cek pembayaran apakah lunas
                        Pembayaran pembayaran = pembayaranService.getLastPembayaran(Integer.valueOf(noFaktur.getText().trim()));
                        
                        if(pembayaran.getSisa() <= 0){
                            o.setStatus("LUNAS");
                            jumlahBayarCicilTf.setEnabled(false);
                            jSimpan.setEnabled(false);
                            ordersService.update(o, UserSession.getUsername());
                        }
                }

                JOptionPane.showMessageDialog(this, "Pembayaran telah disimpan","Info",JOptionPane.INFORMATION_MESSAGE);
                jumlahBayarCicilTf.setText("");
                jCariActionPerformed(null);
            }else{
                JOptionPane.showMessageDialog(this, "Kesalahan Password!");
            }
            passwordDialog.setVisible(false);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
        }
    }//GEN-LAST:event_inputBayarButtonActionPerformed

    private String getMD5(String password){
        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            
            byte byteData[] = md.digest();
            
            for (int i = 0; i < byteData.length; i++) {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sb.toString();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamatCustomerTextField;
    private javax.swing.JScrollPane bayarScrollPane;
    private javax.swing.JTable bayarTable;
    private javax.swing.ButtonGroup bgPembayaran;
    private javax.swing.JTextField brutoTF;
    private javax.swing.JTextField diskonTF;
    private javax.swing.JTextField diskonValueTF;
    private javax.swing.JButton inputBayarButton;
    private javax.swing.JButton jCari;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jSimpan;
    private javax.swing.JTextField jumlahBayarCicilTf;
    private javax.swing.JRadioButton kreditRB;
    private javax.swing.JTextField namaCustomerTextField;
    private javax.swing.JTextField nettoTF;
    private javax.swing.JTextField noFaktur;
    private javax.swing.JPasswordField password;
    private javax.swing.JDialog passwordDialog;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTable produkListTable;
    private javax.swing.JScrollPane produkScrollPane;
    private javax.swing.JTextField sisaBayarTF;
    private javax.swing.JTextField teleponCustomerTextField;
    private javax.swing.JTextField tglFaktur;
    private javax.swing.JTextField totalBayarTF;
    private javax.swing.JRadioButton tunaiRB;
    // End of variables declaration//GEN-END:variables

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public static PembayaranUi getMyInstance() {
        return myInstance;
    }

    public static void setMyInstance(PembayaranUi myInstance) {
        PembayaranUi.myInstance = myInstance;
    }

    public OrdersService getOrdersService() {
        return ordersService;
    }

    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }
}
