package cz.fi.muni.pa165;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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
