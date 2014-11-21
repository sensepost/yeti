package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.IpAddress;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class IpAddressDao extends DatabaseEntityDao<IpAddress> {

    @Override
    public IpAddress saveOrUpdate(IpAddress e) {
        IpAddress ipa = findByAddressAndFootprint(e.getAddress(), e.getFootprint().getId());
        if (ipa == null) {
            super.saveOrUpdate(e);
            return e;
        }
        return ipa;
    }
    
    public IpAddress findByAddressAndFootprint(String ipAddress, int footprintId) {
        String hql = "from IpAddress where address = :address and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("address", ipAddress);
        query.setParameter("footprintId", footprintId);
        List<IpAddress> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public List<IpAddress> findByFootprint(int footprintId) {
        String hql = "from IpAddress where footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("footprintId", footprintId);
        return query.list();
    }
    
    public List<IpAddress> findByHostAndFootprint(String hostName, int footprintId) {
        String hql = "from IpAddress where hosts.name = :hostName and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("hostName", hostName);
        query.setParameter("footprintId", footprintId);
        return query.list();
    }
    
    public void deleteIpAddressFromFootprint(String ipAddress, int footprintId) {
        String hql = "delete from IpAddress where address = :ipAddress and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("ipAddress", ipAddress);
        query.setParameter("footprintId", footprintId);
        query.executeUpdate();
    }
}
