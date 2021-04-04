package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.UserRole;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Martin Podhora
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class SmartMeterTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SmartMeterDao smartMeterDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Address a1;
    private House h1;
    private MeterLog ml1;
    private MeterLog ml2;
    private SmartMeter sm1;

    @BeforeMethod
    public void Init() {
        a1 = new Address();
        a1.setCity("ABC");
        a1.setCountry("DEF");
        a1.setStreetName("GHI");

        h1 = new House();
        h1.setAddress(a1);
        h1.setName("ABCDEFGHI");
        h1.setRunning(true);

        sm1 = new SmartMeter();
        sm1.setHouse(h1);
        sm1.setCumulativePowerConsumption(100);
        sm1.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 23), LocalTime.of(16, 30)));
        sm1.setPowerConsumptionSinceLastLog(100);
        sm1.setRunning(true);

        ml1 = new MeterLog();
        ml1.setLogDate(LocalDate.of(2021, 1, 23));
        ml1.setLogTime(LocalTime.of(15, 30));
        ml1.setMeasure(123L);
        ml1.setSmartMeter(sm1);

        ml2 = new MeterLog();
        ml2.setLogDate(LocalDate.of(2021, 1, 23));
        ml2.setLogTime(LocalTime.of(16, 30));
        ml2.setMeasure(123L);
        ml1.setSmartMeter(sm1);

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(a1);
            em.persist(h1);
            em.persist(sm1);
            em.persist(ml1);
            em.persist(ml2);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @AfterMethod
    public void afterTest() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from MeterLog").executeUpdate();
            em.createQuery("delete from SmartMeter").executeUpdate();
            em.createQuery("delete from House").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void basicCreateTestNoHouse() {
        SmartMeter smBCT = new SmartMeter();
        smBCT.setCumulativePowerConsumption(0);
        smBCT.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 31), LocalTime.of(19, 30)));
        smBCT.setPowerConsumptionSinceLastLog(0);
        smBCT.setRunning(false);

        smartMeterDao.create(smBCT);
    }

    @Test
    public void advancedWithRelationshipsCreateTest() {
        SmartMeter smBCT = new SmartMeter();
        smBCT.setHouse(h1);
        smBCT.setCumulativePowerConsumption(0);
        smBCT.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 31), LocalTime.of(19, 30)));
        smBCT.setPowerConsumptionSinceLastLog(0);
        smBCT.setRunning(false);

        smartMeterDao.create(smBCT);

        Assert.assertTrue(smBCT.getId() > 0);
        SmartMeter result = findEntityInDb(smBCT.getId());

        Assert.assertNotNull(result);
        Assert.assertTrue(result.getHouse().getName().equals("ABCDEFGHI"));
        Assert.assertEquals(result.getCumulativePowerConsumption(), 0.);
        Assert.assertEquals(result.getLastLogTakenAt(), LocalDateTime.of(LocalDate.of(2021, 1, 31), LocalTime.of(19, 30)));
        Assert.assertEquals(result.getPowerConsumptionSinceLastLog(), 0.0);
        Assert.assertEquals(result.isRunning(), false);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void updateNoHouseTest() {
        sm1.setHouse(null);
        smartMeterDao.update(sm1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setFutureDateTest() {
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        SmartMeter smNHT = new SmartMeter();
        smNHT.setCumulativePowerConsumption(0);
        smNHT.setLastLogTakenAt(date);
        smNHT.setPowerConsumptionSinceLastLog(0);
        smNHT.setRunning(false);
    }

    @Test
    public void basicUpdateTest() {
        SmartMeter sm = createEntitySmartMeter();

        Assert.assertTrue(!sm.isRunning());

        LocalDateTime now = LocalDateTime.now();
        SmartMeter result = findEntityInDb(sm.getId());
        result.setRunning(true);
        result.setLastLogTakenAt(now);
        result.setPowerConsumptionSinceLastLog(10000);
        result.setCumulativePowerConsumption(1000000);
        smartMeterDao.update(result);

        SmartMeter smUpdated = findEntityInDb(result.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(smUpdated.getCumulativePowerConsumption(), 1000000.0);
        Assert.assertEquals(smUpdated.getLastLogTakenAt(), now);
        Assert.assertEquals(smUpdated.getPowerConsumptionSinceLastLog(), 10000.0);
        Assert.assertTrue(smUpdated.isRunning());
    }

    @Test
    public void findByIdTest() {
        SmartMeter smGot = smartMeterDao.findById(sm1.getId());

        Assert.assertEquals(sm1.getLastLogTakenAt(), smGot.getLastLogTakenAt());
        Assert.assertEquals(sm1.getCumulativePowerConsumption(), smGot.getCumulativePowerConsumption());
        Assert.assertEquals(sm1.isRunning(), smGot.isRunning());
        Assert.assertEquals(sm1.getPowerConsumptionSinceLastLog(), smGot.getPowerConsumptionSinceLastLog());
    }

    @Test
    public void findNonExistingByIdTest() {
        SmartMeter smGot = smartMeterDao.findById(0l);

        Assert.assertNull(smGot);
  }

    @Test
    public void findAllTest() {
        List<SmartMeter> smGot = smartMeterDao.findAll();

        Assert.assertEquals(smGot.size(), 1);
        Assert.assertEquals(smGot.get(0).getId(), sm1.getId());
   }

    @Test
    public void findEmptyAllTest() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from MeterLog").executeUpdate();
            em.createQuery("delete from SmartMeter").executeUpdate();
            em.createQuery("delete from House").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        List<SmartMeter> smGot = smartMeterDao.findAll();

        Assert.assertEquals(smGot.size(), 0);
    }

    @Test
    public void findByRunning() {

        List<SmartMeter> smGot = smartMeterDao.findByRunning(true);

        Assert.assertEquals(smGot.size(), 1);
        Assert.assertTrue(smGot.get(0).isRunning());
    }

    @Test
    public void findByNonRunning() {
        createEntitySmartMeter();
        List<SmartMeter> smGot = smartMeterDao.findByRunning(false);

        Assert.assertEquals(smGot.size(), 1);
        Assert.assertTrue(!smGot.get(0).isRunning());
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void deleteExistingFKValidation() {
        smartMeterDao.delete(sm1);
    }

    @Test
    public void deleteExistingNoFKValidation() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from MeterLog").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        smartMeterDao.delete(sm1);

        SmartMeter smGot = findEntityInDb(sm1.getId());
        Assert.assertNull(smGot);
    }

    @Test(expectedExceptions = JpaObjectRetrievalFailureException.class)
    public void deleteNonExisting() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from MeterLog").executeUpdate();
            em.createQuery("delete from SmartMeter").executeUpdate();
            em.createQuery("delete from House").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        smartMeterDao.delete(sm1);
    }

    private SmartMeter findEntityInDb(Long id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            SmartMeter result = em.find(SmartMeter.class, id);
            em.getTransaction().commit();

            return result;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private SmartMeter createEntitySmartMeter() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            SmartMeter sm = new SmartMeter();
            sm.setHouse(h1);
            sm.setCumulativePowerConsumption(0);
            sm.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 15), LocalTime.of(5, 00)));
            sm.setPowerConsumptionSinceLastLog(0);
            sm.setRunning(false);
            em.persist(sm);

            em.getTransaction().commit();
            return sm;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
