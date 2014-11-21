package com.sensepost.yeti.gui;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.Globals;
import com.sensepost.yeti.persistence.DataStore;
import com.sensepost.yeti.reports.ResultExport;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;

public class NetworkMapview extends javax.swing.JPanel implements DisplayResultIFace {

    private static final long serialVersionUID = 1L;

    public NetworkMapview() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnRefresh = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Map"));
        jPanel2.setName("jPanel2"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 638, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 556, Short.MAX_VALUE)
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnRefresh.setText("Refresh"); // NOI18N
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefresh);

        btnExport.setText("Export to KML"); // NOI18N
        btnExport.setFocusable(false);
        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExport.setName("btnExport"); // NOI18N
        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportMouseClicked(evt);
            }
        });
        jToolBar1.add(btnExport);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void loadMapData() {
        Map<String, CustomWaypoint> point = new HashMap<>();
        try {
            LookupService cl = new LookupService(ConfigSettings.getGeoIPCityFile(),
                    LookupService.GEOIP_MEMORY_CACHE);

            Set<CustomWaypoint> waypoints = new HashSet<>();
            try {
                for (String host : DataStore.getHosts(Globals.getCurrentFootprintId())) {
                    for (String ip : DataStore.getIpAddressesForHost(host)) {
                        Location l = cl.getLocation(ip);
                        String hash = String.valueOf(l.latitude) + ":" + String.valueOf(l.longitude);
                        CustomWaypoint hostLocation;
                        if (point.containsKey(hash)) {
                            hostLocation = (CustomWaypoint) point.get(hash);
                        } else {
                            hostLocation = new CustomWaypoint(l.latitude, l.longitude);
                        }

                        hostLocation.addLabel(host + ": " + ip);
                        point.remove(hash);
                        point.put(hash, hostLocation);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger("NetworkMapView").log(Level.SEVERE, null, ex);
            }

            waypoints.addAll(point.values());

            WaypointPainter painter = new WaypointPainter();
            painter.setRenderer(new WaypointRenderer() {
                @Override
                public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
                    g.setPaint(new Color(0, 0, 255, 200));
                    Polygon triangle = new Polygon();
                    triangle.addPoint(0, 0);
                    triangle.addPoint(11, 11);
                    triangle.addPoint(-11, 11);
                    g.fill(triangle);
                    int startY = 10;
                    int width = 0;
                    for (String s : ((CustomWaypoint) wp).getLabel()) {
                        if (((int) g.getFontMetrics().getStringBounds(s, g).getWidth()) > width) {
                            width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                        }
                        startY += 10;
                    }

                    g.fillRoundRect(-12, 10, width + 20, startY + 5, 10, 10);

                    startY = 26;
                    width = 0;
                    g.setPaint(Color.WHITE);
                    for (String s : ((CustomWaypoint) wp).getLabel()) {
                        g.drawString(s, 0 - width, startY);
                        startY += 10;
                    }
                    return true;
                }
            });

            painter.setWaypoints(waypoints);
//            mpWorldMap.getMainMap().setOverlayPainter(painter);

        } catch (IOException ex) {
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadMapData();
}//GEN-LAST:event_btnRefreshActionPerformed

    private void btnExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMouseClicked
        ResultExport.exportToKML();
}//GEN-LAST:event_btnExportMouseClicked

    @Override
    public void setModel(DefaultTableModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DefaultTableModel getModel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    private class CustomWaypoint extends Waypoint {

        private final List<String> label = new ArrayList<>();

        public CustomWaypoint(float lat, float lon) {
            super(lat, lon);
        }

        public void addLabel(String value) {
            label.add(value);
        }

        public List<String> getLabel() {
            return label;
        }
    }
}
