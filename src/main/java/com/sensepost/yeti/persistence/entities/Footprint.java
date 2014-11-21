package com.sensepost.yeti.persistence.entities;

import com.sensepost.yeti.common.EqualityUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author Johan Snyman
 */
@Entity
@Table(name = "footprints")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Footprint extends DatabaseEntity {

    @Column(name = "footprint_name", nullable = false)
    private String name;

    public Footprint() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DatabaseEntity)) {
            return false;
        }

        final Footprint f = (Footprint) obj;

        return EqualityUtil.equals(f.getName(), getName());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + ((getName() == null) ? 0 : getName().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Footprint").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Name: ").append(getName()).append(System.lineSeparator());

        return str.toString();
    }
}
