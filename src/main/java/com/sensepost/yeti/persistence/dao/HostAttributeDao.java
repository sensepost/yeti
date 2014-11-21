package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.HostAttribute;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class HostAttributeDao extends DatabaseEntityDao<HostAttribute> {

    @Override
    public HostAttribute saveOrUpdate(HostAttribute e) {
        HostAttribute ha = findByKeyAndHost(e.getKey(), e.getHost().getId());
        if (ha == null) {
            super.saveOrUpdate(e);
            return e;
        }
        return ha;
    }
    
    public List<HostAttribute> findByHostName(String hostName) {
        String hql = "from HostAttribute where host.name = :hostName";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("hostName", hostName);
        return query.list();
    }
    
    public HostAttribute findByKeyAndHost(String key, int hostId) {
        String hql = "from HostAttribute where key = :key and host.id = :hostId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("key", key);
        query.setParameter("hostId", hostId);
        List<HostAttribute> list =  query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public void deleteHostAtrribute(String hostName, String key, String value, int footprintId) {
        String hql = "delete from HostAttribute where host.name = :hostName and key = : key and value = :value and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("hostName", hostName);
        query.setParameter("key", key);
        query.setParameter("value", value);
        query.setParameter("footprintId", footprintId);
        query.executeUpdate();
    }
}
