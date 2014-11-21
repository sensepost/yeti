package com.sensepost.yeti.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.sensepost.yeti.persistence.DataStore;

public class YileInit extends BaseDlg {

    /**
     * Creates new form yileInit
     */
    public YileInit() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDomains = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        cbxImportFrom = new javax.swing.JComboBox();
        btnLoadFromSource = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Input parameters"));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setText("Hostname/URLs"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtDomains.setColumns(20);
        txtDomains.setRows(5);
        txtDomains.setName("txtDomains"); // NOI18N
        txtDomains.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDomainsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtDomains);

        jLabel3.setText("Sources"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        cbxImportFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Initial domains", "Discovered domains" }));
        cbxImportFrom.setEnabled(false);
        cbxImportFrom.setName("cbxImportFrom"); // NOI18N

        btnLoadFromSource.setText("Load"); // NOI18N
        btnLoadFromSource.setEnabled(false);
        btnLoadFromSource.setName("btnLoadFromSource"); // NOI18N
        btnLoadFromSource.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadFromSourceMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(39, 39, 39)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cbxImportFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnLoadFromSource))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel2)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbxImportFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(btnLoadFromSource))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
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
                .addContainerGap(222, Short.MAX_VALUE)
                .add(btnCancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(btnStart)
                .addContainerGap())
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnCancel)
                    .add(btnStart)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDomainsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDomainsMouseClicked

}//GEN-LAST:event_txtDomainsMouseClicked

    private void btnLoadFromSourceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadFromSourceMouseClicked
        HashSet<String> hosts = new HashSet<>();
        switch (cbxImportFrom.getSelectedIndex()) {
            case 0: {
                hosts.addAll(DataStore.getHosts());
                hosts.addAll(DataStore.getInitialDataItems(DataStore.HOST));
                break;
            }
            case 1: {
                hosts.addAll(DataStore.getInitialDataItems(DataStore.HOST));
                break;
            }
            case 2: {

            }
        }
        for (String host : hosts) {
            this.txtDomains.append(host + "\n");
        }
}//GEN-LAST:event_btnLoadFromSourceMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.setVisible(false);
}//GEN-LAST:event_btnCancelMouseClicked

    private void btnStartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStartMouseClicked
        this.cancelled = false;
        this.setVisible(false);
}//GEN-LAST:event_btnStartMouseClicked

    public List<String> getHosts() {
        List<String> result = new ArrayList<>();
        for (String s : txtDomains.getText().split("\n")) {
            if (!s.startsWith("http")) {
                s = "http://" + s;
            }
            result.add(s);
        }
        return result;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLoadFromSource;
    private javax.swing.JButton btnStart;
    private javax.swing.JComboBox cbxImportFrom;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDomains;
    // End of variables declaration//GEN-END:variables

}
