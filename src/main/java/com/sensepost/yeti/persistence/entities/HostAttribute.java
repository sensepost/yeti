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
@Table(name = "host_attributes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class HostAttribute extends DatabaseEntity {

    @Column(name = "attr_key", nullable = false)
    private String key;

    @Column(name = "attr_value", nullable = false)
    private String value;

    @Column(name = "attr_type", length = 100, nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'info'")
    private String type;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private Host host;

    public HostAttribute() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HostAttribute)) {
            return false;
        }

        final HostAttribute ha = (HostAttribute) obj;

        return EqualityUtil.equals(ha.getKey(), getKey())
                && EqualityUtil.equals(ha.getValue(), getValue())
                && EqualityUtil.equals(ha.getType(), getType())
                && EqualityUtil.equals(ha.getHost(), getHost());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + (getKey() == null ? 0 : getKey().hashCode());
        result = 27 * result + (getValue() == null ? 0 : getValue().hashCode());
        result = 27 * result + (getType() == null ? 0 : getType().hashCode());
        result = 27 * result + (getHost() == null ? 0 : getHost().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Host Attribute").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Attribute Key: ").append(getKey()).append(System.lineSeparator());
        str.append("Attribute  Value: ").append(getValue()).append(System.lineSeparator());
        str.append("Attribute Type: ").append(getType()).append(System.lineSeparator());
        str.append("Host: ").append(getHost() == null ? "" : getHost().getName()).append(System.lineSeparator());

        return str.toString();
    }
}