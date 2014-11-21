package com.sensepost.yeti.persistence.dao.config;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.dao.DatabaseEntityDao;
import com.sensepost.yeti.persistence.entities.config.WhoisServer;
import org.hibernate.Query;
/**
 *
 * @author Johan Snyman
 */
public class WhoisServerDao extends DatabaseEntityDao<WhoisServer> {
    
    public WhoisServer findByRegex(String regex) {
        String hql = "from WhoisServer where regex = :regex";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("regex", regex);
        return (WhoisServer) query.uniqueResult();
    }
}
