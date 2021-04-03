package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class HouseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HouseDao houseDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @AfterMethod
    public void afterTest() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from House ").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    public void basicCreateTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
    }


}
