package cz.fi.muni.pa165;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import cz.fi.muni.pa165.entity.MeterLog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;



@ContextConfiguration(classes=PersistenceApplicationContext.class)
public class Main {
    private static EntityManagerFactory emf;

     // Playground method for very basic testing if the database works

    public static void playground() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(PersistenceApplicationContext.class);
        emf = Persistence.createEntityManagerFactory("default");

        EntityManager em = null;

        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            MeterLog ml = new MeterLog();
            ml.setMeasure(123L);
            em.persist(ml);
            em.getTransaction().commit();

            em.getTransaction().begin();
            List<MeterLog> logs = em.createQuery(
                    "select l from MeterLog l order by l.logDate",
                    MeterLog.class).getResultList();
            System.out.println(logs.get(0).getCreateStamp());
            System.out.println(logs.get(0).getLogDate());
            System.out.println(logs.get(0).getLogTime());

            em.getTransaction().commit();

            ml.setMeasure(1L);

            em.getTransaction().begin();
            System.out.println(ml.getCreateStamp());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ml = em.merge(ml);

            System.out.println(ml.getCreateStamp());
            System.out.println(LocalDateTime.now());
            em.getTransaction().commit();

            em.getTransaction().begin();
            List<MeterLog> logs2 = em.createQuery(
                    "select l from MeterLog l order by l.logDate",
                    MeterLog.class).getResultList();
            System.out.println(logs2.get(0).getCreateStamp());

            em.getTransaction().commit();


        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void main(String[] args) {
        playground();
    }
}
