package com.sensepost.yeti.persistence.dao;

import com.sensepost.yeti.persistence.HibernateUtil;
import static com.sensepost.yeti.persistence.HibernateUtil.getSession;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Johan Snyman
 * @param <E> The <code>DatabaseEntity</code> type of the database table.
 */
public class DatabaseEntityDao<E extends Serializable> {

    private final Class<E> persistentClass;

    public DatabaseEntityDao() {
        this.persistentClass = (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public E findById(Integer id) {
        return (E) HibernateUtil.getSession().get(persistentClass, id);
    }

    public List<E> findAll() {
        return HibernateUtil.getSession().createCriteria(persistentClass).list();
    }

    public E saveOrUpdate(E e) {
        try {
            HibernateUtil.getSession().saveOrUpdate(e);
            return e;
        } catch (ConstraintViolationException cve) {
            Logger.getLogger("saveOrUpdate").log(Level.INFO,
                    "The entity will not be persisted as a duplicate already exists.", cve);
        }
        return null;
    }

    public void delete(E e) {
        HibernateUtil.getSession().delete(e);
    }

    public List findByCriteria(Criterion criterion) {
        Criteria criteria = HibernateUtil.getSession().createCriteria(persistentClass);
        criteria.add(criterion);
        return criteria.list();
    }

    public int truncate() {
        Session s = getSession();
        int rowsAffected = 0;
        try {
            Class c = getPersistentClass();
            String hql = "delete from " + c.getSimpleName();
            Query q = s.createQuery(hql);
            rowsAffected = q.executeUpdate();
        } catch (HibernateException e) {
            Logger.getLogger("truncate").log(Level.INFO,
                    "Could not clear the table.", e);
        }
        return rowsAffected;
    }

    public Class<E> getPersistentClass() {
        return persistentClass;
    }
}
