package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.service.*;
import cz.fi.muni.pa165.service.config.BeanMappingConfiguration;
import cz.fi.muni.pa165.service.facade.HouseFacadeImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes = BeanMappingConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HouseFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private HouseService houseService;

    @Mock
    private AddressService addressService;

    @Mock
    private PortalUserService portalUserService;

    @Mock
    private SmartMeterService smartMeterService;

    @Mock
    private MeterLogService meterLogService;


    private HouseFacade houseFacade;
    private HouseCreateDTO houseCreateDTO;
    private HouseDTO houseDTO;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setName("Test house");
        houseCreateDTO.setRunning(true);
        houseCreateDTO.setStreet("Botanická");
        houseCreateDTO.setCode("15");
        houseCreateDTO.setCity("Brno");
        houseCreateDTO.setCountry("Czech Republic");
        houseCreateDTO.setPostCode("123456");
        houseCreateDTO.setPortalUserId(1L);
        houseDTO = new HouseDTO();

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("Botanická");
        addressDTO.setCode("15");
        addressDTO.setCity("Brno");
        addressDTO.setCountry("Czech Republic");
        addressDTO.setPostCode("123456");

        PortalUserHouseDTO portalUserDTO = new PortalUserHouseDTO();
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(1L);
        houseDTO.setName("Test house");
        houseDTO.setRunning(true);
        houseDTO.setAddress(addressDTO);
        houseDTO.setPortalUserId(portalUserDTO.getId());

        House house = new House();
        house.setId(1L);
        house.setName("Test house");
        house.setRunning(true);
        house.setAddress(new Address());
        house.setPortalUser(new PortalUser());

        when(houseService.createHouse(any(House.class))).thenReturn(house);
        when(houseService.findById(any(Long.class))).thenReturn(house);
        when(addressService.createAddress(any(Address.class))).thenReturn(new Address());
        when(addressService.findById(any(Long.class))).thenReturn(new Address());
        when(portalUserService.findUserById(any(Long.class))).thenReturn(new PortalUser());

        houseFacade = new HouseFacadeImpl(houseService, addressService, portalUserService, smartMeterService, meterLogService);
    }

    @Test
    public void testCreate() {
        houseFacade.createHouse(houseCreateDTO);
        verify(houseService, times(1)).createHouse(any(House.class));
    }

    @Test
    public void deleteTest() {
        houseFacade.deleteHouse(1L);
        verify(houseService, times(1)).deleteHouse(any(House.class));
    }

    @Test
    public void editTest() {
        HouseEditDTO editDTO = new HouseEditDTO();
        editDTO.setName("Nove");
        editDTO.setStreet("Ečerova");
        editDTO.setCode("35");
        editDTO.setCity("Brno");
        editDTO.setCountry("Czech Republic");
        editDTO.setPostCode("123456");
        houseFacade.editHouse(1L, editDTO);

        verify(houseService, times(1)).findById(any(Long.class));
        verify(houseService, times(1)).changeName(any(House.class), any(String.class));
        verify(addressService, times(1)).createAddress(any(Address.class));
        verify(houseService, times(1)).changeAddress(any(House.class), any(Address.class));
    }

    @Test
    public void getHouseWithIdTest() {
        houseFacade.getHouseWithId(1L);
        verify(houseService, times(1)).findById(any(Long.class));
    }

    @Test
    public void getHousesByUser() {
        houseFacade.getHousesByUser(1L);
        verify(portalUserService, times(1)).findUserById(any(Long.class));
        verify(houseService, times(1)).findByUser(any(PortalUser.class));
    }

    @Test
    public void getHousesByName() {
        houseFacade.getHousesByName("Test house");
        verify(houseService, times(1)).findByName(any(String.class));
    }

    @Test
    public void getHousesByAddress() {
        houseFacade.getHousesByAddress(1L);
        verify(addressService, times(1)).findById(any(Long.class));
        verify(houseService, times(1)).findByAddress(any(Address.class));
    }

    @Test
    public void getAllTest() {
        houseFacade.getAllHouses();
        verify(houseService, times(1)).findAll();
    }

    @Test
    public void changeRunningTest() {
        houseFacade.changeRunning(1L, false);
        verify(houseService, times(1)).findById(any(Long.class));
        verify(houseService, times(1)).changeRunning(any(House.class), eq(false));
    }

    @Test
    public void isRunningTest() {
        Boolean res = houseFacade.isRunning(1L);
        verify(houseService, times(1)).findById(any(Long.class));
        Assert.assertTrue(res);
    }
}