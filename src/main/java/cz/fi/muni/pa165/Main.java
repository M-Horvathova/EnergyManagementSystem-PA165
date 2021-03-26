package cz.fi.muni.pa165;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import cz.fi.muni.pa165.entity.MeterLog;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
}