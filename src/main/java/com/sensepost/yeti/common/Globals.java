package com.sensepost.yeti.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sensepost.yeti.helpers.TldExpandHelper;

public class Globals {

    private final static Set<String> tlds = new HashSet<>();

    private static int currentFootprintID = -1;
    private static String currentFootprintName = "";

    public static int getCurrentFootprintId() {
        return Globals.currentFootprintID;
    }

    public static String getCurrentFootprintName() {
        return Globals.currentFootprintName;
    }

    public static void setCurrentFootprintId(int value) {
        Globals.currentFootprintID = value;
    }

    public static void setCurrentFootprintName(String value) {
        Globals.currentFootprintName = value;
    }

    public Set<String> getTLDList() {
        if (tlds.isEmpty()) {
            try {
                tlds.addAll(TldExpandHelper.getTldList(ConfigSettings.getTldSourceListLocation()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tlds;
    }
}
