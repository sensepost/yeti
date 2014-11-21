package com.sensepost.yeti.persistence.entities.config;

import com.sensepost.yeti.persistence.entities.DatabaseEntity;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Generic configuration property to be stored in the database.
 * @author Johan Snyman
 */
@Entity
@Table(name = "configuration_properties")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ConfigurationProperty extends DatabaseEntity {
    
    private String propertyName;
    private String propertyValue;
}
