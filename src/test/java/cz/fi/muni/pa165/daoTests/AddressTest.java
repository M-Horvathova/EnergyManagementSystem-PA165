package cz.fi.muni.pa165.daoTests;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.entity.Address;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AddressTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private EntityManager em;

    @Test
    public void basicCreateTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");

        addressDao.create(address);
        Address result = em.find(Address.class, address.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStreet(), address.getStreet());
        Assert.assertEquals(result.getCode(), address.getCode());
        Assert.assertEquals(result.getCity(), address.getCity());
        Assert.assertEquals(result.getCountry(), address.getCountry());
        Assert.assertEquals(result.getPostCode(), address.getPostCode());
    }

    @Test
    public void nullableStreetCreate() {
        Address address = new Address();
        address.setStreet(null);
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");
        addressDao.create(address);
    }

    @Test
    public void nullableCodeCreate() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode(null);
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");

        addressDao.create(address);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void nullableCityCreateTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity(null);
        address.setCountry("Czech Republic");
        address.setPostCode("123456");

        addressDao.create(address);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void nullableCountryCreateTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry(null);
        address.setPostCode("123456");

        addressDao.create(address);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void nullablePostCodeTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode(null);

        addressDao.create(address);
    }

    @Test
    public void updateTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");

        addressDao.create(address);

        address.setStreet("Lesna");
        address.setCode("25");
        address.setCode("Bratislava");
        address.setCode("Slovakia");
        address.setCode("980770");

        Address result = addressDao.update(address);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStreet(), address.getStreet());
        Assert.assertEquals(result.getCode(), address.getCode());
        Assert.assertEquals(result.getCity(), address.getCity());
        Assert.assertEquals(result.getCountry(), address.getCountry());
        Assert.assertEquals(result.getPostCode(), address.getPostCode());
    }

    @Test
    public void findByIdTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");

        addressDao.create(address);

        Address result = addressDao.findById(address.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStreet(), address.getStreet());
        Assert.assertEquals(result.getCode(), address.getCode());
        Assert.assertEquals(result.getCity(), address.getCity());
        Assert.assertEquals(result.getCountry(), address.getCountry());
        Assert.assertEquals(result.getPostCode(), address.getPostCode());
    }

    @Test
    public void findByNotExistingIdTest() {
        Address result = addressDao.findById(16L);
        Assert.assertNull(result);
    }

    @Test
    public void findAllTest() {
        createListOfAddresses();
        List<Address> list = addressDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 4, "Length of the list should be same");
    }

    @Test
    public void findAllEmptyTest() {
        List<Address> list = addressDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0, "Length of the list should be same");
    }

    @Test
    public void findWithoutNullValuesTest() {
        Address address = createListOfAddresses().get(0);

        Address result = addressDao.find(address.getStreet(), address.getCode(),
                address.getCity(), address.getPostCode(), address.getCountry());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStreet(), address.getStreet());
        Assert.assertEquals(result.getCode(), address.getCode());
        Assert.assertEquals(result.getCity(), address.getCity());
        Assert.assertEquals(result.getCountry(), address.getCountry());
        Assert.assertEquals(result.getPostCode(), address.getPostCode());
    }

    @Test
    public void findStreetNullValueTest() {
        Address address = createListOfAddresses().get(3);

        Address result = addressDao.find(address.getStreet(), address.getCode(),
                address.getCity(), address.getPostCode(), address.getCountry());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStreet(), address.getStreet());
        Assert.assertEquals(result.getCode(), address.getCode());
        Assert.assertEquals(result.getCity(), address.getCity());
        Assert.assertEquals(result.getCountry(), address.getCountry());
        Assert.assertEquals(result.getPostCode(), address.getPostCode());
    }

    @Test
    public void findCodeNullValueTest() {
        Address address = createListOfAddresses().get(1);

        Address result = addressDao.find(address.getStreet(), address.getCode(),
                address.getCity(), address.getPostCode(), address.getCountry());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStreet(), address.getStreet());
        Assert.assertEquals(result.getCode(), address.getCode());
        Assert.assertEquals(result.getCity(), address.getCity());
        Assert.assertEquals(result.getCountry(), address.getCountry());
        Assert.assertEquals(result.getPostCode(), address.getPostCode());
    }

    @Test
    public void findStreetAndCodeNullValuesTest() {
        Address address = createListOfAddresses().get(2);

        Address result = addressDao.find(address.getStreet(), address.getCode(),
                address.getCity(), address.getPostCode(), address.getCountry());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStreet(), address.getStreet());
        Assert.assertEquals(result.getCode(), address.getCode());
        Assert.assertEquals(result.getCity(), address.getCity());
        Assert.assertEquals(result.getCountry(), address.getCountry());
        Assert.assertEquals(result.getPostCode(), address.getPostCode());
    }

    @Test
    public void findNotExistsTest() {
        Address result = addressDao.find("Street", "15", "City", "528987", "USA");
        Assert.assertNull(result);
    }

    @Test
    public void deleteTest() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");

        addressDao.create(address);
        Address resultAfterCreate = addressDao.findById(address.getId());

        Assert.assertNotNull(resultAfterCreate);

        addressDao.delete(address);

        Address resultAfterDelete = addressDao.findById(address.getId());
        Assert.assertNull(resultAfterDelete);
    }

    private List<Address> createListOfAddresses() {
        Address address1 = new Address();
        address1.setStreet("Botanická");
        address1.setCode("15");
        address1.setCity("Brno");
        address1.setCountry("Czech Republic");
        address1.setPostCode("123456");

        addressDao.create(address1);

        Address address2 = new Address();
        address2.setStreet("Lesná");
        address2.setCode(null);
        address2.setCity("Brno");
        address2.setCountry("Czech Republic");
        address2.setPostCode("123456");

        addressDao.create(address2);

        Address address3 = new Address();
        address3.setStreet(null);
        address3.setCode(null);
        address3.setCity("Hodonín");
        address3.setCountry("Czech Republic");
        address3.setPostCode("520031");

        addressDao.create(address3);

        Address address4 = new Address();
        address4.setStreet(null);
        address4.setCode("15");
        address4.setCity("Hodonín");
        address4.setCountry("Czech Republic");
        address4.setPostCode("520031");

        addressDao.create(address4);

        return Arrays.asList(address1, address2, address3, address4);
    }
}
