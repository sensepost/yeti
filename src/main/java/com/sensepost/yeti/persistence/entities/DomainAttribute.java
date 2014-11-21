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
@Table(name = "domain_attributes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DomainAttribute extends DatabaseEntity {

    @Column(name = "attr_key", nullable = false)
    private String key;

    @Column(name = "attr_value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;

    public DomainAttribute() {
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

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DomainAttribute)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final DomainAttribute da = (DomainAttribute) obj;
        return EqualityUtil.equals(da.getKey(), getKey())
                && EqualityUtil.equals(da.getValue(), getValue())
                && EqualityUtil.equals(da.getDomain(), getDomain());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + ((getKey() == null) ? 0 : getKey().hashCode());
        result = 27 * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = 27 * result + ((getDomain() == null) ? 0 : getDomain().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Domain Attrribute:").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Attribute Key ").append(getKey()).append(System.lineSeparator());
        str.append("Atribute Value: ").append(getValue()).append(System.lineSeparator());
        str.append("Domain: ").append(getDomain() == null ? "" : getDomain().getName()).append(System.lineSeparator());

        return str.toString();
    }
}
