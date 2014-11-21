CREATE TABLE footprints ( 
    id              INTEGER PRIMARY KEY AUTO_INCREMENT,
    footprint_name  VARCHAR(255) NOT NULL,
    created         TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    UNIQUE (footprint_name)
);
CREATE TABLE initial_data ( 
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    data_type      VARCHAR(100),
    data_value     VARCHAR(255),
    created        TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    footprint_id   INTEGER NOT NULL REFERENCES footprints (id)
);
CREATE TABLE domains ( 
    id              INTEGER PRIMARY KEY AUTO_INCREMENT,
    domain_name     VARCHAR(255) NOT NULL,
    whois           TEXT,
    created         TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    footprint_id    INTEGER NOT NULL REFERENCES footprints (id),
    UNIQUE (domain_name, footprint_id)
);
CREATE TABLE domain_attributes ( 
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    attr_key       VARCHAR(255) NOT NULL,
    attr_value     VARCHAR(255) NOT NULL,
    created        TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    domain_id      INTEGER NOT NULL REFERENCES domains (id),
    UNIQUE (attr_value, domain_id)
);
CREATE TABLE ip_addresses ( 
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    address        VARCHAR(15) NOT NULL UNIQUE,
    created        TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    host_id        INTEGER REFERENCES hosts (id),
    footprint_id   INTEGER REFERENCES footprints (id),
    UNIQUE (address, footprint_id)
);
CREATE TABLE hosts ( 
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    host_name      VARCHAR(255) NOT NULL UNIQUE,
    created        TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    domain_id      INTEGER NOT NULL REFERENCES domains (id),
    ip_address_id  INTEGER NOT NULL REFERENCES ip_address (id),
    UNIQUE (host_name, domain_id)
);
CREATE TABLE host_attributes (
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    attr_key       VARCHAR(255) NOT NULL,
    attr_value     VARCHAR(255) NOT NULL,
    attr_type      VARCHAR(100) DEFAULT 'info',
    created        TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    host_id        INTEGER NOT NULL REFERENCES hosts (id),
    UNIQUE (attr_key, host_id)
);
CREATE TABLE hosts_ip_addresses (
    host_id        INTEGER NOT NULL REFERENCES hosts (id),
    ip_address_id  INTEGER NOT NULL REFERENCES ip_addresses (id),
    PRIMARY KEY (host_id, ip_address_id)
);
CREATE TABLE ports ( 
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    port_number    INTEGER,
    service_name   TEXT,
    version        TEXT,
    extra_info     TEXT,
    port_state     TEXT,
    created        TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    ip_address_id  INTEGER NOT NULL REFERENCES ip_addresses (id),
    UNIQUE (port_number, ip_address_id)
);
CREATE TABLE whois_servers (
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    server_url     VARCHAR(255) NOT NULL,
    regex          VARCHAR(255) NOT NULL,
    query_format   VARCHAR(100) NOT NULL,
    server_type    INTEGER NOT NULL DEFAULT 1,
    UNIQUE (port_number, ip_address_id)
);
CREATE TABLE server_options (
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    option_name    VARCHAR(255) NOT NULL,
    option_value   VARCHAR(255) NOT NULL,
    server_id      INTEGER NOT NULL REFERENCES whois_server (id),
    UNIQUE (port_number, ip_address_id)
);
CREATE TABLE configuration_properties (
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    property_name  VARCHAR(255) NOT NULL,
    property_value VARCHAR(255) NOT NULL,
    UNIQUE (property_name)
);
