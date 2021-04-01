package cz.fi.muni.pa165;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
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
            ml.setLogDate(LocalDate.of(2021, 1, 23));
            ml.setLogTime(LocalTime.of(15, 30));
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
        playground();
    }
}
