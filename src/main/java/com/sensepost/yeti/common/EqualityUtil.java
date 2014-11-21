package com.sensepost.yeti.common;

import com.sensepost.yeti.persistence.entities.DatabaseEntity;

/**
 *
 * @author Johan Snyman
 */
public class EqualityUtil {

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj2 == null; 
        } else {
            return obj1.equals(obj2);
        }
    }
    
    public static boolean equals(DatabaseEntity obj1, DatabaseEntity obj2) {
        if (obj1 == null) {
            return obj2 == null; 
        } else {
            return equals(obj1.getId(), obj2.getId());
        }
    }
}
