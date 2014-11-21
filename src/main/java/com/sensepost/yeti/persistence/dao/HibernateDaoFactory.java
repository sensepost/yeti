package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.dao.config.ServerOptionDao;
import com.sensepost.yeti.persistence.dao.config.WhoisServerDao;

/**
 *
 * @author Johan Snyman
 */
public class HibernateDaoFactory {
    
    private static HibernateDaoFactory instance;
    
    private HibernateDaoFactory() {
    }
    
    public static HibernateDaoFactory getInstance() {
        if (instance == null) {
            instance = new HibernateDaoFactory();
        }
        return instance;
    }

    public DomainDao getDomainDao() {
        return (DomainDao) instantiateDAO(DomainDao.class);
    }
    
    public DomainAttributeDao getDomainAttributeDao() {
        return (DomainAttributeDao) instantiateDAO(DomainAttributeDao.class);
    }
    
    public FootprintDao getFootprintDao() {
        return (FootprintDao) instantiateDAO(FootprintDao.class);
    }
    
    public HostDao getHostDao() {
        return (HostDao) instantiateDAO(HostDao.class);
    }
    
    public HostAttributeDao getHostAttributeDao() {
        return (HostAttributeDao) instantiateDAO(HostAttributeDao.class);
    }
    
    public InitialDataDao getInitialDataDao() {
        return (InitialDataDao) instantiateDAO(InitialDataDao.class);
    }
    
    public IpAddressDao getIpAddressDao() {
        return (IpAddressDao) instantiateDAO(IpAddressDao.class);
    }
    
    public PortDao getPortDao() {
        return (PortDao) instantiateDAO(PortDao.class);
    }
    
    public ServerOptionDao getServerOptionDao() {
        return (ServerOptionDao) instantiateDAO(ServerOptionDao.class);
    }
    
    public WhoisServerDao getWhoisServerDao() {
        return (WhoisServerDao) instantiateDAO(WhoisServerDao.class);
    }
    
    private DatabaseEntityDao instantiateDAO(Class daoClass) {  
        try {  
            DatabaseEntityDao dao = (DatabaseEntityDao)daoClass.newInstance();  
            return dao;  
        } catch (IllegalAccessException | InstantiationException ex) {  
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);  
        }  
    }
}
