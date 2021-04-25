package cz.fi.muni.pa165.daoTests;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public void findByUserBasicTest() {
        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRole.USER_ROLE_NAME);
        em.persist(userRole);

        PortalUser user = new PortalUser();
        user.setFirstName("Steve");
        user.setLastName("Jobs");
        user.setUserRole(userRole);
        user.setCreatedTimestamp(LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.of(23, 59,59)));
        user.setPasswordHash("#*##23e");
        user.setEmail("steve.jobs@gmail.com");
        user.setPhone("+999111999");
        em.persist(user);

        PortalUser user2 = new PortalUser();
        user2.setFirstName("Bill");
        user2.setLastName("Gates");
        user2.setUserRole(userRole);
        user2.setCreatedTimestamp(LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.of(23, 59,59)));
        user2.setPasswordHash("#*##23e");
        user2.setEmail("bill.gates@gmail.com");
        user2.setPhone("+999111885");
        em.persist(user2);

        Address address = createValidAddress("Brno");
        House house = new House();
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(true);
        house.setPortalUser(user);
        houseDao.create(house);

        House house2 = new House();
        house2.setAddress(address);
        house2.setName("Test house2");
        house2.setRunning(true);
        house2.setPortalUser(user2);
        houseDao.create(house2);

        List<House> results = houseDao.findByUser(user);
        Assert.assertNotNull(results);
        Assert.assertEquals(results.size(), 1);
        Assert.assertEquals(results.get(0), house);
    }

    @Test
    public void findByUserEmptyTest() {
        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRole.USER_ROLE_NAME);
        em.persist(userRole);

        PortalUser user = new PortalUser();
        user.setFirstName("Steve");
        user.setLastName("Jobs");
        user.setUserRole(userRole);
        user.setCreatedTimestamp(LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.of(23, 59,59)));
        user.setPasswordHash("#*##23e");
        user.setEmail("steve.jobs@gmail.com");
        user.setPhone("+999111999");
        em.persist(user);

        List<House> results = houseDao.findByUser(user);
        Assert.assertNotNull(results);
        Assert.assertEquals(results.size(), 0);
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
