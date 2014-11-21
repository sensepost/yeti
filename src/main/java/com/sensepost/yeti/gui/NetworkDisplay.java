package com.sensepost.yeti.gui;

import java.awt.Component;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import com.sensepost.yeti.common.Globals;
import com.sensepost.yeti.common.Log;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.plugins.AttributePluginManager;
import com.sensepost.yeti.results.KeyValue;
import com.sensepost.yeti.results.Port;
import com.sensepost.yeti.treeview.Attribute;
import com.sensepost.yeti.treeview.AttributeValue;
import com.sensepost.yeti.treeview.Domain;
import com.sensepost.yeti.treeview.Host;
import com.sensepost.yeti.treeview.Hosts;
import com.sensepost.yeti.treeview.Ip;
import com.sensepost.yeti.treeview.Ips;
import com.sensepost.yeti.persistence.DataStore;
import java.util.List;

/**
 *
 * @author willemmouton
 */
public class NetworkDisplay extends javax.swing.JPanel implements DisplayResultIFace {

    protected ExecutorService executor = Executors.newCachedThreadPool();
    protected AttributePluginManager attributePlugins = null;

    public NetworkDisplay() {
        initComponents();
        rbGroup.add(miIp);
        rbGroup.add(miDomain);
        TreeRenderer treeRenderer = new TreeRenderer();
        trDomainTree.setCellRenderer(treeRenderer);
        this.trDomainTree.addTreeSelectionListener(new TreeSelectionHandler());
        attributePlugins = new AttributePluginManager();
        for (JMenuItem mi : this.attributePlugins.getPlugins()) {
            this.miPlugins.add(mi);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbGroup = new javax.swing.ButtonGroup();
        pmNetworkActions = new javax.swing.JPopupMenu();
        miOpenBrowser = new javax.swing.JMenuItem();
        miWhois = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miDelete = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miPlugins = new javax.swing.JMenu();
        pmGroup = new javax.swing.JPopupMenu();
        miDomain = new javax.swing.JRadioButtonMenuItem();
        miIp = new javax.swing.JRadioButtonMenuItem();
        pmHide = new javax.swing.JPopupMenu();
        miHideDomainAttr = new javax.swing.JCheckBoxMenuItem();
        miHideHostAttr = new javax.swing.JCheckBoxMenuItem();
        miHidePorts = new javax.swing.JCheckBoxMenuItem();
        jPanel2 = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        trDomainTree = new javax.swing.JTree();
        btnGroupBy = new javax.swing.JButton();
        btnShow = new javax.swing.JButton();

        pmNetworkActions.setName("pmNetworkActions"); // NOI18N

        miOpenBrowser.setText("Open browser"); // NOI18N
        miOpenBrowser.setName("miOpenBrowser"); // NOI18N
        miOpenBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miOpenBrowserActionPerformed(evt);
            }
        });
        pmNetworkActions.add(miOpenBrowser);

        miWhois.setText("Whois"); // NOI18N
        miWhois.setName("miWhois"); // NOI18N
        miWhois.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miWhoisActionPerformed(evt);
            }
        });
        pmNetworkActions.add(miWhois);

        jSeparator2.setName("jSeparator2"); // NOI18N
        pmNetworkActions.add(jSeparator2);

        miDelete.setText("Delete"); // NOI18N
        miDelete.setName("miDelete"); // NOI18N
        miDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeleteActionPerformed(evt);
            }
        });
        pmNetworkActions.add(miDelete);

        jSeparator1.setName("jSeparator1"); // NOI18N
        pmNetworkActions.add(jSeparator1);

        miPlugins.setText("Plugins"); // NOI18N
        miPlugins.setName("miPlugins"); // NOI18N
        pmNetworkActions.add(miPlugins);

        pmGroup.setName("pmGroup"); // NOI18N

        miDomain.setSelected(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/sensepost/yeti/gui/Bundle"); // NOI18N
        miDomain.setText(bundle.getString("NetworkDisplay.miDomain.text")); // NOI18N
        miDomain.setName("miDomain"); // NOI18N
        miDomain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miDomainMouseClicked(evt);
            }
        });
        pmGroup.add(miDomain);

        miIp.setText(bundle.getString("NetworkDisplay.miIp.text")); // NOI18N
        miIp.setName("miIp"); // NOI18N
        miIp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miIpMouseClicked(evt);
            }
        });
        miIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miIpActionPerformed(evt);
            }
        });
        pmGroup.add(miIp);

        pmHide.setName("pmHide"); // NOI18N

        miHideDomainAttr.setText(bundle.getString("NetworkDisplay.miHideDomainAttr.text")); // NOI18N
        miHideDomainAttr.setName("miHideDomainAttr"); // NOI18N
        pmHide.add(miHideDomainAttr);

        miHideHostAttr.setText(bundle.getString("NetworkDisplay.miHideHostAttr.text")); // NOI18N
        miHideHostAttr.setName("miHideHostAttr"); // NOI18N
        pmHide.add(miHideHostAttr);

        miHidePorts.setText(bundle.getString("NetworkDisplay.miHidePorts.text")); // NOI18N
        miHidePorts.setName("miHidePorts"); // NOI18N
        pmHide.add(miHidePorts);

        setName("Form"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Network view"));
        jPanel2.setName("jPanel2"); // NOI18N

        btnRefresh.setText("Refresh"); // NOI18N
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Network");
        trDomainTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        trDomainTree.setComponentPopupMenu(pmNetworkActions);
        trDomainTree.setName("trDomainTree"); // NOI18N
        trDomainTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trDomainTreeMouseClicked(evt);
            }
        });
        trDomainTree.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent evt) {
            }
            public void treeExpanded(javax.swing.event.TreeExpansionEvent evt) {
                trDomainTreeTreeExpanded(evt);
            }
        });
        jScrollPane1.setViewportView(trDomainTree);

        btnGroupBy.setText(bundle.getString("NetworkDisplay.btnGroupBy.text")); // NOI18N
        btnGroupBy.setName("btnGroupBy"); // NOI18N
        btnGroupBy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGroupByMouseClicked(evt);
            }
        });

        btnShow.setText(bundle.getString("NetworkDisplay.btnShow.text")); // NOI18N
        btnShow.setName("btnShow"); // NOI18N
        btnShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1)
            .add(jPanel2Layout.createSequentialGroup()
                .add(btnRefresh)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnGroupBy)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnShow)
                .add(0, 331, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnRefresh)
                    .add(btnGroupBy)
                    .add(btnShow))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        this.refreshView();
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void trDomainTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trDomainTreeMouseClicked

    }//GEN-LAST:event_trDomainTreeMouseClicked

    private void trDomainTreeTreeExpanded(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_trDomainTreeTreeExpanded

    }//GEN-LAST:event_trDomainTreeTreeExpanded

    private void miDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeleteActionPerformed
        if (trDomainTree.getSelectionPath() != null) {
            Object o = ((DefaultMutableTreeNode) trDomainTree.getSelectionPath().getLastPathComponent()).getUserObject();
            if (o.getClass() == Domain.class) {
                DataStore.deleteDomainFromFootprint(((Domain) o).toString());
            } else if (o.getClass() == Host.class) {
                DataStore.deleteHostFromFootprint(((Host) o).toString());
            } else if (o.getClass() == Ip.class) {
                // delete class
            } else if (o.getClass() == AttributeValue.class) {
                TreeNode tn = ((TreeNode) trDomainTree.getSelectionPath().getLastPathComponent()).getParent();
                AttributeValue av = (AttributeValue) o;
                Object obj = ((DefaultMutableTreeNode) tn.getParent()).getUserObject();
                if (obj.getClass().equals(Domain.class)) {
                    String domainName = ((Domain) obj).toString();
                    DataStore.deleteDomainAttribute(domainName, av.getKey(), av.getValue());
                } else if(obj.getClass().equals(Host.class)) {
                    String hostName = ((Host) obj).toString();
                    DataStore.deleteHostAttribute(hostName, av.getKey(), av.getValue());
                }
                
                
                
            } else if (o.getClass() == Port.class) {
                TreeNode ipNode = ((TreeNode) trDomainTree.getSelectionPath().getLastPathComponent()).getParent();
                Object ipObj = ((DefaultMutableTreeNode) ipNode).getUserObject();
                String ipAddress = ((Ip) ipObj).getValue();
                Port port = (Port) o;
                DataStore.deletePortFromIp(ipAddress, port.getPortNumber());
            }
            this.buildDomainView();
        }
    }//GEN-LAST:event_miDeleteActionPerformed

    private void doWhois(String domain) {
        try {
            String name = domain.split("\\.", 2)[0];
            String tld = domain.split("\\.", 2)[1];
            DoWhois frmWhoisTool = new DoWhois(name, tld);
            frmWhoisTool.setVisible(true);
        } catch (Exception e) {
            DoWhois frmWhoisTool = new DoWhois();
            frmWhoisTool.setVisible(true);
        }
    }

    private void miOpenBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miOpenBrowserActionPerformed
        Object o = ((DefaultMutableTreeNode) trDomainTree.getSelectionPath().getLastPathComponent()).getUserObject();
        if (o.getClass() == Domain.class) {
            //dataDiskStoreage.deleteDomainFromFootprint(((Domain)o).toString());
        } else if (o.getClass() == Host.class) {
            String siteAddress = "http://" + ((Host) o).toString();
            UtilFunctions.launchBrowser(siteAddress);
        } else if (o.getClass() == Ip.class) {
            String siteAddress = "http://" + ((Ip) o).toString();
            UtilFunctions.launchBrowser(siteAddress);
        }
    }//GEN-LAST:event_miOpenBrowserActionPerformed

    private void miWhoisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miWhoisActionPerformed
        Object o = ((DefaultMutableTreeNode) trDomainTree.getSelectionPath().getLastPathComponent()).getUserObject();
        if (o.getClass() == Domain.class) {
            this.doWhois(((Domain) o).toString());
        } else if (o.getClass() == Host.class) {

        } else if (o.getClass() == Ip.class) {

        }
    }//GEN-LAST:event_miWhoisActionPerformed

    private void btnGroupByMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGroupByMouseClicked
        pmGroup.show(jPanel2, (int) btnGroupBy.getLocation().getX(), btnGroupBy.getLocation().y + btnGroupBy.getHeight());
    }//GEN-LAST:event_btnGroupByMouseClicked

    private void btnShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowMouseClicked
        pmHide.show(jPanel2, (int) btnShow.getLocation().getX(), btnShow.getLocation().y + btnShow.getHeight());
    }//GEN-LAST:event_btnShowMouseClicked

    private void miDomainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miDomainMouseClicked

    }//GEN-LAST:event_miDomainMouseClicked

    private void miIpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miIpMouseClicked
        miIp.setSelected(true);
        miDomain.setSelected(false);
        btnGroupBy.setText("Group by ip");
        buildIpView();
    }//GEN-LAST:event_miIpMouseClicked

    private void miIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miIpActionPerformed
        miDomain.setSelected(true);
        miIp.setSelected(false);
        btnGroupBy.setText("Group by domain");
        buildDomainView();
    }//GEN-LAST:event_miIpActionPerformed

    private void buildIpView() {
    }

    public void buildDomainView() {
        DefaultMutableTreeNode root = ((DefaultMutableTreeNode) trDomainTree.getModel().getRoot());
        if (root != null) {
            root.removeAllChildren();
            List<String> domains = DataStore.getDomains(Globals.getCurrentFootprintId());
            if (domains != null) {
                for (String domainName : domains) {

                    DefaultMutableTreeNode domainNode = new DefaultMutableTreeNode(new Domain(domainName));

                    DefaultMutableTreeNode attrNode = new DefaultMutableTreeNode(new Attribute());
                    if (!miHideDomainAttr.isSelected()) {
                        for (KeyValue attr : DataStore.getDomainAttributesKeyValues(domainName)) {
                            DefaultMutableTreeNode attrValueNode = new DefaultMutableTreeNode(new AttributeValue(attr.getStrKey(), attr.getStrValue()));
                            attrNode.add(attrValueNode);
                        }
                        domainNode.add(attrNode);
                    }

                    List<String> hosts = DataStore.getHosts(domainName);
                    if (hosts != null) {
                        for (String hostName : hosts) {

                            DefaultMutableTreeNode hostNode = new DefaultMutableTreeNode(new Host(hostName));

                            DefaultMutableTreeNode hostAttrNode = new DefaultMutableTreeNode(new Attribute());
                            DefaultMutableTreeNode ipsNode = new DefaultMutableTreeNode(new Ips());
                            if (!miHideHostAttr.isSelected()) {
                                for (AttributeValue attr : DataStore.getHostAttributesKeyValue(hostName)) {
                                    DefaultMutableTreeNode attrValueNode = new DefaultMutableTreeNode(attr);
                                    hostAttrNode.add(attrValueNode);
                                }
                                hostNode.add(hostAttrNode);
                            }

                            List<String> ips = DataStore.getIpAddressesForHost(hostName);
                            if (ips != null) {
                                for (String ip : ips) {
                                    DefaultMutableTreeNode ipNode = new DefaultMutableTreeNode(new Ip(ip));
                                    if (!miHidePorts.isSelected()) {
                                        for (com.sensepost.yeti.persistence.entities.Port p : DataStore.getPortsForIp(ip)) {
                                            com.sensepost.yeti.results.Port port = new com.sensepost.yeti.results.Port(p.getPortNumber());
                                            port.setState(p.getPortState());
                                            port.setVersion(p.getVersion());
                                            port.setExtraInfo(p.getExtraInfo());

                                            DefaultMutableTreeNode portNode = new DefaultMutableTreeNode(port);
                                            ipNode.add(portNode);
                                        }
                                    }
                                    hostNode.add(ipNode);
                                }
                            }
                            domainNode.add(hostNode);
                        }
                    }

                    root.add(domainNode);
                }
            }

            ((DefaultTreeModel) trDomainTree.getModel()).reload();

            int row = 0;
            while (row < trDomainTree.getRowCount()) {
                trDomainTree.expandRow(row);
                row++;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGroupBy;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnShow;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem miDelete;
    private javax.swing.JRadioButtonMenuItem miDomain;
    private javax.swing.JCheckBoxMenuItem miHideDomainAttr;
    private javax.swing.JCheckBoxMenuItem miHideHostAttr;
    private javax.swing.JCheckBoxMenuItem miHidePorts;
    private javax.swing.JRadioButtonMenuItem miIp;
    private javax.swing.JMenuItem miOpenBrowser;
    private javax.swing.JMenu miPlugins;
    private javax.swing.JMenuItem miWhois;
    private javax.swing.JPopupMenu pmGroup;
    private javax.swing.JPopupMenu pmHide;
    private javax.swing.JPopupMenu pmNetworkActions;
    private javax.swing.ButtonGroup rbGroup;
    private javax.swing.JTree trDomainTree;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setModel(DefaultTableModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DefaultTableModel getModel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refreshView() {
        if (miDomain.isSelected()) {
            this.buildDomainView();
        } else {
            this.buildIpView();
        }
    }

    public class TreeRenderer implements TreeCellRenderer {

        private static final long serialVersionUID = 1L;

        private final DefaultTreeCellRenderer networkNodeRenderer;
        private final DefaultTreeCellRenderer domainNodeRenderer;
        private final DefaultTreeCellRenderer hostNodeRenderer;
        private final DefaultTreeCellRenderer ipNodeRenderer;
        private final DefaultTreeCellRenderer attrNodeRenderer;
        private final DefaultTreeCellRenderer attrConfigNodeRenderer;
        private final DefaultTreeCellRenderer attrVulnNodeRenderer;
        private final DefaultTreeCellRenderer hostsNodeRenderer;

        private final URL openPort = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/flag_green.png");
        private final URL filteredPort = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/flag_blue.png");
        private final URL closedPort = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/flag_red.png");

        public TreeRenderer() {

            URL networkIconUrl = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/network.png");
            URL domainIconUrl = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/star.png");
            URL hostIconUrl = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/server.png");
            URL ipIconUrl = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/bullet_blue.png");
            URL attributeIconUrl = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/attr.png");
            URL attributeConfigIconUrl = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/attr_config.png");
            URL attributeVulnIconUrl = this.getClass().getClassLoader().getResource("com/sensepost/yeti/resources/vuln.png");

            networkNodeRenderer = new DefaultTreeCellRenderer();
            networkNodeRenderer.setLeafIcon(new ImageIcon(networkIconUrl));
            networkNodeRenderer.setOpenIcon(new ImageIcon(networkIconUrl));
            networkNodeRenderer.setClosedIcon(new ImageIcon(networkIconUrl));

            domainNodeRenderer = new DefaultTreeCellRenderer();
            domainNodeRenderer.setLeafIcon(new ImageIcon(domainIconUrl));
            domainNodeRenderer.setOpenIcon(new ImageIcon(domainIconUrl));
            domainNodeRenderer.setClosedIcon(new ImageIcon(domainIconUrl));

            hostNodeRenderer = new DefaultTreeCellRenderer();
            hostNodeRenderer.setLeafIcon(new ImageIcon(hostIconUrl));
            hostNodeRenderer.setOpenIcon(new ImageIcon(hostIconUrl));
            hostNodeRenderer.setClosedIcon(new ImageIcon(hostIconUrl));

            ipNodeRenderer = new DefaultTreeCellRenderer();
            ipNodeRenderer.setLeafIcon(new ImageIcon(ipIconUrl));
            ipNodeRenderer.setOpenIcon(new ImageIcon(ipIconUrl));
            ipNodeRenderer.setClosedIcon(new ImageIcon(ipIconUrl));

            attrNodeRenderer = new DefaultTreeCellRenderer();
            attrNodeRenderer.setLeafIcon(new ImageIcon(attributeIconUrl));
            attrNodeRenderer.setOpenIcon(new ImageIcon(attributeIconUrl));
            attrNodeRenderer.setClosedIcon(new ImageIcon(attributeIconUrl));

            attrConfigNodeRenderer = new DefaultTreeCellRenderer();
            attrConfigNodeRenderer.setLeafIcon(new ImageIcon(attributeConfigIconUrl));
            attrConfigNodeRenderer.setOpenIcon(new ImageIcon(attributeConfigIconUrl));
            attrConfigNodeRenderer.setClosedIcon(new ImageIcon(attributeConfigIconUrl));

            attrVulnNodeRenderer = new DefaultTreeCellRenderer();
            attrVulnNodeRenderer.setLeafIcon(new ImageIcon(attributeVulnIconUrl));
            attrVulnNodeRenderer.setOpenIcon(new ImageIcon(attributeVulnIconUrl));
            attrVulnNodeRenderer.setClosedIcon(new ImageIcon(attributeVulnIconUrl));

            hostsNodeRenderer = new DefaultTreeCellRenderer();
            hostsNodeRenderer.setLeafIcon(new ImageIcon(networkIconUrl));
            hostsNodeRenderer.setOpenIcon(new ImageIcon(networkIconUrl));
            hostsNodeRenderer.setClosedIcon(new ImageIcon(networkIconUrl));

        }

        private DefaultTreeCellRenderer getPortRenderer(URL image) {
            DefaultTreeCellRenderer portRenderer = new DefaultTreeCellRenderer();
            portRenderer.setLeafIcon(new ImageIcon(image));
            portRenderer.setOpenIcon(new ImageIcon(image));
            portRenderer.setClosedIcon(new ImageIcon(image));

            return portRenderer;
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree,
                Object value,
                boolean selected,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) {

            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

                switch (node.getLevel()) {
                    case 0:
                        return networkNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                }
                if (node.getUserObject().getClass() == Domain.class) {
                    return domainNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                } else if (node.getUserObject().getClass() == Host.class) {
                    return hostNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                } else if (node.getUserObject().getClass() == Ip.class) {
                    return ipNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                } else if (node.getUserObject().getClass() == Attribute.class) {
                    return attrNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                } else if (node.getUserObject().getClass() == AttributeValue.class) {
                    if (((AttributeValue) node.getUserObject()).getType().compareTo("vuln") == 0) {
                        return attrVulnNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                    } else {
                        return attrConfigNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                    }
                } else if (node.getUserObject().getClass() == Hosts.class) {
                    return hostsNodeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                } else if (node.getUserObject().getClass() == Port.class) {
                    DefaultTreeCellRenderer pr = null;
                    if (((Port) node.getUserObject()).getState().compareTo("open") == 0) {
                        pr = getPortRenderer(openPort);
                    } else if (((Port) node.getUserObject()).getState().compareTo("closed") == 0) {
                        pr = getPortRenderer(closedPort);
                    } else if (((Port) node.getUserObject()).getState().compareTo("filtered") == 0) {
                        pr = getPortRenderer(filteredPort);
                    }
                    //String label = String.format("%d : %s",((port__)node.getUserObject()).getPortNumber(),((port__)node.getUserObject()).getServiceName());
                    if (pr != null) {
                        return pr.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                    }
                }
            }
            return networkNodeRenderer;
        }
    }

    private class TreeSelectionHandler implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            try {
                Log.debug("Clicked: " + e.getPath().toString());
                if (miDomain.isSelected()) {
                    String host = trDomainTree.getSelectionPath().getPathComponent(2).toString();
                    attributePlugins.setArgs(host);
                }
            } catch (Exception ex) {

            }
        }
    }
}
