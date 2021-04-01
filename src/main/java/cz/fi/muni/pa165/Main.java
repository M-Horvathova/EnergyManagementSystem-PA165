package cz.fi.muni.pa165;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.PortalUserDaoImpl;
import cz.fi.muni.pa165.entity.MeterLog;

import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.generators.PortalUserGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.repository.support.Repositories;

public class Main {
    // Playgroung method for very basic testing if the database works

    public static void playground() {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            MeterLog ml = new MeterLog();
            ml.setLogDate(LocalDate.of(2021, 1, 13).atTime(1, 30, 0));
            ml.setMeasure(123L);
            em.persist(ml);

            em.getTransaction().commit();

            em.getTransaction().begin();
            List<MeterLog> logs = em.createQuery(
                    "select l from MeterLog l order by l.logDate",
                    MeterLog.class).getResultList();
            System.out.println(logs.get(0).getLogDate());
            em.getTransaction().commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);

        playground();
        System.out.println("Hello World");
    }

//    private static void initDBWithUsers() {
//        PortalUserDao portalUserDao = new PortalUserDaoImpl();
//        PortalUser pu1 = PortalUserGenerator.generateNewUser();
//        PortalUser pu2 = PortalUserGenerator.generateNewUser();
//
//        portalUserDao.create(pu1);
//        portalUserDao.create(pu2);
//        List<PortalUser> puList = portalUserDao.findAll();
//        PortalUser pu1Retrieved = portalUserDao.findByUserName(pu1.getEmail());
//    }
}
