package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.Domain;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class DomainDao extends DatabaseEntityDao<Domain> {

    @Override
    public Domain saveOrUpdate(Domain e) {
        Domain d = findByName(e.getName(), e.getFootprint().getId());
        if (d == null) {
            super.saveOrUpdate(e);
            return e;
        }
        return d;
    }

    public Domain findByName(String domainName, int footprintId) {
        String hql = "from Domain where name = :domainName and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("domainName", domainName);
        query.setParameter("footprintId", footprintId);
        List<Domain> list = query.list();
        if (!list.isEmpty() && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }
    
    public List<Domain> findByFootprint(int footprintId) {
        String hql = "from Domain where footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("footprintId", footprintId);
        return query.list();
    }
    
    public void deleteDomain(String domainName, int footprintId) {
        String hql = "delete from Domain where name = :domainName and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("domainName", domainName);
        query.setParameter("footprintId", footprintId);
        query.executeUpdate();
    }
}
