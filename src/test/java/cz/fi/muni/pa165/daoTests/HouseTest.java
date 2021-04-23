package cz.fi.muni.pa165.daoTests;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HouseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HouseDao houseDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void basicCreateTest() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(true);

        houseDao.create(house);
        House result = findEntityInDb(house.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(house.getName(), result.getName());
        Assert.assertEquals(house.getRunning(), result.getRunning());
        Assert.assertEquals(result.getAddress(), house.getAddress());
    }

    @Test
    public void createWithSmartMeter() {
        Address address = createValidAddress("Brno");
        SmartMeter smartMeter = createValidSmartMeter();
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(true);
        house.addSmartMeter(smartMeter);
        houseDao.create(house);
        updateSmartMeter(smartMeter);

        List<House> result = em.createQuery("SELECT h FROM House h LEFT JOIN FETCH h.smartMeters WHERE h.id=:id",
                House.class).setParameter("id", house.getId()).getResultList();

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1);
        House resultHouse = result.get(0);
        Assert.assertNotNull(resultHouse);
        Assert.assertEquals(resultHouse.getName(), house.getName());
        Assert.assertEquals(resultHouse.getSmartMeters().size(), house.getSmartMeters().size(),
                "Matching number of smart meters expected");

    }

    @Test
    public void updateWithSmartMeter() {
        Address address = createValidAddress("Brno");
        SmartMeter smartMeter = createValidSmartMeter();
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(true);
        house.addSmartMeter(smartMeter);
        houseDao.create(house);
        updateSmartMeter(smartMeter);


        smartMeter.setPowerConsumptionSinceLastLog(3D);
        houseDao.update(house);
        updateSmartMeter(smartMeter);

        List<House> resultUpdate = em.createQuery("SELECT h FROM House h LEFT JOIN FETCH h.smartMeters WHERE h.id=:id",
                House.class).setParameter("id", house.getId()).getResultList();

        Assert.assertNotNull(resultUpdate);
        Assert.assertEquals(resultUpdate.size(), 1);
        House resultHouse = resultUpdate.get(0);

        Assert.assertNotNull(resultHouse);
        Assert.assertNotNull(resultHouse.getSmartMeters());
        Assert.assertEquals(resultHouse.getSmartMeters().size(), 1);
        Assert.assertTrue(resultHouse.getSmartMeters().contains(smartMeter));
    }

    @Test
    public void updateHouseName() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(false);
        houseDao.create(house);
        house.setName("New name");
        houseDao.update(house);

        House result = findEntityInDb(house.getId());
        Assert.assertEquals(result.getName(), house.getName());
        Assert.assertEquals(result.getName(), "New name");
    }

    @Test
    public void updateHouseAddress() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(false);
        houseDao.create(house);
        Address addressNew = createValidAddress("Praha");
        house.setAddress(addressNew);
        houseDao.update(house);

        House result = findEntityInDb(house.getId());
        Assert.assertEquals(result.getName(), house.getName());
        Assert.assertEquals(result.getAddress(), addressNew);
    }



    @Test
    public void deleteHouse() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(false);
        houseDao.create(house);
        houseDao.delete(house);
        House result = findEntityInDb(house.getId());
        Assert.assertNull(result);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createWithNullName() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setRunning(true);

        houseDao.create(house);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createWithNullRunning() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");

        houseDao.create(house);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createWithNullAddress() {
        House house = new House();
        house.setRunning(false);
        house.setName("Test house");
        house.setAddress(null);

        houseDao.create(house);
    }

    @Test
    public void findByIdDeletedHouse() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(false);
        houseDao.create(house);
        houseDao.delete(house);

        House result = houseDao.findById(house.getId());
        Assert.assertNull(result);
    }

    @Test
    public void findByNameTest() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(true);
        houseDao.create(house);

        List<House> result = houseDao.findByName(house.getName());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0), house);
    }

    @Test
    public void findByAddressTest() {
        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(true);
        houseDao.create(house);

        List<House> results = houseDao.findByAddress(address);
        Assert.assertNotNull(results);
        Assert.assertEquals(results.size(), 1);
        Assert.assertEquals(results.get(0), house);
    }

    @Test
    public void findAllTest() {
        Address address1 = createValidAddress("Brno");
        Address address2 = createValidAddress("Praha");
        House house1 = new House();
        house1.setAddress(address1);
        house1.setName("Test house");
        house1.setRunning(true);
        House house2 = new House();
        house2.setAddress(address2);
        house2.setName("Test house");
        house2.setRunning(true);
        houseDao.create(house2);
        houseDao.create(house1);

        List<House> results = houseDao.findAll();

        Assert.assertNotNull(results);
        Assert.assertEquals(results.size(), 2);
        Assert.assertTrue(results.contains(house1) && results.contains(house2));
    }

    @Test
    public void findAllEmptyResult() {
        List<House> results = houseDao.findAll();
        Assert.assertNotNull(results);
        Assert.assertEquals(results.size(), 0);
    }

    private SmartMeter createValidSmartMeter() {
            SmartMeter smartMeter = new SmartMeter();
            smartMeter.setCumulativePowerConsumption(123D);
            smartMeter.setRunning(false);
            smartMeter.setLastLogTakenAt(LocalDateTime.of(2021, 1,12,7,32));
            smartMeter.setPowerConsumptionSinceLastLog(0);
            em.persist(smartMeter);
            return smartMeter;
    }


    private Address createValidAddress(String city) {
            Address address = new Address();
            address.setStreet("Botanická");
            address.setCode("15");
            address.setCity(city);
            address.setCountry("Czech Republic");
            address.setPostCode("123456");
            em.persist(address);
            return address;
    }

    private House findEntityInDb(Long id) {
        return em.find(House.class, id);
    }

    private void updateSmartMeter(SmartMeter smartMeter) {
            em.merge(smartMeter);
    }
}
