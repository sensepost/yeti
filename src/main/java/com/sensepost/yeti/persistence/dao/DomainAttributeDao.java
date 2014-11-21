package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.DomainAttribute;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class DomainAttributeDao extends DatabaseEntityDao<DomainAttribute> {

    @Override
    public DomainAttribute saveOrUpdate(DomainAttribute e) {
        DomainAttribute da = findByAttributeValueAndDomain(e.getValue(), e.getDomain().getId());
        if (da == null) {
            super.saveOrUpdate(e);
            return e;
        }
        return da;
    }

    public DomainAttribute findByAttributeValueAndDomain(String value, int domainId) {
        String hql = "from DomainAttribute where value = :value and domain.id = :domainId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("value", value);
        query.setParameter("domainId", domainId);
        List<DomainAttribute> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<DomainAttribute> findByDomainNameAndFootprint(String domainName, int footprintId) {
        String hql = "from DomainAttribute where domain.name = :domainName and footprint_id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("domainName", domainName);
        query.setParameter("footprintId", footprintId);
        return query.list();
    }
    
    public void deleteDomainAtrribute(String domainName, String key, String value) {
        String hql = "delete from DomainAttribute where domain.name = :domainName and key = : key and value = :value";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("domainName", domainName);
        query.setParameter("key", key);
        query.setParameter("value", value);
        query.executeUpdate();
    }
}
