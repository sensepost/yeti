package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.Host;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class HostDao extends DatabaseEntityDao<Host> {

    @Override
    public Host saveOrUpdate(Host e) {
        Host h = findByNameAndDomain(e.getName(), e.getDomain().getId());
        if (h == null) {
            super.saveOrUpdate(e);
            return e;
        }
        return h;
    }
    
    public Host findByNameAndFootprint(String hostName, int footprintId) {
        String hql = "from Host where name = :hostName and domain.footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("hostName", hostName);
        query.setParameter("footprintId", footprintId);
        return (Host) query.uniqueResult();
    }
    
    public Host findByNameAndDomain(String hostName, int domainId) {
        String hql = "from Host where name = :hostName and domain.id = :domainId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("hostName", hostName);
        query.setParameter("domainId", domainId);
        return (Host) query.uniqueResult();
    }

    public List<Host> findByFootprint(int footprintId) {
        String hql = "from Host where domain.footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("footprintId", footprintId);
        return query.list();
    }

    public List<Host> findByIpAddress(String ipAddress) {
        String hql = "from Host where ipAddresses.address = :ipAddress";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("ipAddress", ipAddress);
        return query.list();
    }
    
    public List<Host> findByDomainAndFootprint(String domainName, int footprintId) {
        String hql = "from Host where domain.name = :domainName and domain.footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("domainName", domainName);
        query.setParameter("footprintId", footprintId);
        return query.list();
    }
    
    public void deleteHost(String hostName, int footprintId) {
        String hql = "delete from Host where name = :hostName and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("hostName", hostName);
        query.setParameter("footprintId", footprintId);
        query.executeUpdate();
    }
}
