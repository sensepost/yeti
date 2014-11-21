package com.sensepost.yeti.gui;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.common.Globals;
import com.sensepost.yeti.models.IpModel;
import com.sensepost.yeti.persistence.DataStore;

/**
 *
 * @author willemmouton
 */
public class IpResults extends javax.swing.JPanel implements DisplayResultIFace {

    private final IpModel model = new IpModel();

    public IpResults() {
        //this.model.loadData();
        initComponents();
        tblResults.setModel(model);
    }

    public void loadData() {
        DefaultListModel hostModel = new DefaultListModel();
        DefaultListModel portModel = new DefaultListModel();

        if (model != null && tblResults != null) {
            if (tblResults.getSelectedRow() >= 0 && model.getItemAt(tblResults.getSelectedRow()) != null) {
                List<String> hosts = DataStore.getHostsPerIp(model.getItemAt(tblResults.getSelectedRow()));
                if (hosts != null && !hosts.isEmpty()) {
                    for (String host : hosts) {
                        hostModel.addElement(host);
                    }
                }

                List<com.sensepost.yeti.persistence.entities.Port> ports = DataStore.getPortsForIp(model.getItemAt(tblResults.getSelectedRow()), Globals.getCurrentFootprintId());
                if (ports != null && !ports.isEmpty()) {
                    for (com.sensepost.yeti.persistence.entities.Port p : ports) {
                        portModel.addElement(p.getPortNumber());
                    }
                }
            }

            lbHosts.setModel(hostModel);
            lbPorts.setModel(portModel);
            lbStats.setText(String.format("%d ips", model.getRowCount()));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmIpMenu = new javax.swing.JPopupMenu();
        miDelete = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnExport = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lbHosts = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lbPorts = new javax.swing.JList();
        lbStats = new javax.swing.JLabel();

        pmIpMenu.setName("pmIpMenu"); // NOI18N

        miDelete.setText("Delete ip"); // NOI18N
        miDelete.setName("miDelete"); // NOI18N
        miDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeleteActionPerformed(evt);
            }
        });
        pmIpMenu.add(miDelete);

        setName("Form"); // NOI18N

        jToolBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnExport.setText("Export"); // NOI18N
        btnExport.setFocusable(false);
        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExport.setName("btnExport"); // NOI18N
        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnExport);

        btnReload.setText("Reload"); // NOI18N
        btnReload.setFocusable(false);
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReload.setName("btnReload"); // NOI18N
        btnReload.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReload);

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane1.setDividerLocation(350);
        jSplitPane1.setDividerSize(4);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("IP Addresses"));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblResults.setAutoCreateRowSorter(true);
        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblResults.setComponentPopupMenu(pmIpMenu);
        tblResults.setName("tblResults"); // NOI18N
        tblResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultsMouseClicked(evt);
            }
        });
        tblResults.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblResultsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblResults);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setName("jPanel2"); // NOI18N

        jSplitPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane2.setDividerLocation(250);
        jSplitPane2.setDividerSize(4);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setName("jSplitPane2"); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Hosts"));
        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        lbHosts.setName("lbHosts"); // NOI18N
        jScrollPane2.setViewportView(lbHosts);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ports"));
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        lbPorts.setName("lbPorts"); // NOI18N
        jScrollPane3.setViewportView(lbPorts);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel4);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jSplitPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel2);

        lbStats.setText("0 Ips"); // NOI18N
        lbStats.setName("lbStats"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jSplitPane1)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(lbStats)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSplitPane1)
                .add(4, 4, 4)
                .add(lbStats))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        model.loadData();
        lbStats.setText(String.format("%d ips", model.getRowCount()));
}//GEN-LAST:event_btnReloadActionPerformed

    private void tblResultsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblResultsKeyReleased
        loadData();
    }//GEN-LAST:event_tblResultsKeyReleased

    private void tblResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultsMouseClicked
        loadData();
    }//GEN-LAST:event_tblResultsMouseClicked

    private void miDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeleteActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this ip, \nand all it's hosts/subdomains?", "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String ip = ((IpModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 0).toString();
            DataStore.deleteIpFromFootprint(ip);
            model.loadData();
            lbStats.setText(String.format("%d ips", model.getRowCount()));
        }
    }//GEN-LAST:event_miDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReload;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList lbHosts;
    private javax.swing.JList lbPorts;
    private javax.swing.JLabel lbStats;
    private javax.swing.JMenuItem miDelete;
    private javax.swing.JPopupMenu pmIpMenu;
    private javax.swing.JTable tblResults;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setModel(DefaultTableModel model) {
        tblResults.setModel(model);
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) tblResults.getModel();
    }
}
