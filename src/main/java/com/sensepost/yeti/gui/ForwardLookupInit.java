package com.sensepost.yeti.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import com.sensepost.yeti.common.*;
import com.sensepost.yeti.persistence.DataStore;

/**
 *
 * @author willemm
 */
public class ForwardLookupInit extends BaseDlg {

    private final DefaultListModel model = new DefaultListModel();

    public ForwardLookupInit() {
        initComponents();
        setLocationRelativeTo(null);
        setModalityType(ModalityType.APPLICATION_MODAL);
        populateFilelist();
        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder("Input parameters"));

    }

    private void populateFilelist() {
        List<String> files = UtilFunctions.filesInDir(ConfigSettings.getForwardLookupBruteListLocation());
        Iterator i = files.iterator();
        while (i.hasNext()) {
            model.addElement(new CheckListItem(i.next().toString()));
        }
        lstBrutefiles.setModel(this.model);
        lstBrutefiles.setCellRenderer(new CheckListRenderer());
        lstBrutefiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstBrutefiles.addMouseListener(new checkListMouseAdapter());
    }

    public boolean getCheckARecord() {
        return true;
    }

    public boolean getCheckAAAARecord() {
        return cbxAAAARecord.isSelected();
    }

    public boolean getCheckCNameRecord() {
        return cbxCNameRecord.isSelected();
    }

    public boolean getCheckTXTRecord() {
        return cbxTXTRecord.isSelected();
    }

    public List<String> getDomains() {
        List<String> result = new ArrayList<String>();
        String[] domains = txtDomains.getText().split("\n");
        for (int idx = 0; idx < domains.length; idx++) {
            result.add(domains[idx]);
        }
        return result;
    }

    public List<String> getBruteforceName() throws FileNotFoundException, IOException {
        HashSet<String> tempRes = new HashSet<String>();

        List<String> result = new ArrayList<String>();
        for (int idx = 0; idx < model.size(); idx++) {
            if (((CheckListItem) model.get(idx)).isSelected) {
                String filename = ((CheckListItem) model.get(idx)).toString();
                tempRes.addAll(UtilFunctions.fileToList(ConfigSettings.getForwardLookupBruteListLocation() + "/" + filename));
            }
        }
        result.addAll(tempRes);
        return result;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        choice1 = new java.awt.Choice();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstBrutefiles = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDomains = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        cbxImportFrom = new javax.swing.JComboBox();
        btnLoadFromSource = new javax.swing.JButton();
        cbxSelectAll = new javax.swing.JCheckBox();
        cbxARecord = new javax.swing.JCheckBox();
        cbxAAAARecord = new javax.swing.JCheckBox();
        cbxCNameRecord = new javax.swing.JCheckBox();
        cbxTXTRecord = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setName("Form"); // NOI18N

        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder("Input parameters"));
        pnlMain.setName("pnlMain"); // NOI18N

        choice1.setName("choice1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        lstBrutefiles.setName("lstBrutefiles"); // NOI18N
        jScrollPane1.setViewportView(lstBrutefiles);

        jLabel1.setText("Bruteforce files..."); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Domains..."); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        txtDomains.setColumns(20);
        txtDomains.setRows(5);
        txtDomains.setName("txtDomains"); // NOI18N
        jScrollPane2.setViewportView(txtDomains);

        jLabel3.setText("Sources"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        cbxImportFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Initial data", "Discovered domains" }));
        cbxImportFrom.setName("cbxImportFrom"); // NOI18N

        btnLoadFromSource.setText("Load"); // NOI18N
        btnLoadFromSource.setName("btnLoadFromSource"); // NOI18N
        btnLoadFromSource.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadFromSourceMouseClicked(evt);
            }
        });

        cbxSelectAll.setText("Select all"); // NOI18N
        cbxSelectAll.setName("cbxSelectAll"); // NOI18N
        cbxSelectAll.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbxSelectAllStateChanged(evt);
            }
        });
        cbxSelectAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSelectAllItemStateChanged(evt);
            }
        });

        cbxARecord.setSelected(true);
        cbxARecord.setText("ARecord"); // NOI18N
        cbxARecord.setEnabled(false);
        cbxARecord.setName("cbxARecord"); // NOI18N

        cbxAAAARecord.setSelected(true);
        cbxAAAARecord.setText("AAAARecord"); // NOI18N
        cbxAAAARecord.setName("cbxAAAARecord"); // NOI18N

        cbxCNameRecord.setSelected(true);
        cbxCNameRecord.setText("CNameRecord"); // NOI18N
        cbxCNameRecord.setName("cbxCNameRecord"); // NOI18N

        cbxTXTRecord.setSelected(true);
        cbxTXTRecord.setText("TXTRecord"); // NOI18N
        cbxTXTRecord.setName("cbxTXTRecord"); // NOI18N

        jLabel4.setText("Check DNS Records"); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxSelectAll))
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxImportFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLoadFromSource))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxTXTRecord)
                            .addComponent(cbxCNameRecord)
                            .addComponent(cbxAAAARecord)
                            .addComponent(cbxARecord)
                            .addComponent(jLabel4))))
                .addGap(71, 71, 71))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxSelectAll)
                    .addComponent(cbxImportFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoadFromSource)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(cbxARecord)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxAAAARecord)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxCNameRecord)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxTXTRecord)
                                .addContainerGap())
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
        );

        btnStart.setText("Start"); // NOI18N
        btnStart.setName("btnStart"); // NOI18N
        btnStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStartMouseClicked(evt);
            }
        });

        btnCancel.setText("Cancel"); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(489, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStart)
                .addContainerGap())
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, 667, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnStartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStartMouseClicked
        boolean showWarning = true;
        for (int idx = 0; idx < model.size(); idx++) {
            if (((CheckListItem) model.get(idx)).isSelected) {
                showWarning = false;
            }
        }
        if (!showWarning) {
            this.cancelled = false;
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please select a bruteforce word list(s)", "Selection required", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnStartMouseClicked

    private void btnLoadFromSourceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadFromSourceMouseClicked
        HashSet<String> domains = new HashSet<>();

        switch (cbxImportFrom.getSelectedIndex()) {
            case 0: {
                domains.addAll(DataStore.getDomains());
                domains.addAll(DataStore.getInitialDataItems(DataStore.DOMAIN));
                break;
            }
            case 1: {
                domains.addAll(DataStore.getInitialDataItems(DataStore.DOMAIN));
                break;
            }
            case 2: {

            }
        }

        for (String domain : domains) {
            this.txtDomains.append(domain + "\n");
        }

}//GEN-LAST:event_btnLoadFromSourceMouseClicked

    private void cbxSelectAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSelectAllItemStateChanged

    }//GEN-LAST:event_cbxSelectAllItemStateChanged

    private void cbxSelectAllStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbxSelectAllStateChanged
        for (int idx = 0; idx < model.size(); idx++) {
            ((CheckListItem) model.get(idx)).setSelected(cbxSelectAll.isSelected());
        }
        this.repaint();
    }//GEN-LAST:event_cbxSelectAllStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLoadFromSource;
    private javax.swing.JButton btnStart;
    private javax.swing.JCheckBox cbxAAAARecord;
    private javax.swing.JCheckBox cbxARecord;
    private javax.swing.JCheckBox cbxCNameRecord;
    private javax.swing.JComboBox cbxImportFrom;
    private javax.swing.JCheckBox cbxSelectAll;
    private javax.swing.JCheckBox cbxTXTRecord;
    private java.awt.Choice choice1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstBrutefiles;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JTextArea txtDomains;
    // End of variables declaration//GEN-END:variables

    class CheckListItem {

        private String label;
        private boolean isSelected = false;

        public CheckListItem(String label) {
            this.label = label;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String toString() {
            return label;
        }
    }

    // Handles rendering cells in the list using a check box
    class CheckListRenderer extends JCheckBox implements ListCellRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckListItem) value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }

    class checkListMouseAdapter extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            JList list = (JList) event.getSource();
            int index = list.locationToIndex(event.getPoint());
            CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
            item.setSelected(!item.isSelected());
            list.repaint(list.getCellBounds(index, index));
        }
    }
}
