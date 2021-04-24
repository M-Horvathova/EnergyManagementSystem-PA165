package cz.fi.muni.pa165.serviceTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes= ServiceConfiguration.class)
public class houseTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private HouseDao houseDao;

    @Mock
    private AddressDao addressDao;

    @Autowired
    @InjectMocks
    private HouseService houseService;

    private House testHouse;

    private Address testAddress;

    @BeforeMethod
    public void setup() throws ServiceException  {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public House prepareTestHouse() {
        testHouse = new House();
        testHouse.setId(1L);
        testHouse.setRunning(false);
        testHouse.setName("Test house");
        testHouse.setAddress(prepareAddress());
        return testHouse;
    }

    public Address prepareAddress() {
       Address testAddress = new Address();
       testAddress.setCity("Test City");
       testAddress.setPostCode("12345");
       testAddress.setCountry("CZ");
       this.testAddress = testAddress;
       return  testAddress;
    }

    @Test
    public void changeAddress() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        House testHouse2 = prepareTestHouse();
        testHouse2.setName("test house 2");
        testHouses.add(testHouse2);
        when(houseDao.update(testHouse)).thenReturn(testHouse);
        when(houseDao.findByAddress(any(Address.class))).thenReturn(new ArrayList<>(testHouses));

        Address newAddress = new Address();
        newAddress.setCity("Test New City");
        newAddress.setPostCode("54321");
        newAddress.setCountry("SK");
        houseService.changeAddress(testHouse, newAddress);
        Assert.assertEquals(testHouse.getAddress(), newAddress);
        verify(addressDao, times(0)).delete(any(Address.class));
    }

    @Test
    public void changeAddressDelete() {
        Address oldAddress = prepareAddress();
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        when(houseDao.update(testHouse)).thenReturn(testHouse);
        when(houseDao.findByAddress(oldAddress)).thenReturn(new ArrayList<>(testHouses));
        Address newAddress = new Address();
        newAddress.setCity("Test New City");
        newAddress.setPostCode("54321");
        newAddress.setCountry("SK");

        houseService.changeAddress(testHouse, newAddress);

        Assert.assertEquals(testHouse.getAddress(), newAddress);
        verify(addressDao, times(1)).delete(any(Address.class));
        verify(addressDao).delete(oldAddress);
    }

    @Test
    public void deleteHouseNotAddress() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        House testHouse2 = prepareTestHouse();
        testHouse2.setName("test house 2");
        testHouses.add(testHouse2);
        when(houseDao.findByAddress(any(Address.class))).thenReturn(new ArrayList<>(testHouses));

        houseService.deleteHouse(testHouse);

        verify(addressDao, times(0)).delete(any(Address.class));
        verify(houseDao).delete(testHouse);
    }

    @Test
    public void deleteHouseAndAddress() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        when(houseDao.findByAddress(any(Address.class))).thenReturn(new ArrayList<>(testHouses));

        houseService.deleteHouse(testHouse);

        verify(addressDao, times(1)).delete(testAddress);
        verify(houseDao).delete(testHouse);
    }

    @Test
    public void createValidHouse() {
        houseService.createHouse(testHouse);
        verify(houseDao, times(1)).create(testHouse);
    }

    @Test
    public void changeName() {
        String newName = "new name";
        houseService.changeName(testHouse, newName);
        Assert.assertEquals(newName, testHouse.getName());
        verify(houseDao).update(testHouse);
    }

    @Test
    public void changeRunningSame() {
        houseService.changeRunning(testHouse, false);
        Assert.assertFalse(testHouse.getRunning());
        verify(houseDao).update(testHouse);
    }

    @Test
    public void changeRunning() {
        houseService.changeRunning(testHouse, true);
        Assert.assertTrue(testHouse.getRunning());
        verify(houseDao).update(testHouse);
    }

    @Test
    public void findById() {
        when(houseDao.findById(testHouse.getId())).thenReturn(testHouse);
        Assert.assertEquals(testHouse, houseService.findById(testHouse.getId()));
    }

    @Test
    public void findByNameOne() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        when(houseDao.findByName(testHouse.getName())).thenReturn(testHouses);

        Assert.assertEquals(testHouses, houseService.findByName(testHouse.getName()));
    }

    @Test
    public void findByNameTwo() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        House testHouse2 = prepareTestHouse();
        testHouse2.setId(2L);
        testHouses.add(testHouse2);
        when(houseDao.findByName(testHouse.getName())).thenReturn(testHouses);

        Assert.assertEquals(testHouses, houseService.findByName(testHouse.getName()));
    }

    @Test
    public void findByNameNone() {
        List<House> testHouses = new ArrayList<>();
        when(houseDao.findByName(testHouse.getName())).thenReturn(testHouses);

        Assert.assertEquals(testHouses, houseService.findByName(testHouse.getName()));
    }

    @Test
    public void findByAddressOne() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        when(houseDao.findByAddress(testHouse.getAddress())).thenReturn(testHouses);

        Assert.assertEquals(houseService.findByAddress(testHouse.getAddress()), testHouses);
    }

    @Test
    public void findByAddressTwo() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        House testHouse2 = prepareTestHouse();
        Address address2 = prepareAddress();
        address2.setCountry("SK");
        testHouse2.setAddress(address2);
        testHouses.add(testHouse2);
        when(houseDao.findByAddress(testHouse.getAddress())).thenReturn(testHouses);

        Assert.assertEquals(houseService.findByAddress(testHouse.getAddress()), testHouses);
    }

    @Test
    public void findByAddressNone() {
        List<House> testHouses = new ArrayList<>();
        when(houseDao.findByAddress(testHouse.getAddress())).thenReturn(testHouses);

        Assert.assertEquals(houseService.findByAddress(testHouse.getAddress()), testHouses);
    }

    @Test
    public void findByUserOne() {
        PortalUser user = new PortalUser();
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        when(houseDao.findByUser(user)).thenReturn(testHouses);

        Assert.assertEquals(houseService.findByUser(user), testHouses);
    }

    @Test
    public void findByUserTwo() {
        PortalUser user = new PortalUser();
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        House testHouse2 = prepareTestHouse();
        Address address2 = prepareAddress();
        address2.setCountry("SK");
        testHouse2.setAddress(address2);
        testHouses.add(testHouse2);
        when(houseDao.findByUser(user)).thenReturn(testHouses);

        Assert.assertEquals(houseService.findByUser(user), testHouses);
    }

    @Test
    public void findByUserNone() {
        PortalUser user = new PortalUser();
        List<House> testHouses = new ArrayList<>();
        when(houseDao.findByUser(user)).thenReturn(testHouses);

        Assert.assertEquals(houseService.findByUser(user), testHouses);
    }

    @Test
    public void findAllOne() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        when(houseDao.findAll()).thenReturn(testHouses);

        Assert.assertEquals(houseService.findAll(), testHouses);
    }

    @Test
    public void findAllTwo() {
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        House testHouse2 = prepareTestHouse();
        Address address2 = prepareAddress();
        address2.setCountry("SK");
        testHouse2.setAddress(address2);
        testHouses.add(testHouse2);
        when(houseDao.findAll()).thenReturn(testHouses);

        Assert.assertEquals(houseService.findAll(), testHouses);
    }

    @Test
    public void findAllNone() {
        List<House> testHouses = new ArrayList<>();
        when(houseDao.findAll()).thenReturn(testHouses);

        Assert.assertEquals(houseService.findAll(), testHouses);
    }

}
