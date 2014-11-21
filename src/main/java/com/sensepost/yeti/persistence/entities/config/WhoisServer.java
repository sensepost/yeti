package com.sensepost.yeti.persistence.entities.config;

import com.sensepost.yeti.common.EqualityUtil;
import com.sensepost.yeti.persistence.entities.DatabaseEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Johan Snyman
 */
@Entity
@Table(name = "whois_servers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class WhoisServer extends DatabaseEntity {
    @Column(name = "server_url", nullable = false)
    private String serverUrl;
    @Column(name = "regex", nullable = false)
    private String regex;
    @Column(name = "query_format", nullable = false, length = 100)
    private String queryFormat;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "server_type", nullable = false)
    private ServerType serverType;
    
    @OneToMany(mappedBy = "")
    private Set<ServerOption> serverOptions;
    
    public enum ServerType {
        TLD, 
        IPV4, 
        IPV6;
        
        private ServerType() {
        }
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getQueryFormat() {
        return queryFormat;
    }

    public void setQueryFormat(String queryFormat) {
        this.queryFormat = queryFormat;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public Set<ServerOption> getServerOptions() {
        return serverOptions;
    }

    public void setServerOptions(Set<ServerOption> serverOptions) {
        this.serverOptions = serverOptions;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WhoisServer)) {
            return false;
        }

        final WhoisServer ws = (WhoisServer) obj;

        return EqualityUtil.equals(ws.getQueryFormat(), getQueryFormat())
                && EqualityUtil.equals(ws.getRegex(), getRegex())
                && EqualityUtil.equals(ws.getServerType(),getServerType())
                && EqualityUtil.equals(ws.getServerUrl(), getServerUrl());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 27 * result + (getQueryFormat() == null ? 0 : getQueryFormat().hashCode());
        result = 27 * result + (getRegex() == null ? 0 : getRegex().hashCode());
        result = 27 * result + (getServerType() == null ? 0 : getServerType().hashCode());
        result = 27 * result + (getServerUrl() == null ? 0 : getServerUrl().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Whois Server").append(System.lineSeparator());
        str.append(super.toString());
        str.append("Query format: ").append(getQueryFormat()).append(System.lineSeparator());
        str.append("Regular expression: ").append(getRegex() == null ? "" : getRegex()).append(System.lineSeparator());
        str.append("Server type: ").append(getServerType() == null ? "" : getServerType()).append(System.lineSeparator());
        str.append("Server URL: ").append(getServerUrl() == null ? "" : getServerUrl()).append(System.lineSeparator());

        return str.toString();
    }
}
