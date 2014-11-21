package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.entities.InitialData;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Johan Snyman
 */
public class InitialDataDao extends DatabaseEntityDao<InitialData> {

    public List<InitialData> findByTypeAndFootprintId(String type, int footprintId) {
        String hql = "from InitialData where type = :type and footprint.id = :footprintId";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("type", type);
        query.setParameter("footprintId", footprintId);
        return query.list();
    } 
}
