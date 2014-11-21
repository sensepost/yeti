package com.sensepost.yeti.gui;

import java.util.ArrayList;
import java.util.HashSet;

import com.sensepost.yeti.common.JIPCalc;
import com.sensepost.yeti.persistence.DataStore;

/**
 *
 * @author willemmouton
 */
public class CertInit extends BaseDlg {

    private static final long serialVersionUID = 1L;

    private String buffer = "";

    public CertInit() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtIPRanges = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxImportFrom = new javax.swing.JComboBox();
        btnLoadFromSource = new javax.swing.JButton();
        cbxMakeRanges = new javax.swing.JCheckBox();
        cbxRanges = new javax.swing.JComboBox();
        btnCancel = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Input params..."));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtIPRanges.setColumns(20);
        txtIPRanges.setRows(5);
        txtIPRanges.setName("txtIPRanges"); // NOI18N
        jScrollPane1.setViewportView(txtIPRanges);

        jLabel1.setText("IP ranges"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel3.setText("Sources"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        cbxImportFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Initial IP Addresses", "Discovered IP Addresses" }));
        cbxImportFrom.setName("cbxImportFrom"); // NOI18N

        btnLoadFromSource.setText("Load"); // NOI18N
        btnLoadFromSource.setName("btnLoadFromSource"); // NOI18N
        btnLoadFromSource.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadFromSourceMouseClicked(evt);
            }
        });

        cbxMakeRanges.setText("Make ranges"); // NOI18N
        cbxMakeRanges.setName("cbxMakeRanges"); // NOI18N
        cbxMakeRanges.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMakeRangesItemStateChanged(evt);
            }
        });

        cbxRanges.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "/24", "/16" }));
        cbxRanges.setEnabled(false);
        cbxRanges.setName("cbxRanges"); // NOI18N
        cbxRanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxRangesActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(cbxMakeRanges)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbxRanges, 0, 151, Short.MAX_VALUE)
                .add(101, 101, 101))
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cbxImportFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnLoadFromSource))
                    .add(jLabel1))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbxImportFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(btnLoadFromSource))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbxMakeRanges)
                    .add(cbxRanges, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
        );

        btnCancel.setText("Cancel"); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        btnStart.setText("Start"); // NOI18N
        btnStart.setName("btnStart"); // NOI18N
        btnStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStartMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnCancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(btnStart)
                .addContainerGap())
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnStart)
                    .add(btnCancel))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStartMouseClicked
        this.cancelled = false;
        this.setVisible(false);
    }//GEN-LAST:event_btnStartMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnLoadFromSourceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadFromSourceMouseClicked
        HashSet<String> ips = new HashSet<>();
        switch (cbxImportFrom.getSelectedIndex()) {
            case 0: {
                ips.addAll(DataStore.getAllIPSForCurrentFootprint());
                ips.addAll(DataStore.getInitialDataItems(DataStore.IP));
                break;
            }
            case 1: {
                ips.addAll(DataStore.getInitialDataItems(DataStore.IP));
                break;
            }
            case 2: {

            }
        }
        for (String ip : ips) {
            this.txtIPRanges.append(ip + "\n");
        }
    }//GEN-LAST:event_btnLoadFromSourceMouseClicked

    private void cbxMakeRangesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMakeRangesItemStateChanged
        this.cbxRanges.setEnabled(cbxMakeRanges.isSelected());
        if (this.buffer.isEmpty()) {
            if (cbxMakeRanges.isSelected()) {
                this.buffer = txtIPRanges.getText();
            } else {
                txtIPRanges.setText(this.buffer);
                this.buffer = "";
            }
        }
        this.cbxRanges.setSelectedIndex(0);
}//GEN-LAST:event_cbxMakeRangesItemStateChanged

    private void cbxRangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxRangesActionPerformed
        HashSet<String> ips = new HashSet<String>();
        for (String ip : this.buffer.split("\\n")) {
            String[] ipParts = ip.split("\\.");
            String modIp = "";
            switch (cbxRanges.getSelectedIndex()) {
                case 0:
                    modIp = String.format("%s.%s.%s.0/24", ipParts[0], ipParts[1], ipParts[2]);
                    break;
                case 1:
                    modIp = String.format("%s.%s.0.0/16", ipParts[0], ipParts[1]);
                    break;

                default:
                    return;
            }
            ips.add(modIp);
        }

        txtIPRanges.setText("");
        for (String ip : ips) {
            txtIPRanges.append(ip + "\n");
        }
}//GEN-LAST:event_cbxRangesActionPerformed

    public ArrayList<String> getTargets() {
        ArrayList<String> result = new ArrayList<String>();
        String[] ips = txtIPRanges.getText().split("\n");
        for (String ip : ips) {
            @SuppressWarnings("static-access")
            ArrayList<String> al = new JIPCalc(ip).getTargets();
            result.addAll(al);
        }
        return result;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLoadFromSource;
    private javax.swing.JButton btnStart;
    private javax.swing.JComboBox cbxImportFrom;
    private javax.swing.JCheckBox cbxMakeRanges;
    private javax.swing.JComboBox cbxRanges;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtIPRanges;
    // End of variables declaration//GEN-END:variables

}
