package com.sensepost.yeti.persistence.entities;

import com.sensepost.yeti.common.EqualityUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Johan Snyman
 */
@Entity
@Table(name = "hosts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Host extends DatabaseEntity {

    @Column(name = "host_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToMany(
            targetEntity = IpAddress.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "hosts_ip_addresses",
            joinColumns = @JoinColumn(name = "host_id"),
            inverseJoinColumns = @JoinColumn(name = "ip_address_id")
    )
    private List<IpAddress> ipAddresses;

    public Host() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public List<IpAddress> getIpAddresses() {
        return ipAddresses;
    }

    private void setIpAddresses(List<IpAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }
    
    
    public void addIpAddress(IpAddress ipAddress) {
        if(ipAddresses == null) {
            ipAddresses = new ArrayList<>();
        }
        ipAddresses.add(ipAddress);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Host)) {
            return false;
        }

        final Host h = (Host) obj;

        return EqualityUtil.equals(h.getName(), getName())
                && EqualityUtil.equals(h.getDomain(), getDomain());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + (getName() == null ? 0 : getName().hashCode());
        result = 27 * result + (getDomain() == null ? 0 : getDomain().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Host").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Name: ").append(getName()).append(System.lineSeparator());
        str.append("Domain: ").append(getDomain() == null ? "" : getDomain().getName()).append(System.lineSeparator());

        return str.toString();
    }
}
