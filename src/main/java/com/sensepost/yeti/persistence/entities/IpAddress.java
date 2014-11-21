package com.sensepost.yeti.persistence.entities;

import com.sensepost.yeti.common.EqualityUtil;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Johan Snyman
 */
@Entity
@Table(name = "ip_addresses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class IpAddress extends DatabaseEntity {

    @Column(name = "address", length = 15, nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "footprint_id")
    private Footprint footprint;
    
    @ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        mappedBy = "ipAddresses",
        targetEntity = Host.class
    )
    private List<Host> hosts;

    public IpAddress() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Footprint getFootprint() {
        return footprint;
    }

    public void setFootprint(Footprint footprint) {
        this.footprint = footprint;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IpAddress)) {
            return false;
        }

        final IpAddress ia = (IpAddress) obj;

        return EqualityUtil.equals(ia.getAddress(), getAddress())
                && EqualityUtil.equals(ia.getFootprint(), getFootprint());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + (getAddress() == null ? 0 : getAddress().hashCode());
        result = 27 * result + (getFootprint() == null ? 0 : getFootprint().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Ip Address").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Address: ").append(getAddress()).append(System.lineSeparator());
        str.append("Footprint: ").append(getFootprint() == null ? "" : getFootprint().getName()).append(System.lineSeparator());

        return str.toString();
    }
}
