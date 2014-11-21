package com.sensepost.yeti.gui;

import com.sensepost.yeti.common.Globals;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.persistence.DataStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author willemmouton
 */
public class StartFootprintInit extends BaseDlg {

    public StartFootprintInit() {
        initComponents();
        setLocationRelativeTo(null);
        lbFootprintName.setText(Globals.getCurrentFootprintName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRootTerms = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtKnownDomains = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKnownIPS = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtKnownHosts = new javax.swing.JTextArea();
        lbFootprintName = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Known info..."));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText("Footprint name"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtRootTerms.setColumns(20);
        txtRootTerms.setRows(5);
        txtRootTerms.setName("txtRootTerms"); // NOI18N
        jScrollPane1.setViewportView(txtRootTerms);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Root terms", jPanel2);

        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        txtKnownDomains.setColumns(20);
        txtKnownDomains.setRows(5);
        txtKnownDomains.setName("txtKnownDomains"); // NOI18N
        jScrollPane4.setViewportView(txtKnownDomains);

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Known domains", jPanel5);

        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        txtKnownIPS.setColumns(20);
        txtKnownIPS.setRows(5);
        txtKnownIPS.setName("txtKnownIPS"); // NOI18N
        jScrollPane2.setViewportView(txtKnownIPS);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Known ip ranges", jPanel3);

        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        txtKnownHosts.setColumns(20);
        txtKnownHosts.setRows(5);
        txtKnownHosts.setName("txtKnownHosts"); // NOI18N
        jScrollPane3.setViewportView(txtKnownHosts);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Known hosts", jPanel4);

        lbFootprintName.setText("n/a"); // NOI18N
        lbFootprintName.setName("lbFootprintName"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lbFootprintName)
                .addContainerGap(455, Short.MAX_VALUE))
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(lbFootprintName))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
        );

        btnStart.setText("Done"); // NOI18N
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
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(519, Short.MAX_VALUE)
                .add(btnStart)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnStart)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStartMouseClicked
        List<String> rootTerms = new ArrayList<>();
        List<String> ips = new ArrayList<>();
        List<String> domains = new ArrayList<>();
        List<String> hosts = new ArrayList<>();
        
        if (txtRootTerms.getText().length() > 0) {
            String[] termsArr = txtRootTerms.getText().split("\n");
            rootTerms = Arrays.asList(termsArr);
        }

        if (txtKnownIPS.getText().length() > 0) {
            String[] ipArr = txtKnownIPS.getText().split("\n");
            ips = Arrays.asList(ipArr);
        }

        if (txtKnownDomains.getText().length() > 0) {
            String[] domainArr = txtKnownDomains.getText().split("\n");
            domains = Arrays.asList(domainArr);
        }

        if (txtKnownHosts.getText().length() > 0) {
            String[] knownHosts = txtKnownHosts.getText().split("\n");
            for (String s : knownHosts) {
                if (!s.isEmpty()) {
                    hosts.add(s);
                    String domain = NetworkTools.getDomainFromHost(s);
                    String rootTerm = NetworkTools.getRootTermFromDomain(domain);
                    domains.add(domain);
                    rootTerms.add(rootTerm);
                }
            }
        }
        
        DataStore.addInitialItems(rootTerms, ips, domains, hosts);
        setVisible(false);
    }//GEN-LAST:event_btnStartMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbFootprintName;
    private javax.swing.JTextArea txtKnownDomains;
    private javax.swing.JTextArea txtKnownHosts;
    private javax.swing.JTextArea txtKnownIPS;
    private javax.swing.JTextArea txtRootTerms;
    // End of variables declaration//GEN-END:variables

}
