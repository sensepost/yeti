package com.sensepost.yeti.gui;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.models.HostModel;
import com.sensepost.yeti.persistence.DataStore;
import com.sensepost.yeti.treeview.AttributeValue;
import java.util.List;

/**
 *
 * @author willem
 */
public class HostResults extends javax.swing.JPanel implements DisplayResultIFace {

    private final HostModel model = new HostModel();

    public HostResults() {
        initComponents();
        tblHosts.setModel(model);
    }

    public void loadData() {
        model.loadData();
        lbStats.setText(String.format("%d hosts", model.getRowCount()));
    }

    public void loadDetail() {
        DefaultListModel ipModel = new DefaultListModel();
        DefaultListModel attrModel = new DefaultListModel();

        List<String> ips = DataStore.getIpAddressesForHost(model.getItemAt(tblHosts.getSelectedRow()).getHostName());
        if (ips != null) {
            for (String ip : ips) {
                ipModel.addElement(ip);
            }
        }

        List<AttributeValue> attrs = DataStore.getHostAttributesKeyValue(model.getItemAt(tblHosts.getSelectedRow()).getHostName());
        if (attrs != null) {
            for (AttributeValue attr : attrs) {
                attrModel.addElement(attr.toString());
            }
        }

        lbIPs.setModel(ipModel);
        lbAttributes.setModel(attrModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        miReload = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miLaunchInBrowser = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miDeleteHost = new javax.swing.JMenuItem();
        miDeleteDomain = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnExport = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHosts = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lbIPs = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lbAttributes = new javax.swing.JList();
        lbStats = new javax.swing.JLabel();

        miReload.setText("Refresh");
        miReload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miReloadMouseClicked(evt);
            }
        });
        jPopupMenu1.add(miReload);
        jPopupMenu1.add(jSeparator1);

        miLaunchInBrowser.setText("jMenuItem1");
        miLaunchInBrowser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miLaunchInBrowserMouseClicked(evt);
            }
        });
        jPopupMenu1.add(miLaunchInBrowser);
        jPopupMenu1.add(jSeparator2);

        miDeleteHost.setText("jMenuItem2");
        miDeleteHost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miDeleteHostMouseClicked(evt);
            }
        });
        jPopupMenu1.add(miDeleteHost);

        miDeleteDomain.setText("jMenuItem3");
        miDeleteDomain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miDeleteDomainMouseClicked(evt);
            }
        });
        jPopupMenu1.add(miDeleteDomain);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnExport.setText("Export"); // NOI18N
        btnExport.setFocusable(false);
        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnExport);

        btnReload.setText("Reload"); // NOI18N
        btnReload.setFocusable(false);
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("IP Addresses"));

        tblHosts.setAutoCreateRowSorter(true);
        tblHosts.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHosts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHostsMouseClicked(evt);
            }
        });
        tblHosts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblHostsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblHosts);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jSplitPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane2.setDividerLocation(250);
        jSplitPane2.setDividerSize(4);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("IP Addresses"));

        jScrollPane2.setViewportView(lbIPs);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Attributes"));

        jScrollPane3.setViewportView(lbAttributes);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jSplitPane1.setRightComponent(jPanel2);

        lbStats.setText("0 Ips"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbStats)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addGap(4, 4, 4)
                .addComponent(lbStats))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        model.loadData();
        lbStats.setText(String.format("%d ips", model.getRowCount()));
    }//GEN-LAST:event_btnReloadActionPerformed

    private void tblHostsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHostsMouseClicked
        loadDetail();
    }//GEN-LAST:event_tblHostsMouseClicked

    private void tblHostsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblHostsKeyReleased
        loadDetail();
    }//GEN-LAST:event_tblHostsKeyReleased

    private void miReloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miReloadMouseClicked
        model.loadData();
        lbStats.setText(String.format("%d hosts", model.getRowCount()));
    }//GEN-LAST:event_miReloadMouseClicked

    private void miDeleteHostMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miDeleteHostMouseClicked
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this host?", "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String hostname = ((HostModel) tblHosts.getModel()).getValueAt(tblHosts.getSelectedRow(), 0).toString();
            DataStore.deleteHostFromFootprint(hostname);
            model.loadData();
            lbStats.setText(String.format("%d hosts", model.getRowCount()));
        }
    }//GEN-LAST:event_miDeleteHostMouseClicked

    private void miDeleteDomainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miDeleteDomainMouseClicked
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this domain, \nand all it's hosts/subdomains?", "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String domain = ((HostModel) tblHosts.getModel()).getValueAt(tblHosts.getSelectedRow(), 1).toString();
            DataStore.deleteDomainFromFootprint(domain);
            model.loadData();
            lbStats.setText(String.format("%d hosts", model.getRowCount()));
        }
    }//GEN-LAST:event_miDeleteDomainMouseClicked

    private void miLaunchInBrowserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miLaunchInBrowserMouseClicked
        String siteAddress = "http://" + ((HostModel) tblHosts.getModel()).getValueAt(tblHosts.getSelectedRow(), 0).toString();
        UtilFunctions.launchBrowser(siteAddress);
    }//GEN-LAST:event_miLaunchInBrowserMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReload;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList lbAttributes;
    private javax.swing.JList lbIPs;
    private javax.swing.JLabel lbStats;
    private javax.swing.JMenuItem miDeleteDomain;
    private javax.swing.JMenuItem miDeleteHost;
    private javax.swing.JMenuItem miLaunchInBrowser;
    private javax.swing.JMenuItem miReload;
    private javax.swing.JTable tblHosts;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setModel(DefaultTableModel model) {
        tblHosts.setModel((HostModel) model);
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) tblHosts.getModel();
    }
}
