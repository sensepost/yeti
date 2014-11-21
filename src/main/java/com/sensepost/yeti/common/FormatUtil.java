package com.sensepost.yeti.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Johan Snyman
 */
public class FormatUtil {

    public static String formatTimestamp(Timestamp ts) {
        if (ts == null) return null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(ts.getTime());
        return format.format(date);
    }
    
    public static String formatDate(Date d) {
        if (d == null) return null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(d);
    }
}
