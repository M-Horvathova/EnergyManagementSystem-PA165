package cz.fi.muni.pa165.serviceTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.service.AddressService;
import cz.fi.muni.pa165.service.AddressServiceImpl;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes= ServiceConfiguration.class)
public class AddressServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private AddressDao addressDao;
    private AddressService addressService;
    private Address existingAddress;
    private Address address;

    @BeforeMethod
    public void setup() throws ServiceException  {
        MockitoAnnotations.initMocks(this);
        existingAddress = new Address();
        existingAddress.setId(1L);
        existingAddress.setCity("Test City");
        existingAddress.setPostCode("12345");
        existingAddress.setCountry("CZ");
        existingAddress.setCode("1191");
        existingAddress.setStreet("New");

        address = new Address();
        address.setCity("Test City");
        address.setPostCode("12345");
        address.setCountry("CZ");
        address.setCode("1191");
        address.setStreet("New");

        addressService = new AddressServiceImpl(addressDao);
    }

    @Test
    public void createValidAddress() {
        when(addressDao.find(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(null);

        Address result = addressService.createAddress(address);
        Assert.assertNotNull(result);
        verify(addressDao, times(1)).find(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
        verify(addressDao, times(1)).create(any(Address.class));
    }

    @Test
    public void createExistingAddress() {
        when(addressDao.find(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(existingAddress);

        Address result = addressService.createAddress(address);
        Assert.assertNotNull(result);
        verify(addressDao, times(1)).find(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
        verify(addressDao, times(0)).create(any(Address.class));
    }

    @Test
    public void findById() {
        when(addressDao.findById(any(Long.class))).thenReturn(existingAddress);
        Address result = addressService.findById(existingAddress.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(result, existingAddress);
        verify(addressDao, times(1)).findById(1L);
    }
}
