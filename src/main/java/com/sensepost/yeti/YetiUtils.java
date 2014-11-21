package com.sensepost.yeti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YetiUtils {

    private static int[] ipToByte(String ip) {
        int[] result = new int[4];
        String[] ipPart = ip.split("\\.");
        result[0] = Integer.parseInt(ipPart[0]);
        result[1] = Integer.parseInt(ipPart[1]);
        result[2] = Integer.parseInt(ipPart[2]);
        result[3] = Integer.parseInt(ipPart[3]);

        return result;
    }

    public static List<String> genIPList(String startIp, String endIp) {
        List<String> result = new ArrayList<>();
        int[] beginIp = ipToByte(startIp);
        int[] stopIp = ipToByte(endIp);
        //192.168.0.0 192.168.0.255
        for (int idxa = beginIp[0]; idxa <= stopIp[0]; idxa++) {
            for (int idxb = beginIp[1]; idxb <= stopIp[1]; idxb++) {
                for (int idxc = beginIp[2]; idxc <= stopIp[2]; idxc++) {
                    for (int idxd = beginIp[3]; idxd <= stopIp[3]; idxd++) {
                        result.add(String.format("%d.%d.%d.%d", idxa, idxb, idxc, idxd));
                    }
                }
            }

        }

        return result;
    }

    public static String[] splitToPairs(String Line) {
        Line = Line.replace(" + ", ", ");
        ArrayList<String> result = new ArrayList<>();
        boolean foundQwote = false;
        String pair = "";
        for (int idx = 0; idx < Line.length(); idx++) {
            if (Line.charAt(idx) == '"') {
                foundQwote = !foundQwote;
            } else if (Line.charAt(idx) == ',') {
                if (!foundQwote) {
                    result.add(pair.trim());
                    pair = "";
                } else {
                    pair = pair + Line.charAt(idx);
                }
            } else {
                pair = pair + Line.charAt(idx);
            }
        }
        result.add(pair.trim());
        return result.toArray(new String[result.size()]);
    }

    public static Map<String, String> hastTableFromString(String certLine) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = YetiUtils.splitToPairs(certLine);
        for (String str : pairs) {
            try {

                String[] keyVal = str.split("=");
                keyVal[0] = keyVal[0].trim();
                keyVal[1] = keyVal[1].trim();
                String line = keyVal[1];
                if (result.containsKey(keyVal[0])) {
                    line = result.get(keyVal[0]) + ", " + line;
                }
                result.put(keyVal[0], line);
            } catch (Exception ex) {
                Logger.getLogger("YetiUtils.hastTableFromString").log(Level.WARNING, null, ex);
            }
        }
        return result;
    }
}
