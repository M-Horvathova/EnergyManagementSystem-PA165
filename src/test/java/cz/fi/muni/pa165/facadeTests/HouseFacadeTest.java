package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.AddressDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserHouseDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.facade.HouseFacadeImpl;
import cz.fi.muni.pa165.service.AddressService;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.PortalUser.PortalUserService;
import cz.fi.muni.pa165.service.config.BeanMappingConfiguration;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

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
    HouseService houseService;

    @Mock
    AddressService addressService;

    @Mock
    PortalUserService portalUserService;

    @Mock
    BeanMappingService beanMappingService;

    HouseFacade houseFacade;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);

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
        houseDTO.setPortalUser(portalUserDTO);

        when(houseService.findById(any(Long.class))).thenReturn(new House());
        when(beanMappingService.mapTo(any(House.class), eq(HouseDTO.class))).thenReturn(houseDTO);

        houseFacade = new HouseFacadeImpl(houseService, addressService, portalUserService, beanMappingService);
    }

    @Test
    public void testCreate() {
        System.err.println(houseFacade.getHouseWithId(1L));

        verify(beanMappingService, times(1)).mapTo(any(House.class), eq(HouseDTO.class));
    }
}