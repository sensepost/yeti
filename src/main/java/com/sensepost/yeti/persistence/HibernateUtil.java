package com.sensepost.yeti.persistence;

import com.sensepost.yeti.common.ConfigSettings;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Johan Snyman
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            Properties dbProps = ConfigSettings.getDatabaseProperties();
            
            Configuration configuration = new Configuration();
            configuration.setProperties(dbProps);
            configuration.configure("hibernate-annotation.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (HibernateException ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionAnnotationFactory();
        }
        return sessionFactory;
    }
    
    public static Session getSession() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionAnnotationFactory();
        }
        
        return sessionFactory.getCurrentSession();
    }
    
    public static void resetSession() {
        sessionFactory = null;
    }
}
