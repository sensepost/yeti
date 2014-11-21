package com.sensepost.yeti.reports;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.persistence.DataStore;
import com.sensepost.yeti.results.CertResult;
import com.sensepost.yeti.results.DomainResult;
import com.sensepost.yeti.results.ForwardLookupResult;
import com.sensepost.yeti.results.ReverseLookupResult;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author willemm
 */
public class ResultExport {

    public static void exportToKML() {
        String filename = UtilFunctions.saveFile("*.kml");
        if (filename.isEmpty()) {
            return;
        }
        String kmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n"
                + "<Document>\n"
                + "%s"
                + "</Document>\n</kml>\n";

        String kmlPlace = "<Placemark>\n"
                + "<name>%s</name>\n"
                + "<description>%s</description>\n"
                + "<open>true</open>\n"
                + "<Point>"
                + "<coordinates>%s,%s</coordinates></Point>\n"
                + "</Placemark>\n";

        try {
            LookupService cl = new LookupService(ConfigSettings.getGeoIPCityFile(),
                    LookupService.GEOIP_MEMORY_CACHE);
            String kmlBodyPayload = "";
            for (String hostName : DataStore.getHosts()) {
                for (String ipAddress : DataStore.getIpAddressesForHost(hostName)) {
                    Location l = cl.getLocation(ipAddress);
                    if (l != null) {
                        kmlBodyPayload += String.format(kmlPlace, ipAddress, 
                                hostName, String.valueOf(l.longitude), String.valueOf(l.latitude));
                    }
                }
            }

            String kmlData = String.format(kmlBody, kmlBodyPayload);
            try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
                out.write(kmlData);
            }
        } catch (IOException ex) {
            Logger.getLogger("networkMapview.btnExportToKMLActionPerformed").log(Level.SEVERE, null, ex);
        }
    }

    public static void ExportForwardLookupsToXLS(String filename, List<Object> data) throws IOException {
        try {
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true);
            WritableCellFormat titleformat = new WritableCellFormat(titleFont);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
            WritableSheet sheet = workbook.createSheet("Forward lookups", 0);
            sheet.addCell(new Label(0, 0, "Domain name", titleformat));
            sheet.addCell(new Label(1, 0, "Host name", titleformat));
            sheet.addCell(new Label(2, 0, "IP address", titleformat));
            sheet.addCell(new Label(3, 0, "Type", titleformat));
            int nextRow = 1;
            Iterator i = data.iterator();
            while (i.hasNext()) {
                ForwardLookupResult res = (ForwardLookupResult) i.next();
                sheet.addCell(new Label(0, nextRow, res.getDomainName()));
                sheet.addCell(new Label(1, nextRow, res.getHostName()));
                sheet.addCell(new Label(2, nextRow, res.getIpAddress()));
                sheet.addCell(new Label(3, nextRow, res.getLookupType()));
                nextRow++;
            }
            workbook.write();
            workbook.close();
        } catch (WriteException ex) {
            Logger.getLogger("resultExport.ExportForwardLookupsToXLS").log(Level.SEVERE, null, ex);
        }
    }

    public static void ExportTLDToXLS(String filename, ArrayList<Object> data) throws IOException {
        try {
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true);
            WritableCellFormat titleformat = new WritableCellFormat(titleFont);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
            WritableSheet sheet = workbook.createSheet("TLD Expand", 0);
            sheet.addCell(new Label(0, 0, "Domain name", titleformat));
            sheet.addCell(new Label(1, 0, "Name server", titleformat));
            sheet.addCell(new Label(2, 0, "Admin name", titleformat));
            sheet.addCell(new Label(3, 0, "Registrant", titleformat));
            int nextRow = 1;
            Iterator i = data.iterator();
            while (i.hasNext()) {
                DomainResult res = (DomainResult) i.next();
                sheet.addCell(new Label(0, nextRow, res.getDomainName()));
                sheet.addCell(new Label(1, nextRow, res.getNameServer()));
                sheet.addCell(new Label(2, nextRow, res.getAdminName()));
                sheet.addCell(new Label(3, nextRow, res.getRegistrant()));
                nextRow++;
            }
            workbook.write();
            workbook.close();
        } catch (WriteException ex) {
            Logger.getLogger("resultExport.ExportForwardLookupsToXLS").log(Level.SEVERE, null, ex);
        }
    }

    public static void ExportCertToXLS(String filename, ArrayList<Object> data) throws IOException {
        try {
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true);
            WritableCellFormat titleformat = new WritableCellFormat(titleFont);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
            WritableSheet sheet = workbook.createSheet("SSLCert CN", 0);
            sheet.addCell(new Label(0, 0, "IP address", titleformat));
            sheet.addCell(new Label(1, 0, "Host name", titleformat));
            sheet.addCell(new Label(2, 0, "Domain name", titleformat));
            int nextRow = 1;
            Iterator i = data.iterator();
            while (i.hasNext()) {
                CertResult res = (CertResult) i.next();
                sheet.addCell(new Label(0, nextRow, res.getIpAddress()));
                sheet.addCell(new Label(1, nextRow, res.getHostName()));
                sheet.addCell(new Label(2, nextRow, res.getDomainName()));
                nextRow++;
            }
            workbook.write();
            workbook.close();
        } catch (WriteException ex) {
            Logger.getLogger("resultExport.ExportForwardLookupsToXLS").log(Level.SEVERE, null, ex);
        }
    }

    public static void ExportReverseToXLS(String filename, ArrayList<Object> data) throws IOException {
        try {
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true);
            WritableCellFormat titleformat = new WritableCellFormat(titleFont);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
            WritableSheet sheet = workbook.createSheet("Bing IP search", 0);
            sheet.addCell(new Label(0, 0, "IP address", titleformat));
            sheet.addCell(new Label(1, 0, "Domain name", titleformat));
            sheet.addCell(new Label(2, 0, "Host name", titleformat));
            int nextRow = 1;
            Iterator i = data.iterator();
            while (i.hasNext()) {
                ReverseLookupResult res = (ReverseLookupResult) i.next();
                sheet.addCell(new Label(0, nextRow, res.getIpAddress()));
                sheet.addCell(new Label(1, nextRow, res.getDomainName()));
                sheet.addCell(new Label(2, nextRow, res.getHostName()));
                nextRow++;
            }
            workbook.write();
            workbook.close();
        } catch (WriteException ex) {
            Logger.getLogger("resultExport.ExportReverseToXLS").log(Level.SEVERE, null, ex);
        }
    }
}
