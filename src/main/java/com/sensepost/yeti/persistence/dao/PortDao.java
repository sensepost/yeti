package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.Port;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class PortDao extends DatabaseEntityDao<Port> {

    @Override
    public Port saveOrUpdate(Port e) {
        Port p = findByPortAndIpAddress(e.getPortNumber(), e.getIpAddress().getId());
        if (p == null) {
            super.saveOrUpdate(e);
            return e;
        }
        return p;
    }
    
    public Port findByPortAndIpAddress(Integer portNumber, int ipAddressId) {
        String hql = "from Port where portNumber = :portNumber and ipAddress.id = :ipAddressId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("portNumber", portNumber);
        query.setParameter("ipAddressId", ipAddressId);
        List<Port> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<Port> findPortsByIpAddressAndFootprint(String ipAddress, int footprintId) {
        String hql = "from Port where ipAddress.address = :ipAddress and ipAddress.footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("ipAddress", ipAddress);
        query.setParameter("footprintId", footprintId);
        return query.list();
    }
    
    public void deletePortFromIp(String ipAddress, int portNumber, int footprintId) {
        String hql = "delete from Port where portNumber = :portNumber and ipAddress.address = :ipAddress and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("portNumber", portNumber);
        query.setParameter("ipAddress", ipAddress);
        query.setParameter("footprintId", footprintId);
        query.executeUpdate();
    }
}
