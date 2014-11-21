package com.sensepost.yeti.persistence.entities;

import com.sensepost.yeti.common.EqualityUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Johan Snyman
 */
@Entity
@Table(name = "domains")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Domain extends DatabaseEntity {

    @Column(name = "domain_name", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String whois;

    @ManyToOne
    @JoinColumn(name = "footprint_id")
    private Footprint footprint;

    public Domain() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhois() {
        return whois;
    }

    public void setWhois(String whois) {
        this.whois = whois;
    }

    public Footprint getFootprint() {
        return footprint;
    }

    public void setFootprint(Footprint footprint) {
        this.footprint = footprint;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Domain)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final Domain d = (Domain) obj;
        return EqualityUtil.equals(d.getName(), getName())
                && EqualityUtil.equals(d.getWhois(), getWhois())
                && EqualityUtil.equals(d.getFootprint(), getFootprint());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + ((getName() == null) ? 0 : getName().hashCode());
        result = 27 * result + ((getWhois() == null) ? 0 : getWhois().hashCode());
        result = 27 * result + ((getFootprint() == null) ? 0 : getFootprint().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Domain:").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Name ").append(getName()).append(System.lineSeparator());
        str.append("Whois: ").append(getWhois()).append(System.lineSeparator());
        str.append("Footrpint: ").append(getFootprint() == null ? "" : getFootprint().getName()).append(System.lineSeparator());

        return str.toString();
    }
}
