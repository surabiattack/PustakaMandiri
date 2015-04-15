/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.ui;

import com.pustaka.common.CommonConstant;
import com.pustaka.common.NumberFormat;
import com.pustaka.common.TableColumnAdjuster;
import com.pustaka.model.Customer;
import com.pustaka.model.OrderDetail;
import com.pustaka.model.Orders;
import com.pustaka.model.Pembayaran;
import com.pustaka.model.Produk;
import com.pustaka.model.UserSession;
import com.pustaka.provider.ApplicationContextProvider;
import com.pustaka.service.CustomerService;
import com.pustaka.service.OrdersService;
import com.pustaka.service.ProdukService;
import com.pustaka.vo.ProdukVo;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author dwi
 */
public class AddOrderInternalFrame extends javax.swing.JInternalFrame implements ItemListener {

    private CustomerService customerService;
    private ProdukService produkService;
    private OrdersService ordersService;
    private Produk produkSelected;
    private Customer customerSelected;
    private List<Customer> customerList = new ArrayList<Customer>();
    private List<Produk> produkList = new ArrayList<Produk>();
    private List<ProdukVo> produkVoList = new ArrayList<ProdukVo>();
    private List<Orders> orderList = new ArrayList<Orders>();
    private List<OrderDetail> orderDetailList;
    private SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT);
    private static AddOrderInternalFrame myInstance;
    static Logger logger = Logger.getLogger(AddOrderInternalFrame.class);

    /**
     * Creates new form AddOrderInternalFrame
     */
    public AddOrderInternalFrame() {
        logger.info("Constructing Order UI");
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        customerService = (CustomerService) appContext.getBean("customerService");
        produkService = (ProdukService) appContext.getBean("produkService");
        ordersService = (OrdersService) appContext.getBean("ordersService");
        initComponents();
        
        // set produk table
        setDisplayProdukTable();
        onChangeDiskon();
        onChangePembayaranKredit();

        // add item listener
        kreditRB.addItemListener(this);

        // remove from ui
        inputJumlah.setVisible(false);
        hapusJumlah.setVisible(false);
        
        // get id order
        int nextPrimaryValue = ordersService.getNextPrimaryValue("pustaka", "orders");
        
        namaCustomerTextField1.setText(""+nextPrimaryValue);
    }

    public static AddOrderInternalFrame getInstance() {
        if (myInstance == null) {
            myInstance = new AddOrderInternalFrame();
        }

        return myInstance;
    }

    private void onChangePembayaranKredit() {
        totalBayarTF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                countSisa();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                countSisa();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                countSisa();
            }
        });
    }

    private void countSisa() {
        String nettoValue = StringUtils.replace(nettoTF.getText(), ".", "");
        String pembayaranValue;
        if (totalBayarTF.getText().contains(".")) {
            pembayaranValue = StringUtils.replace(totalBayarTF.getText(), ".", "");
        } else {
            pembayaranValue = totalBayarTF.getText();
        }

        Long sisa = Long.valueOf(nettoValue) - (pembayaranValue.equals("") ? 0L : Long.valueOf(pembayaranValue));
        sisaBayarTF.setText(NumberFormat.getInstance().formatNumber(sisa));
    }

    private void onChangeDiskon() {
        diskonTF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeNetto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeNetto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeNetto();
            }
        });
    }

    private void changeNetto() {
        Long diskon = 0L;
        Long nettoDisplay;
        try {
            if (diskonTF.getText().trim().equals("")) {
//                diskonValueTF.setText(diskon.toString());
            } else if (Integer.valueOf(diskonTF.getText().trim()) > 100) {
                diskonTF.setText("");
                JOptionPane.showMessageDialog(this, "Diskon tidak lebih dari 100 ", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                diskon = Long.valueOf(diskonTF.getText());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Diskon harus angka! ", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String brutoValue = StringUtils.replace(brutoTF.getText(), ".", "");
            Long totalDiskon = (Long.valueOf(brutoValue) * diskon) / 100;
            diskonValueTF.setText(NumberFormat.getInstance().formatNumber(totalDiskon));
            nettoDisplay = Long.valueOf(brutoValue) - totalDiskon;
            nettoTF.setText(NumberFormat.getInstance().formatNumber(nettoDisplay));
            totalBayarTF.setText(nettoTF.getText());
        } catch (NumberFormatException e) {
        }
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
        customerDialog = new javax.swing.JDialog();
        customerScrollPane = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        searchCustomerTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        produkScrollPane = new javax.swing.JScrollPane();
        produkListTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return colIndex == 4; //Disallow the editing of any cell
            }
        };
        hapusJumlah = new javax.swing.JButton();
        inputJumlah = new javax.swing.JButton();
        tunaiRB = new javax.swing.JRadioButton();
        kreditRB = new javax.swing.JRadioButton();
        saveOrderButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
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
        clearButton = new javax.swing.JButton();
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
        namaCustomerTextField1 = new javax.swing.JTextField();
        namaCustomerTextField2 = new javax.swing.JTextField(sdf.format(new Date()));
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        customerDialog.setTitle("Lov Customer");

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerTableMouseClicked(evt);
            }
        });
        customerScrollPane.setViewportView(customerTable);

        javax.swing.GroupLayout customerDialogLayout = new javax.swing.GroupLayout(customerDialog.getContentPane());
        customerDialog.getContentPane().setLayout(customerDialogLayout);
        customerDialogLayout.setHorizontalGroup(
            customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(customerDialogLayout.createSequentialGroup()
                        .addComponent(searchCustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        customerDialogLayout.setVerticalGroup(
            customerDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerDialogLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(searchCustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(customerScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setTitle("Add Order");
        setMinimumSize(new java.awt.Dimension(90, 50));
        setPreferredSize(new java.awt.Dimension(930, 700));

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

        hapusJumlah.setText("Delete Jumlah");
        hapusJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusJumlahActionPerformed(evt);
            }
        });

        inputJumlah.setText("Input Jumlah");
        inputJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputJumlahActionPerformed(evt);
            }
        });

        bgPembayaran.add(tunaiRB);
        tunaiRB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tunaiRB.setSelected(true);
        tunaiRB.setText("Tunai");

        bgPembayaran.add(kreditRB);
        kreditRB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        kreditRB.setText("Kredit");

        try{
            Image img = ImageIO.read(getClass().getResource("/resources/print_icon.png"));
            saveOrderButton.setIcon(new ImageIcon(img));
        }catch (Exception ex) {
            logger.error("ERROR = " + ex.getMessage());
        }
        saveOrderButton.setText("Print Order");
        saveOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveOrderButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel Order");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

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

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(tunaiRB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kreditRB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(produkScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(inputJumlah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapusJumlah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tunaiRB)
                        .addComponent(kreditRB))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(saveOrderButton)
                        .addComponent(clearButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(produkScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hapusJumlah)
                        .addComponent(inputJumlah))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(brutoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                            .addComponent(totalBayarTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sisaBayarTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33))
                    .addComponent(jLabel32))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Data"));

        jLabel12.setText("Nama");

        jLabel13.setText("Alamat");

        jLabel14.setText("Telepon");

        jLabel15.setText(":");

        jLabel16.setText(":");

        jLabel17.setText(":");

        jLabel18.setText("No Faktur");

        jLabel19.setText(":");

        namaCustomerTextField1.setEnabled(false);

        namaCustomerTextField2.setEnabled(false);

        jLabel20.setText("Tgl Faktur");

        jLabel21.setText(":");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(teleponCustomerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
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
                            .addComponent(namaCustomerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))))
                .addGap(360, 360, 360)
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
                    .addComponent(namaCustomerTextField2)
                    .addComponent(namaCustomerTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(namaCustomerTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(namaCustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(namaCustomerTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearCustomerData() {

        namaCustomerTextField.setText(null);
        alamatCustomerTextField.setText(null);
        teleponCustomerTextField.setText(null);
    }

    private void clearAllTable() {
        produkListTable.setModel(new DefaultTableModel());
    }

    private void setDisplayCustomerTable() {
        customerList = customerService.getCustomerList();
        Object data[][] = new Object[customerList.size()][10];
        int x = 0;
        for (Customer customer : customerList) {
            data[x][0] = customer.getCustomerId();
            data[x][1] = customer.getNama();
            data[x][2] = customer.getAlamat();
            data[x][3] = customer.getTelepon();
            ++x;
        }

        String[] judul = {"Id", "Nama", "Alamat", "Telepon"};
        customerTable.setModel(new DefaultTableModel(data, judul));
        customerScrollPane.setViewportView(customerTable);
        customerTable.setEnabled(true);
    }

    private void onChangeCustomerListener() {
        customerList = customerService.findBySearch(namaCustomerTextField.getText().trim(), searchCustomerTextField.getText());
        Object data[][] = new Object[customerList.size()][10];
        int x = 0;
        for (Customer customer : customerList) {
            data[x][0] = customer.getCustomerId();
            data[x][1] = customer.getNama();
            data[x][2] = customer.getAlamat();
            data[x][3] = customer.getTelepon();
            ++x;
        }

        String[] judul = {"Id", "Nama", "Alamat", "Telepon"};
        customerTable.setModel(new DefaultTableModel(data, judul));
        customerScrollPane.setViewportView(customerTable);
        customerTable.setEnabled(true);

    }

    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = customerTable.getSelectedRow();
            customerSelected = customerList.get(row);
            customerDialog.dispose();
        }
    }//GEN-LAST:event_customerTableMouseClicked

    private void sisaBayarTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sisaBayarTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sisaBayarTFActionPerformed

    private void totalBayarTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalBayarTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalBayarTFActionPerformed

    private void diskonValueTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diskonValueTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diskonValueTFActionPerformed

    private void nettoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nettoTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nettoTFActionPerformed

    private void brutoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brutoTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brutoTFActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveOrderButtonActionPerformed
        // TODO add your handling code here:

        int result = JOptionPane.showConfirmDialog(null, "Yakin data disimpan???", "Warning", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Orders newOrder = new Orders();
            List<Customer> newCustomerList = null;
            Customer newCustomer = null;
            Long brutoSave = 0l;
            Long nettoSave = 0l;
            List<Pembayaran> bayarList = new ArrayList<Pembayaran>();

            if (produkListTable.getRowCount() > 0) {
                orderDetailList = new ArrayList<OrderDetail>();
                for (int i = 0; i < produkListTable.getRowCount(); i++) {
                    if (!produkListTable.getValueAt(i, 4).toString().equals("")) {
                        //                        Long total = 0l;
                        OrderDetail ordersDetail = new OrderDetail();
                        ordersDetail.setOrders(newOrder);
                        ordersDetail.setKodeProduk(produkListTable.getValueAt(i, 0).toString());
                        ordersDetail.setJumlah(Integer.valueOf(produkListTable.getValueAt(i, 4).toString()));
                        ordersDetail.setCreateBy(UserSession.getUsername());
                        ordersDetail.setCreateDate(new Date());
                        //                        total = total + vo.getSubTotal();
                        ordersDetail.setSubtotal(Long.valueOf(StringUtils.replace(produkListTable.getValueAt(i, 5).toString(), ".", "")));
//                        brutoSave = brutoSave + vo.getSubTotal();
                        orderDetailList.add(ordersDetail);
                    }
                }

                if (orderDetailList.size() > 0) {
                    if (!namaCustomerTextField.getText().equals("")) {
                        newCustomerList = customerService.findByName(namaCustomerTextField.getText().trim());
                        if (newCustomerList != null && newCustomerList.size() >= 1) {
                            int pilih = JOptionPane.showConfirmDialog(null, "Customer sudah ada, pilih customer "
                                    + "[yes] atau buat baru [no]", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);

                            if (pilih == JOptionPane.YES_OPTION) {
                                //                        Object[] tes = newCustomerList.toArray();
                                Object[] tes = new Object[newCustomerList.size()];
                                int i = 0;
                                for (Customer co : newCustomerList) {
                                    tes[i] = co.getCustomerId() + "-" + co.getNama() + "-" + co.getAlamat() + "";
                                    i++;
                                }
                                String id = (String) JOptionPane.showInputDialog(this, "Pilih Customer", "Pilih Customer", JOptionPane.QUESTION_MESSAGE,
                                        null, tes, tes[0]);
                                //                        System.out.println(id);
                                String[] split = id.split("-");
                                newCustomer = customerService.findById(Integer.valueOf(split[0]));
                            } else if (pilih == JOptionPane.NO_OPTION) {
                                newCustomer = new Customer(namaCustomerTextField.getText().trim(),
                                        alamatCustomerTextField.getText(), teleponCustomerTextField.getText().trim());
                                try {
                                    customerService.save(newCustomer, UserSession.getUsername());
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(this, "ERROR = " + e.getMessage() + "", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                return;
                            }
                        } else {
                            newCustomer = new Customer(namaCustomerTextField.getText().trim(),
                                    alamatCustomerTextField.getText(), teleponCustomerTextField.getText().trim());
                            try {
                                customerService.save(newCustomer, UserSession.getUsername());
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "ERROR = " + e.getMessage() + "", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Nama customer tidak boleh kosong ", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (tunaiRB.isSelected()) {                           // Pembayaran tunai
                        newOrder.setCaraBeli(Orders.TUNAI);
                        newOrder.setStatus(Orders.LUNAS);
                        Pembayaran bayar = new Pembayaran();
                        bayar.setOrders(newOrder);
                        bayar.setTglBayar(new Date());
                        bayar.setBayar(Long.valueOf(StringUtils.replace(nettoTF.getText(), ".", "")));
                        bayar.setSisa(0L);
                        bayar.setCreateBy(UserSession.getUsername());
                        bayar.setCreateDate(new Date());
                        bayarList.add(bayar);
                        newOrder.setPembayaranList(bayarList);
                    } else if (kreditRB.isSelected()) {                    // Pembayaran Cicil
                        newOrder.setCaraBeli(Orders.KREDIT);
                        newOrder.setStatus(Orders.BELUM_LUNAS);
                        Pembayaran bayar = new Pembayaran();
                        bayar.setOrders(newOrder);
                        bayar.setBayar(totalBayarTF.getText().equals("") ? 0 : Long.valueOf(StringUtils.replace(totalBayarTF.getText(), ".", "")));
                        bayar.setSisa(Long.valueOf(StringUtils.replace(sisaBayarTF.getText(), ".", "")));
                        bayar.setCreateBy(UserSession.getUsername());
                        bayar.setTglBayar(new Date());
                        bayar.setCreateDate(new Date());
                        bayarList.add(bayar);
                        newOrder.setPembayaranList(bayarList);
                    } else {                                              // Tidak memilih pembayaran
                        JOptionPane.showMessageDialog(this, "Cara bayar tidak boleh kosong ", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!diskonTF.getText().equals("")) {
                        newOrder.setDiskon(Integer.valueOf(diskonTF.getText()));
                    }
                    newOrder.setIdCustomer(newCustomer.getCustomerId());
                    newOrder.setBruto(Long.valueOf(StringUtils.replace(brutoTF.getText(), ".", "")));
                    newOrder.setNetto(Long.valueOf(StringUtils.replace(nettoTF.getText(), ".", "")));
                    newOrder.setTglOder(new Date());
                    newOrder.setOrderDetailList(orderDetailList);
                    try {
                        ordersService.save(newOrder, UserSession.getUsername());
                        
                        // substract stok from produk
                        for (OrderDetail odTemp : orderDetailList) {
                            Produk pTemp = produkService.getById(odTemp.getKodeProduk());
                            pTemp.setStok(pTemp.getStok() - odTemp.getJumlah());
                            produkService.update(pTemp, UserSession.getUsername());
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "ERROR = " + e.getMessage() + "", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    Map<String, Object> param = new HashMap<String, Object>();
                    Locale locale = new Locale("id", "ID");

                    try {
                        int idOrders = newOrder.getIdOrders();
                        param.put("ID_ORDERS", idOrders);
                        param.put("TOTAL", totalBayarTF.getText().equals("") ? 0 :Double.valueOf(StringUtils.replace(totalBayarTF.getText(), ".", "")));
                        param.put(JRParameter.REPORT_LOCALE, locale);
//                    JasperReport jr = JasperCompileManager.compileReport("D:\\Report\\ReportOrders.jrxml");
                        InputStream jasperStream = getClass().getResourceAsStream("/Report/Order.jasper");
                        JasperReport jr = (JasperReport) JRLoader.loadObject(jasperStream);

                        JasperPrint print = JasperFillManager.fillReport(jr, param, ordersService.getConnection());
//                        JasperViewer.viewReport(print, false); // view print report
                        JasperPrintManager.printReport(print, true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error = " + e.getMessage() + "", "Error", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(this, "Saved successfuly ", "Success", JOptionPane.INFORMATION_MESSAGE);

                    //RESET FORM
                    setDisplayProdukTable();
                    namaCustomerTextField.setText("");
                    alamatCustomerTextField.setText("");
                    teleponCustomerTextField.setText("");
                    brutoTF.setText("0");
                    diskonTF.setText("");
                    nettoTF.setText("0");
                    diskonValueTF.setText("0");
                    totalBayarTF.setText("0");
                    sisaBayarTF.setText("0");
                } else {
                    JOptionPane.showMessageDialog(this, "Pilih produk minimal 1 ", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }//GEN-LAST:event_saveOrderButtonActionPerformed

    private void inputJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputJumlahActionPerformed
        // TODO add your handling code here:
        try {
            if (produkListTable.getSelectedRow() != -1) {

                Long diskonPersen = null;
                Long diskonHarga = null;
                Long brutoSave = 0L;
                Long nettoSave = 0L;
                int jumlahTemp = Integer.valueOf(JOptionPane.showInputDialog("Input jumlah"));

                ProdukVo editedEntity = produkVoList.get(produkListTable.getSelectedRow());
                //                produkVoList.remove(editedEntity);

                editedEntity.setJumlah(jumlahTemp);
                Long subTotalTemp = jumlahTemp * editedEntity.getHarga();
                editedEntity.setSubTotal(subTotalTemp); // recalculate
                produkVoList.set(produkListTable.getSelectedRow(), editedEntity);

                produkListTable.setValueAt(jumlahTemp, produkListTable.getSelectedRow(), 3);
                produkListTable.setValueAt(NumberFormat.getInstance().formatNumber(subTotalTemp),
                        produkListTable.getSelectedRow(), 4);

                for (ProdukVo vo : produkVoList) {
                    if (vo.getJumlah() != 0) {
                        brutoSave = brutoSave + vo.getSubTotal();
                    }
                }

                brutoTF.setText(brutoSave.toString());

                if (!diskonTF.getText().equals("")) {
                    diskonPersen = Long.valueOf(diskonTF.getText());
                    diskonHarga = (brutoSave * diskonPersen) / 100;
                    nettoSave = brutoSave - diskonHarga;
                    nettoTF.setText(nettoSave.toString());
                } else {
                    nettoSave = brutoSave;
                    nettoTF.setText(brutoTF.getText());
                }

            } else {
                JOptionPane.showMessageDialog(this, "Pilih produk! ", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException nfe) {
            //            JOptionPane.showMessageDialog(this, "Jumlah harus angka! ", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            //            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_inputJumlahActionPerformed

    private void hapusJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusJumlahActionPerformed
        // TODO add your handling code here:
        try {
            if (produkListTable.getSelectedRow() != -1) {
                Long diskonPersen = null;
                Long diskonHarga = null;
                Long brutoSave = 0L;
                Long nettoSave = 0L;
                ProdukVo editedEntity = produkVoList.get(produkListTable.getSelectedRow());
                int jumlah = editedEntity.getJumlah();
                Long subTotalTemp = jumlah * editedEntity.getHarga();
                editedEntity.setJumlah(0);
                editedEntity.setSubTotal(0L); // recalculate
                //                produkVoList.remove(editedEntity);

                produkListTable.setValueAt("", produkListTable.getSelectedRow(), 3);
                produkListTable.setValueAt("",
                        produkListTable.getSelectedRow(), 4);

                for (ProdukVo vo : produkVoList) {
                    if (vo.getJumlah() != 0) {
                        brutoSave = brutoSave + vo.getSubTotal();
                    }
                }

                brutoTF.setText(brutoSave.toString());

                if (!diskonTF.getText().equals("")) {
                    diskonPersen = Long.valueOf(diskonTF.getText());
                    diskonHarga = (brutoSave * diskonPersen) / 100;
                    nettoSave = brutoSave - diskonHarga;
                    nettoTF.setText(nettoSave.toString());
                } else {
                    nettoSave = brutoSave;
                    nettoTF.setText(brutoTF.getText());
                }

                //                if((brutoSave - subTotalTemp) < 0){
                //
                //                }else{
                //                    brutoSave = brutoSave - subTotalTemp;
                //                    brutoTF.setText(brutoSave.toString());
                //                    if(!diskonTF.getText().equals("")){
                //                        diskonPersen = Long.valueOf(diskonTF.getText());
                //                        diskonHarga = (brutoSave * diskonPersen)/100;
                //                        nettoSave = brutoSave - diskonHarga;
                //                        nettoTF.setText(nettoSave.toString());
                //                    }else{
                //                        nettoSave = brutoSave;
                //                        nettoTF.setText(brutoTF.getText());
                //                    }
                //                }

            } else {
                JOptionPane.showMessageDialog(this, "Pilih produk yang di hapus! ", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Jumlah harus angka! ", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_hapusJumlahActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        setDisplayProdukTable();
        namaCustomerTextField.setText("");
        alamatCustomerTextField.setText("");
        teleponCustomerTextField.setText("");
        
        brutoTF.setText("");
        diskonTF.setText("");
        diskonValueTF.setText("");
        nettoTF.setText("");
        totalBayarTF.setText("");
        sisaBayarTF.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void setDisplayProdukList() {
//        tempList = customerService.getProdukList();
        Object data[][] = new Object[produkVoList.size()][10];
        int x = 0;
        int no = 1;
        for (ProdukVo vo : produkVoList) {
            data[x][0] = no++;
            data[x][1] = vo.getNamaProduk();
            data[x][2] = NumberFormat.getInstance().formatNumber(vo.getHarga());
            data[x][3] = vo.getJumlah();
            data[x][4] = NumberFormat.getInstance().formatNumber(vo.getSubTotal());
            ++x;
        }

        String[] judul = {"No", "Nama", "Harga", "Jumlah", "Sub Total"};
        produkListTable.setModel(new DefaultTableModel(data, judul));
        produkScrollPane.setViewportView(produkListTable);
        produkListTable.setEnabled(true);
    }

    // Display product list in faktur
    private void setDisplayProdukTable() {
        produkVoList = produkService.getAllProductForVo();
        Object data[][] = new Object[produkVoList.size()][10];
        int x = 0;
        for (ProdukVo produk : produkVoList) {
            data[x][0] = produk.getKodeProduk();
            data[x][1] = produk.getNamaProduk();
            data[x][2] = NumberFormat.getInstance().formatNumber(produk.getHarga());
            data[x][3] = produk.getStok();
            data[x][4] = "";
            data[x][5] = "";
            ++x;
        }

        String[] judul = {"Kode Produk", "Nama Produk", "Harga", "Stok", "Jumlah", "Sub Total"};
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
        
        produkListTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                Long total = 0L;
                String a = "";
                Long diskonPersen = null;
                Long diskonHarga = null;
                Long nettoSave = 0L;
                
                try {
                    if (e.getType() == TableModelEvent.UPDATE) {

                        int row = e.getFirstRow();
                        int column = e.getColumn();

                        if (column == 4) {
                            ProdukVo temp = produkVoList.get(row);
                            int jumlah = !produkListTable.getValueAt(row, column).toString().equals("")
                                    ? Integer.valueOf(produkListTable.getValueAt(row, column).toString()) : 0;
                            if (jumlah > temp.getStok()) {
                                JOptionPane.showMessageDialog(null, "Jumlah melebihi stok! ", "Error", JOptionPane.ERROR_MESSAGE);
                                produkListTable.setValueAt("", row, 4);
                            } else {
                                Long subtotal = jumlah * temp.getHarga();
                                produkListTable.setValueAt(subtotal != 0 ? NumberFormat.getInstance().formatNumber(subtotal) : "", row, 5);
                            }
                        }
                    }

                    // Sum subtotal
                    for (int i = 0; i < produkListTable.getRowCount(); i++) {
                        String subtotalTemp = produkListTable.getValueAt(i, 5).toString();
                        if (!subtotalTemp.equals(StringUtils.EMPTY)) {
                            subtotalTemp = StringUtils.replace(subtotalTemp, ".", a);
                            total = total + Long.valueOf(subtotalTemp);
                        }
                    }

                    brutoTF.setText(NumberFormat.getInstance().formatNumber(total));



                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Jumlah harus angka! ", "Error", JOptionPane.ERROR_MESSAGE);
                    nfe.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                // set netto value
                try {
                    if (!diskonTF.getText().equals("")) {
                        diskonPersen = Long.valueOf(diskonTF.getText());
                        diskonHarga = (total * diskonPersen) / 100;
                        diskonValueTF.setText(NumberFormat.getInstance().formatNumber(diskonHarga));
                        nettoSave = total - diskonHarga;
                        nettoTF.setText(NumberFormat.getInstance().formatNumber(nettoSave));
                    } else {
                        nettoSave = total;
                        nettoTF.setText(brutoTF.getText());
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }

                if (tunaiRB.isSelected()) {
                    totalBayarTF.setText(nettoTF.getText());
                } else {
                    totalBayarTF.setText("");
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamatCustomerTextField;
    private javax.swing.ButtonGroup bgPembayaran;
    private javax.swing.JTextField brutoTF;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JDialog customerDialog;
    private javax.swing.JScrollPane customerScrollPane;
    private javax.swing.JTable customerTable;
    private javax.swing.JTextField diskonTF;
    private javax.swing.JTextField diskonValueTF;
    private javax.swing.JButton hapusJumlah;
    private javax.swing.JButton inputJumlah;
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
    private javax.swing.JRadioButton kreditRB;
    private javax.swing.JTextField namaCustomerTextField;
    private javax.swing.JTextField namaCustomerTextField1;
    private javax.swing.JTextField namaCustomerTextField2;
    private javax.swing.JTextField nettoTF;
    private javax.swing.JTable produkListTable;
    private javax.swing.JScrollPane produkScrollPane;
    private javax.swing.JButton saveOrderButton;
    private javax.swing.JTextField searchCustomerTextField;
    private javax.swing.JTextField sisaBayarTF;
    private javax.swing.JTextField teleponCustomerTextField;
    private javax.swing.JTextField totalBayarTF;
    private javax.swing.JRadioButton tunaiRB;
    // End of variables declaration//GEN-END:variables

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public ProdukService getProdukService() {
        return produkService;
    }

    public void setProdukService(ProdukService produkService) {
        this.produkService = produkService;
    }

    public List<Produk> getProdukList() {
        return produkList;
    }

    public void setProdukList(List<Produk> produkList) {
        this.produkList = produkList;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    public Produk getProdukSelected() {
        return produkSelected;
    }

    public void setProdukSelected(Produk produkSelected) {
        this.produkSelected = produkSelected;
    }

    public List<ProdukVo> getProdukVoList() {
        return produkVoList;
    }

    public void setProdukVoList(List<ProdukVo> produkVoList) {
        this.produkVoList = produkVoList;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public OrdersService getOrdersService() {
        return ordersService;
    }

    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    public Customer getCustomerSelected() {
        return customerSelected;
    }

    public void setCustomerSelected(Customer customerSelected) {
        this.customerSelected = customerSelected;
    }

    public static AddOrderInternalFrame getMyInstance() {
        return myInstance;
    }

    public static void setMyInstance(AddOrderInternalFrame myInstance) {
        AddOrderInternalFrame.myInstance = myInstance;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            totalBayarTF.setEnabled(true);
        } else {
            totalBayarTF.setEnabled(false);
            totalBayarTF.setText(nettoTF.getText());
        }
    }
}
