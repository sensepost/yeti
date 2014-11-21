package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.Footprint;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class FootprintDao extends DatabaseEntityDao<Footprint> {

    @Override
    public Footprint saveOrUpdate(Footprint e) {
        Footprint fp = findByName(e.getName());
        if (fp == null) {
            super.saveOrUpdate(e);
            return e;
        }
        return fp;
    }

    public Integer getFootprintId(String name) {
        String hql = "select id from Footprint where name = :name";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("name", name);
        return (Integer) query.uniqueResult();
    }
    
    public Footprint findByName(String name) {
        String hql = "from Footprint where name = :name";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("name", name);
        return (Footprint) query.uniqueResult();
    }
}
