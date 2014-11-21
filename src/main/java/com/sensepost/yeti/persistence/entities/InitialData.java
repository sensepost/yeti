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
@Table(name = "initial_data")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class InitialData extends DatabaseEntity {

    public InitialData() {
    }

    @Column(name = "data_type", length = 100, nullable = false)
    private String type;

    @Column(name = "data_value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "footprint_id")
    private Footprint footprint;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        if (!(obj instanceof DatabaseEntity)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        final InitialData id = (InitialData) obj;
        return EqualityUtil.equals(id.getFootprint(), getFootprint())
                && EqualityUtil.equals(id.getType(), getType())
                && EqualityUtil.equals(id.getValue(), getValue());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + ((getType() == null) ? 0 : getType().hashCode());
        result = 27 * result + ((getValue() == null) ? 0 : getValue().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Initial Data:").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Type: ").append(getType()).append(System.lineSeparator());
        str.append("Value: ").append(getValue()).append(System.lineSeparator());

        return str.toString();
    }
}
