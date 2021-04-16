package cz.fi.muni.pa165;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.dao.MeterLogDaoImpl;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.MeterLogServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;



@ContextConfiguration(classes=PersistenceApplicationContext.class)
public class Main {
    private static EntityManagerFactory emf;


     // Playground method for very basic testing if the database works
     public static void playground() {
            AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(PersistenceApplicationContext.class);
            emf = Persistence.createEntityManagerFactory("default");
    }

    public static void main(String[] args) {
        playground();
    }
}
