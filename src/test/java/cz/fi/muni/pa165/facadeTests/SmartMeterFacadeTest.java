package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.facade.MeterLogFacadeImpl;
import cz.fi.muni.pa165.facade.SmartMeterFacade;
import cz.fi.muni.pa165.facade.SmartMeterFacadeImpl;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.SmartMeterService;
import cz.fi.muni.pa165.service.config.BeanMappingConfiguration;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Martin Podhora
 */
@ContextConfiguration(classes = BeanMappingConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SmartMeterFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private SmartMeterService smartMeterService;

    @Mock
    private HouseService houseService;

    @Mock
    private BeanMappingService beanMappingService;

    private SmartMeterFacade smartMeterFacade;

    private Address testAddress1;
    private House testHouse1;
    private SmartMeter testSmartMeter1;
    private SmartMeter testSmartMeter2;
    private SmartMeter testSmartMeter3;
    private SmartMeterDTO testSmartMeterDTO;
    private SmartMeterCreateDTO testSmartMeterCreateDTO;
    private List<SmartMeter> allSmartMeters;
    private List<SmartMeter> allRunningSmartMeters;
    private MeterLog testMeterLog11;
    private MeterLog testMeterLog12;
    private MeterLog testMeterLog21;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.openMocks(this);
        initEntitiesAndDTOs();

        when(smartMeterService.create(any(SmartMeter.class))).thenReturn(testSmartMeter1);
        when(smartMeterService.update(any(SmartMeter.class))).thenReturn(testSmartMeter1);
        when(smartMeterService.findById(any(Long.class))).thenReturn(testSmartMeter1);
        when(smartMeterService.findAll()).thenReturn(allSmartMeters);
        when(smartMeterService.getRunningSmartMeters()).thenReturn(allRunningSmartMeters);
        when(beanMappingService.mapTo(any(SmartMeterCreateDTO.class), eq(SmartMeter.class))).thenReturn(testSmartMeter1);
        when(beanMappingService.mapTo(any(SmartMeterDTO.class), eq(SmartMeter.class))).thenReturn(testSmartMeter1);
        when(beanMappingService.mapTo(any(SmartMeter.class), eq(SmartMeterCreateDTO.class))).thenReturn(testSmartMeterCreateDTO);
        when(beanMappingService.mapTo(any(SmartMeter.class), eq(SmartMeterDTO.class))).thenReturn(testSmartMeterDTO);

        smartMeterFacade = new SmartMeterFacadeImpl(smartMeterService, houseService, beanMappingService);
    }

    private void initEntitiesAndDTOs() {
        testAddress1 = new Address();
        testAddress1.setCity("Test city 1");
        testAddress1.setCountry("Test country");
        testAddress1.setStreet("Test street 1");
        testAddress1.setCode("TC");
        testAddress1.setPostCode("01234");

        testHouse1 = new House();
        testHouse1.setAddress(testAddress1);
        testHouse1.setName("Test house 1");
        testHouse1.setRunning(true);

        testSmartMeter1 = new SmartMeter();
        testSmartMeter1.setHouse(testHouse1);
        testSmartMeter1.setCumulativePowerConsumption(100);
        testSmartMeter1.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 30), LocalTime.of(0, 0)));
        testSmartMeter1.setPowerConsumptionSinceLastLog(100);
        testSmartMeter1.setRunning(true);

        testSmartMeter2 = new SmartMeter();
        testSmartMeter2.setHouse(testHouse1);
        testSmartMeter2.setCumulativePowerConsumption(100);
        testSmartMeter2.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 30), LocalTime.of(0, 0)));
        testSmartMeter2.setPowerConsumptionSinceLastLog(100);
        testSmartMeter2.setRunning(true);

        testSmartMeter3 = new SmartMeter();
        testSmartMeter3.setHouse(testHouse1);
        testSmartMeter3.setCumulativePowerConsumption(100);
        testSmartMeter3.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 30), LocalTime.of(0, 0)));
        testSmartMeter3.setPowerConsumptionSinceLastLog(100);
        testSmartMeter3.setRunning(false);

        testMeterLog11 = new MeterLog();
        testMeterLog11.setLogDate(LocalDate.of(2021, 1, 29));
        testMeterLog11.setLogTime(LocalTime.of(0, 0));
        testMeterLog11.setMeasure(50L);
        testMeterLog11.setSmartMeter(testSmartMeter1);

        testMeterLog12 = new MeterLog();
        testMeterLog12.setLogDate(LocalDate.of(2021, 1, 30));
        testMeterLog12.setLogTime(LocalTime.of(0, 0));
        testMeterLog12.setMeasure(100L);
        testMeterLog12.setSmartMeter(testSmartMeter1);

        testMeterLog21 = new MeterLog();
        testMeterLog21.setLogDate(LocalDate.of(2021, 1, 30));
        testMeterLog21.setLogTime(LocalTime.of(0, 0));
        testMeterLog21.setMeasure(100L);
        testMeterLog21.setSmartMeter(testSmartMeter2);

        allSmartMeters = new ArrayList<>();
        allSmartMeters.add(testSmartMeter1);
        allSmartMeters.add(testSmartMeter2);
        allSmartMeters.add(testSmartMeter3);

        allRunningSmartMeters = new ArrayList<>();
        allRunningSmartMeters.add(testSmartMeter1);
        allRunningSmartMeters.add(testSmartMeter2);

        testSmartMeterDTO = new SmartMeterDTO();
        testSmartMeterDTO.setHouse(testHouse1);
        testSmartMeterDTO.setCumulativePowerConsumption(100);
        testSmartMeterDTO.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 30), LocalTime.of(0, 0)));
        testSmartMeterDTO.setPowerConsumptionSinceLastLog(100);
        testSmartMeterDTO.setRunning(true);

        testSmartMeterCreateDTO = new SmartMeterCreateDTO();
        testSmartMeterCreateDTO.setCumulativePowerConsumption(100);
        testSmartMeterCreateDTO.setSmartMeterDescription("TEST");
        testSmartMeterCreateDTO.setRunning(true);
        testSmartMeterCreateDTO.setHouseId(1);
    }


    @Test
    public void createSmartMeterTest() {
        smartMeterFacade.createSmartMeter(testSmartMeterCreateDTO);
        verify(smartMeterService, times(1)).create(any(SmartMeter.class));
    }

    @Test
    public void updateSmartMeterTest() {
        SmartMeterDTO result = smartMeterFacade.updateSmartMeter(testSmartMeterDTO);
        verify(smartMeterService, times(1)).update(any(SmartMeter.class));
    }

    @Test
    public void getSmartMeterTest() {
        smartMeterFacade.getSmartMeter(1L);
        verify(smartMeterService, times(1)).findById(1L);
    }

    @Test
    public void getAllSmartMetersTest() {
        smartMeterFacade.getAllSmartMeters();
        verify(smartMeterService, times(1)).findAll();
    }

    @Test
    public void getRunningSmartMetesTest() {
        smartMeterFacade.getRunningSmartMetes();
        verify(smartMeterService, times(1)).getRunningSmartMeters();
    }

    @Test
    public void deleteSmartMeterTest() {
        smartMeterFacade.deleteSmartMeter(testSmartMeterDTO);
        verify(smartMeterService, times(1)).delete(any(SmartMeter.class));
    }

    @Test
    public void getPowerSpentForDateForSmartMeterTest() {
        smartMeterFacade.getPowerSpentForDateForSmartMeter(testSmartMeterDTO.getLastLogTakenAt().toLocalDate(), testSmartMeterDTO);
        verify(smartMeterService, times(1)).getPowerSpentForDateForSmartMeter(any(LocalDate.class), any(SmartMeter.class));

    }

    @Test
    public void getAllPowerSpentTest() {
        smartMeterFacade.getAllPowerSpent();
        verify(smartMeterService, times(1)).getAllPowerSpent();
    }
}
