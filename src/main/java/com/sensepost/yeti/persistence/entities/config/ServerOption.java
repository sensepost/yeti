package com.sensepost.yeti.persistence.entities.config;

import com.sensepost.yeti.common.EqualityUtil;
import com.sensepost.yeti.persistence.entities.DatabaseEntity;
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
@Table(name = "server_options")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ServerOption extends DatabaseEntity {
    
    @Column(name = "option_name", nullable = false)
    private String optionName;
    
    @Column(name = "option_value", nullable = false)
    private String optionValue;
    
    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private WhoisServer server;

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public WhoisServer getServer() {
        return server;
    }

    public void setServer(WhoisServer server) {
        this.server = server;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServerOption)) {
            return false;
        }

        final ServerOption so = (ServerOption) obj;

        return EqualityUtil.equals(so.getOptionName(),getOptionName())
                && EqualityUtil.equals(so.getOptionValue(), getOptionValue())
                && EqualityUtil.equals(so.getServer(), getServer());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + (getOptionName() == null ? 0 : getOptionName().hashCode());
        result = 27 * result + (getOptionValue() == null ? 0 : getOptionValue().hashCode());
        result = 27 * result + (getServer() == null ? 0 : getServer().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Server Option").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Option name: ").append(getOptionName()).append(System.lineSeparator());
        str.append("Option value: ").append(getOptionValue()).append(System.lineSeparator());

        return str.toString();
    }
}
