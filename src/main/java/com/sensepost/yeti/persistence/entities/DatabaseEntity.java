package com.sensepost.yeti.persistence.entities;

import com.sensepost.yeti.common.EqualityUtil;
import com.sensepost.yeti.common.FormatUtil;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Johan Snyman
 */
@MappedSuperclass
public class DatabaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    public DatabaseEntity() {
        id = -1;
        created = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DatabaseEntity)) {
            return false;
        }

        final DatabaseEntity de = (DatabaseEntity) obj;
        return EqualityUtil.equals(de.getId(), getId())
                && EqualityUtil.equals(de.getCreated(), getCreated());
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 27 * result + ((id == null) ? 0 : id);
        result = 27 * result + ((created == null) ? 0 : created.hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Id: ").append(id).append(System.lineSeparator());
        str.append("Created: ").append(FormatUtil.formatDate(created)).append(System.lineSeparator());

        return str.toString();
    }
}
