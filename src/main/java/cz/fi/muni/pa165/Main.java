package cz.fi.muni.pa165;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
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
            ml.setCreateStamp(LocalDateTime.of(2021, 3, 12, 15, 15));
            em.persist(ml);
            MeterLog ml1 = new MeterLog();
            ml1.setMeasure(123L);
            ml1.setCreateStamp(LocalDateTime.of(2021, 3, 12, 3, 15));
            em.persist(ml1);
            em.getTransaction().commit();

            em.getTransaction().begin();
            List<MeterLog> logs = em.createQuery(
                    "select l from MeterLog l order by l.logDate",
                    MeterLog.class).getResultList();

            logs.forEach(m-> System.out.println(m.isWithinDayTime(DayTime.Night)));

            logs.removeIf(m -> !m.isWithinDayTime(DayTime.Day));
            System.out.println(logs.size());
            System.out.println(logs.get(0).getLogTime());

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
