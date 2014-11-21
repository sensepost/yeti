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
@Table(name = "ports")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Port extends DatabaseEntity {

    @Column(name = "port_number", nullable = false)
    private Integer portNumber;
    
    @Column(name = "service_name", columnDefinition = "TEXT")
    private String serviceName;
    
    @Column(columnDefinition = "TEXT")
    private String version;
    
    @Column(name = "extra_info", columnDefinition = "TEXT")
    private String extraInfo;
    
    @Column(name = "port_state", columnDefinition = "TEXT")
    private String portState;
    
    @ManyToOne
    @JoinColumn(name = "ip_address_id", nullable = false)
    private IpAddress ipAddress;
    
    public Port() {
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getPortState() {
        return portState;
    }

    public void setPortState(String portState) {
        this.portState = portState;
    }

    public IpAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IpAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Port)) {
            return false;
        }

        final Port p = (Port) obj;

        return EqualityUtil.equals(p.getPortNumber(), getPortNumber())
                && EqualityUtil.equals(p.getServiceName(), getServiceName())
                && EqualityUtil.equals(p.getVersion(), getVersion())
                && EqualityUtil.equals(p.getExtraInfo(), getExtraInfo())
                && EqualityUtil.equals(p.getPortState(), getPortState())
                && EqualityUtil.equals(p.getIpAddress(), getIpAddress());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + ((getPortNumber() == null) ? 0 : getPortNumber().hashCode());
        result = 27 * result + ((getServiceName() == null) ? 0 : getServiceName().hashCode());
        result = 27 * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = 27 * result + ((getExtraInfo() == null) ? 0 : getExtraInfo().hashCode());
        result = 27 * result + ((getPortState() == null) ? 0 : getPortState().hashCode());
        result = 27 * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Port").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Port Number: ").append(getPortNumber()).append(System.lineSeparator());
        str.append("Service Name: ").append(getServiceName()).append(System.lineSeparator());
        str.append("Version: ").append(getVersion()).append(System.lineSeparator());
        str.append("Extra Info: ").append(getExtraInfo()).append(System.lineSeparator());
        str.append("Port State: ").append(getPortState()).append(System.lineSeparator());
        str.append("IP Address: ").append(getIpAddress() == null ? "" : getIpAddress().getAddress()).append(System.lineSeparator());

        return str.toString();
    }
}
