package cz.fi.muni.pa165.serviceTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.dao.HouseDaoImpl;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes= ServiceConfiguration.class)
//@ContextConfiguration(classes = PersistenceApplicationContext.class)
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

    @BeforeClass
    public void setup() throws ServiceException  {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public House prepareTestHouse() {
        testHouse = new House();
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
        List<House> testHouses = new ArrayList<>();
        testHouses.add(testHouse);
        when(houseDao.update(testHouse)).thenReturn(testHouse);
        when(houseDao.findByAddress(any(Address.class))).thenReturn(new ArrayList<>(testHouses));

        Address newAddress = new Address();
        newAddress.setCity("Test New City");
        newAddress.setPostCode("54321");
        newAddress.setCountry("SK");
        Address oldAddress = prepareAddress();
        houseService.changeAddress(testHouse, newAddress);
        Assert.assertEquals(testHouse.getAddress(), newAddress);
        verify(addressDao, times(1)).delete(any(Address.class));
        verify(addressDao).delete(oldAddress);
    }




}
