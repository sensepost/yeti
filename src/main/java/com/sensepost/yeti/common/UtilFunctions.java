package com.sensepost.yeti.common;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import org.apache.commons.codec.binary.Base64;

public class UtilFunctions {

    public static String fixPath(String Path) {
        String result = Path.replace('/', File.separatorChar).replaceAll("%20", " ");
        return result;
    }

    public static String getFileExtension(String filename) {
        int idx = filename.lastIndexOf('.');
        String ext = filename.substring(idx + 1);
        return ext;
    }

    public static List<String> filesFromDir(String Dir) {
        File dir = new File(Dir);
        File[] files = dir.listFiles();

        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        Collections.sort(fileNames);
        return fileNames;
    }

    public static List<String> fileToList(String location) throws FileNotFoundException, IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(location))) {
            String line;
            while ((line = input.readLine()) != null) {
                if (!line.isEmpty()) {
                    result.add(line.toLowerCase());
                }
            }
        }
        return result;
    }

    public static void collectionToFile(String location, Collection<String> data) throws FileNotFoundException, IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(location))) {
            for (String s : data) {
                output.append(s);
                output.newLine();
            }
            output.flush();
        }
    }

    public static ArrayList<String> filesInDir(String directory) {
        ArrayList<String> result = new ArrayList<>();
        File dir = new File(directory);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.isDirectory();
            }
        };
        File[] files = dir.listFiles(fileFilter);
        for (File file : files) {
            String filename = file.getName();
            if (filename.startsWith("\\.")) {
                continue; //Skips *nix sysfiles
            }
            result.add(filename);
        }
        return result;
    }

    public static String saveFile(String fileExtension) {
        JFileChooser dlgFile = new JFileChooser();
        dlgFile.setFileFilter(new Filter(fileExtension));
        int returnVal = dlgFile.showSaveDialog(dlgFile);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = dlgFile.getSelectedFile();
            // Check that the file has the correct extension
            String filePath = f.getAbsolutePath();
            if (!filePath.endsWith(fileExtension)) {
                File file = new File(filePath + fileExtension);
                filePath = file.getAbsolutePath();
            }
            return filePath;
        }
        return "";
    }

    public static String getDirectory() {
        JFileChooser dlgFile = new JFileChooser();
        dlgFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = dlgFile.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = dlgFile.getSelectedFile();
            return file.getAbsolutePath();
        }
        return "";
    }

    public static String openFile(String fileExtension) {
        JFileChooser dlgFile = new JFileChooser();
        dlgFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dlgFile.setApproveButtonText("Open");

        int returnVal = dlgFile.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = dlgFile.getSelectedFile();
            return file.getAbsolutePath();
        }
        return "";
    }

    public static void launchBrowser(String url) {
        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger("utilFunctions.launchBrowser").log(Level.SEVERE, null, ex);
            }

        }
    }

    public static String calculateRFC2104HMAC(String data, String key)
            throws java.security.SignatureException {
        String result;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = new String(Base64.encodeBase64(rawHmac));
        } catch (IllegalStateException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }

        return result;
    }

    //QC79J-CM73G-D7TTW-7CKHC-YBDD8
    static class Filter extends javax.swing.filechooser.FileFilter {

        private String fileExtension = "*.*";

        public Filter(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        @Override
        public boolean accept(File file) {
            return file.isDirectory() || file.getName().endsWith(fileExtension);
        }

        @Override
        public String getDescription() {
            return "*" + fileExtension;
        }

    }
}
