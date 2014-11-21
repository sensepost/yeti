package com.sensepost.yeti.helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.sensepost.yeti.results.DomainResult;
import org.xbill.DNS.*;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.Log;

/**
 *
 * @author willemm
 */
public class TldExpandHelper {

    private static List<String> tldList;

    public static List<String> getTldList(String fileLocation) throws FileNotFoundException, IOException {
        if (fileLocation.isEmpty()) {
            fileLocation = ConfigSettings.getTldSourceListLocation();
        }
        List<String> result = new ArrayList<>();
        if (TldExpandHelper.tldList == null) {
            result.addAll(UtilFunctions.fileToList(fileLocation));
        } else {
            result.addAll(TldExpandHelper.tldList);
        }
        return result;
    }

    public static List<DomainResult> expandDomains(String rootName) throws FileNotFoundException, IOException {
        ArrayList<DomainResult> result = new ArrayList<>();
        List<String> tlds = TldExpandHelper.getTldList("");

        Iterator i = tlds.iterator();
        while (i.hasNext()) {
            String tld = i.next().toString();
            String domainToCheck = rootName + tld;
            Log.debug("Trying " + domainToCheck);
            Record[] recs = new Lookup(domainToCheck, Type.NS).run();

            if (recs != null) {
                Log.debug(((NSRecord) recs[0]).getAdditionalName().toString());
                DomainResult newDomain = new DomainResult(domainToCheck);
                newDomain.setNameServer(((NSRecord) recs[0]).getAdditionalName().toString());
                result.add(newDomain);
            }
        }
        return result;
    }
}
