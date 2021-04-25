package cz.fi.muni.pa165.facadeTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.HouseCreateDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.service.AddressService;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.PortalUser.PortalUserService;
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

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes= ServiceConfiguration.class)
public class HouseFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private HouseService houseService;

    @Mock
    private House testHouse;
    private AddressService addressService;
    private PortalUserService portalUserService;


    @Autowired
    @InjectMocks
    private HouseFacade houseFacade;

    @BeforeMethod
    public void setup() throws ServiceException  {
        MockitoAnnotations.initMocks(this);
    }


    public HouseCreateDTO prepareHouseCreateDto() {
        HouseCreateDTO houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setCity("Test City");
        houseCreateDTO.setCountry("CZ");
        houseCreateDTO.setName("Test name");
        houseCreateDTO.setPortalUserId(20L);
        houseCreateDTO.setPostCode("12345");
        houseCreateDTO.setRunning(false);
        return houseCreateDTO;
    }

    public HouseDTO prepareHouseDto() {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setRunning(false);
        houseDTO.setName("Test name");
        return houseDTO;
    }

    public House prepareTestHouse() {
        House testHouse = new House();
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
        return  testAddress;
    }

    public House prepareTestHouseObject() {
        House testHouse = new House();
        testHouse.setId(1L);
        testHouse.setRunning(false);
        testHouse.setName("Test house");
        testHouse.setAddress(prepareAddress());
        return testHouse;
    }

    public PortalUser prepareUser() {
        PortalUser user = new PortalUser();
        user.setId(20L);
        return user;
    }


    @Test
    public void isRunning() {
        when(houseService.findById(1L)).thenReturn(prepareTestHouseObject());
        when(testHouse.getRunning()).thenReturn(prepareTestHouseObject().getRunning());

        houseFacade.isRunning(1L);
    }


}
