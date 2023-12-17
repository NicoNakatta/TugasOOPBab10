/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasTabel;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class GUI_DataSellV1 extends javax.swing.JFrame {

    /**
     * Creates new form GUI_QualitySell
     */
    
    String pembeli1, dealer1, organ1, harga1, bayar1, kemb1;
    public Connection conn;
    
    public void koneksi() throws SQLException {
        try {
            conn=null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost/oop_jualorgan?user=root&password=");
        }catch (ClassNotFoundException ex){
            Logger.getLogger(GUI_DataSellV1.class.getName()).log(Level.SEVERE,null, ex);
        }catch (SQLException e) {
                Logger.getLogger(GUI_DataSellV1.class.getName()).log(Level.SEVERE,null, e);
        }catch (Exception es) {
            Logger.getLogger(GUI_DataSellV1.class.getName()).log(Level.SEVERE,null, es);
        }
    }
    public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Pembeli");
        tabelhead.addColumn("Penjual");
        tabelhead.addColumn("Organ");
        tabelhead.addColumn("Harga");
        tabelhead.addColumn("Bayar");
        tabelhead.addColumn("kembalian");
        try {
            koneksi();
            String sql = "SELECT * FROM tb_datasellv1";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString(1), res.getString(2), res.getString(3), res.getString(4),res.getString(5),res.getString(6),});
            }
            tabel_jual_organ.setModel(tabelhead);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BELUM TERKONEKSI");
        }
    }
    public void refresh() {
        new GUI_DataSellV1().setVisible(true);
        this.setVisible(false);
    }
    public  void insert() {
        Transaksi t = new Transaksi();
        String pembeli = cliente.getText();
        String dealer = (String) boxdeal.getSelectedItem();
        String organ = (String) boxorg.getSelectedItem();
        String harga = txtharga.getText();
        String bayar = txtuang.getText();
        int kemb = t.kembalian(Integer.parseInt(txtuang.getText()), Integer.parseInt(txtharga.getText()));
        try {
            koneksi();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO tb_datasellv1(pembeli, penjual, organ, harga, bayar, kembalian)"
                    + "VALUES('" + pembeli + "','" + dealer + "','" + organ + "','" + harga + "','" + bayar + "','" +kemb +"')");
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukan Data Pembelian!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        refresh();
    }
    public void update() {
        Transaksi t = new Transaksi();
        String pembeli = cliente.getText();
        String dealer = (String) boxdeal.getSelectedItem();
        String organ = (String) boxorg.getSelectedItem();
        String harga = txtharga.getText();
        String bayar = txtuang.getText();
        String pemlama = pembeli1; 
        int kemb = t.kembalian(Integer.parseInt(txtuang.getText()), Integer.parseInt(txtharga.getText()));
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE tb_datasellv1 SET pembeli='" + pembeli + "'," + "penjual='" + dealer + "',"
                    + "organ='" + organ + "'" + ",harga='" + harga + "',bayar='" + bayar + "',kembalian='"
                    + kemb + "' WHERE pembeli = '" + pemlama + "'");
            statement.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Update Data Pembelian Berhasil!");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        refresh();
    }
    public void itempilih() {
        cliente.setText(pembeli1);
        txtJmljantung.setText("0");
        txtJmlginjal.setText("0");
        txtharga.setText(harga1);
        txtuang.setText(bayar1);
        if (dealer1.equals("Andrew")){
            boxdeal.setSelectedIndex(0);
        }
        else{
            boxdeal.setSelectedIndex(1);
        }
        if (organ1.equals("Jantung")){
            boxorg.setSelectedIndex(0);
        }
        else if (organ1.equals("Ginjal")){
            boxorg.setSelectedIndex(1);
        }
        else{
            boxorg.setSelectedIndex(2);
        }
    }
    public void delete() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            try {
                String sql = "DELETE FROM tb_datasellv1 WHERE pembeli='" + cliente.getText() + "'";
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di hapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data gagal di hapus");
            }
        }
        refresh();
    }
    public void cari() {
        try {
            try (Statement statement = conn.createStatement()) {
                String sql = "SELECT * FROM tb_datasellv1 WHERE `pembeli`  LIKE '%" + txtcari.getText() + "%'";
                ResultSet rs = statement.executeQuery(sql); //menampilkan data dari sql query
                if (rs.next()) // .next() = scanner method
                {
                    cliente.setText(rs.getString(1));
                    txtharga.setText(rs.getString(4));
                    txtuang.setText(rs.getString(5));
                    String deal = rs.getString(2);
                    String org = rs.getString(3);
                    if (deal.equals("Andrew")) {
                        boxdeal.setSelectedIndex(0);
                    } else {
                        boxdeal.setSelectedIndex(1);
                    }
                    if (org.equals("Jantung")) {
                        boxorg.setSelectedIndex(0);
                    } else if (org.equals("Ginjal")) {
                        boxorg.setSelectedIndex(1);
                    } else {
                        boxorg.setSelectedIndex(2);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Data yang Anda cari tidak ada");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error." + ex);
        }
    }
    public void batal(){
        cliente.setText("Anon");
        txtJmljantung.setText("0");
        txtJmlginjal.setText("0");
        txtharga.setText("");
        txtuang.setText("");
        boxdeal.setSelectedIndex(0);
        boxorg.setSelectedIndex(0);
    }








    
    
    public GUI_DataSellV1() {
        initComponents();
        tampil();
        
        txtharga.setEnabled(false);
        txtJmlginjal.setEnabled(false);
        getContentPane().setBackground(Color.getHSBColor(0f, 26.5f, 98f));
        Transaksi trn = new Transaksi();
        cliente.setText(trn.Client);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        boxdeal = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        boxorg = new javax.swing.JComboBox<>();
        ckharga = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtharga = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtuang = new javax.swing.JTextField();
        bayar = new javax.swing.JButton();
        cliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_jual_organ = new javax.swing.JTable();
        txtJmljantung = new javax.swing.JTextField();
        txtJmlginjal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnHapus = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("JUAL ORGAN DALAM");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("MASUKKAN NAMA :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("PILIH DEALER      :");

        boxdeal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Andrew", "Karl" }));
        boxdeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxdealActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("PILIH ORGAN       :");

        boxorg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jantung", "Ginjal", "Jantung dan Ginjal" }));
        boxorg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxorgItemStateChanged(evt);
            }
        });
        boxorg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxorgActionPerformed(evt);
            }
        });

        ckharga.setText("Cek Harga");
        ckharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckhargaActionPerformed(evt);
            }
        });

        jLabel9.setText("Rp.");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Masukkan Uang   :");

        bayar.setText("Bayar dan Simpan");
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });

        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });

        tabel_jual_organ.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Pembeli", "Penjual", "Organ", "Harga", "Bayar", "Kembalian"
            }
        ));
        tabel_jual_organ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_jual_organMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_jual_organ);

        txtJmljantung.setText("0");

        txtJmlginjal.setText("0");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Jumlah Jantung   :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Jumlah Ginjal       :");

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnTutup.setText("Tutup");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(boxdeal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(cliente))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtuang, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckharga)
                                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(boxorg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtJmljantung)
                                    .addComponent(txtJmlginjal, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bayar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnHapus)))
                                .addGap(12, 12, 12)
                                .addComponent(btnBatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnTutup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(boxdeal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(boxorg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJmljantung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJmlginjal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckharga)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtuang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus)
                            .addComponent(btnTutup)
                            .addComponent(bayar)
                            .addComponent(btnBatal))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxdealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxdealActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxdealActionPerformed

    private void ckhargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckhargaActionPerformed
        // TODO add your handling code here:
        if (boxorg.getSelectedIndex()==0){
            int hasil;
            Transaksi jantung = new Transaksi();
            hasil = jantung.jualOrgan(Integer.parseInt(txtJmljantung.getText()));
            txtharga.setText(Integer.toString(hasil));
        }
        else if (boxorg.getSelectedIndex()==1){
            int hasil;
            TransaksiGinjal ginjal = new TransaksiGinjal();
            hasil = ginjal.jualOrgan(Integer.parseInt(txtJmlginjal.getText()));
            txtharga.setText(Integer.toString(hasil));
        }
        else{
            int hasil;
            TransaksiGinjal jajal = new TransaksiGinjal();
            hasil = jajal.jualOrgan(Integer.parseInt(txtJmljantung.getText()), Integer.parseInt(txtJmlginjal.getText()));
            txtharga.setText(Integer.toString(hasil));
        }
    }//GEN-LAST:event_ckhargaActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_bayarActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void boxorgItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxorgItemStateChanged
        // TODO add your handling code here:
        if (boxorg.getSelectedIndex()==0){
            txtJmljantung.setEnabled(true);
            txtJmlginjal.setEnabled(false);
        }
        else if (boxorg.getSelectedIndex()==1){
            txtJmljantung.setEnabled(false);
            txtJmlginjal.setEnabled(true);
        }
        else{
            txtJmljantung.setEnabled(true);
            txtJmlginjal.setEnabled(true);
        }
    }//GEN-LAST:event_boxorgItemStateChanged

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnTutupActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        batal();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void boxorgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxorgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxorgActionPerformed

    private void tabel_jual_organMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_jual_organMouseClicked
        // TODO add your handling code here:
        int tabel = tabel_jual_organ.getSelectedRow();
        pembeli1 = tabel_jual_organ.getValueAt(tabel, 0).toString();
        dealer1 = tabel_jual_organ.getValueAt(tabel, 1).toString();
        organ1 = tabel_jual_organ.getValueAt(tabel, 2).toString();
        harga1 = tabel_jual_organ.getValueAt(tabel, 3).toString();
        bayar1 = tabel_jual_organ.getValueAt(tabel, 4).toString();
        kemb1 = tabel_jual_organ.getValueAt(tabel, 5).toString();
        itempilih();
    }//GEN-LAST:event_tabel_jual_organMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_DataSellV1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_DataSellV1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_DataSellV1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_DataSellV1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_DataSellV1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bayar;
    private javax.swing.JComboBox<String> boxdeal;
    private javax.swing.JComboBox<String> boxorg;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTutup;
    private javax.swing.JButton ckharga;
    private javax.swing.JTextField cliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_jual_organ;
    private javax.swing.JTextField txtJmlginjal;
    private javax.swing.JTextField txtJmljantung;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtuang;
    // End of variables declaration//GEN-END:variables
}
