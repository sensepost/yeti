package com.sensepost.yeti;

import com.sensepost.yeti.persistence.HibernateUtil;
import com.sensepost.yeti.persistence.dao.FootprintDao;
import com.sensepost.yeti.persistence.dao.HibernateDaoFactory;
import com.sensepost.yeti.persistence.entities.Footprint;
import java.util.List;
import org.hibernate.Transaction;

/**
 *
 * @author Johan Snyman
 */
public class HibernateAnnotationMain {

    public static void main(String[] args) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();
            
            Footprint fp1 = new Footprint();
            fp1.setName("Footprint1");
            
            Footprint fp2 = new Footprint();
            fp2.setName("Footprint2");
            
            Footprint fp3 = new Footprint();
            fp3.setName("Footprint3");
            
            FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
            fpDao.saveOrUpdate(fp1);
            fpDao.saveOrUpdate(fp2);
            fpDao.saveOrUpdate(fp3);
            
            tx.commit();
            
            tx = HibernateUtil.getSession().beginTransaction();
            
            fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
            List<Footprint> footprints = fpDao.findAll();
            
            System.out.println("Footprints found:");
            for (Footprint f : footprints) {
                System.out.println(f);
            }
            
            tx.commit();
        } catch (RuntimeException re) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("EXCEPTION CAUGHT!!!!!!!!!!!!!");
            re.printStackTrace();
        } finally {
            System.out.println("FINISHED");
            System.exit(0);
        }
    }
}
