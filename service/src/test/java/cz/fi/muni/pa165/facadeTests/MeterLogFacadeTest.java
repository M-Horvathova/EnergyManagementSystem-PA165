package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.dto.meterLog.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.meterLog.MeterLogDTO;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.facade.MeterLogFacade;
import cz.fi.muni.pa165.service.facade.MeterLogFacadeImpl;
import cz.fi.muni.pa165.service.serviceInterface.MeterLogService;
import cz.fi.muni.pa165.service.serviceInterface.SmartMeterService;
import cz.fi.muni.pa165.service.config.BeanMappingConfiguration;
import cz.fi.muni.pa165.service.mappers.MeterLogMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes = BeanMappingConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MeterLogFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MeterLogService meterLogService;

    @Mock
    private SmartMeterService smartMeterService;
    @Mock
    private MeterLogMapper meterLogMapper;

    private MeterLogFacade meterLogFacade;
    private MeterLogCreateDTO meterLogCreateDTO;
    private MeterLogDTO meterLogDTO;
    private MeterLog meterLog;
    private SmartMeter smartMeter;
    private House house;
    private PortalUser user;
    private UserRole role;
    private Address address;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
        initEntityAndDTOAttributes();

        when(meterLogService.createMeterLog(any(MeterLog.class))).thenReturn(meterLog);
        when(meterLogService.findById(any(Long.class))).thenReturn(meterLog);
        when(meterLogService.findInDateFrame(any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<>(Collections.singletonList(meterLog)));
        when(meterLogService.findInDateFrameWithDayTime(any(LocalDate.class), any(LocalDate.class), any(DayTime.class))).thenReturn(new ArrayList<>(Collections.singletonList(meterLog)));
        when(smartMeterService.findById(any(Long.class))).thenReturn(smartMeter);
        when(meterLogMapper.meterLogToDTO(any(MeterLog.class))).thenReturn(meterLogDTO);
        meterLogFacade = new MeterLogFacadeImpl(meterLogService, smartMeterService, meterLogMapper);
    }

    @Test
    public void testCreate() {
        Long id = meterLogFacade.createMeterLog(meterLogCreateDTO);

        Assert.assertEquals(id, meterLog.getId());
        verify(smartMeterService, times(1)).findById(smartMeter.getId());
        verify(meterLogService, times(1)).createMeterLog(any(MeterLog.class));
    }

    @Test
    public void testDelete() {
        meterLogFacade.deleteMeterLog(1L);

        verify(meterLogService, times(1)).findById(1L);
        verify(meterLogService, times(1)).deleteMeterLog(any(MeterLog.class));
    }

    @Test
    public void testGetMeterLogWithId() {
        meterLogFacade.getMeterLogWithId(1L);

        verify(meterLogService, times(1)).findById(1L);
    }

    @Test
    public void testGetAllMeterLogs() {
        meterLogFacade.getAllMeterLogs();

        verify(meterLogService, times(1)).findAll();
    }

    @Test
    public void testGetLogsInTimeFrame() {
        meterLogFacade.getLogsInTimeFrame("2019-12-27", "2019-12-29");

        var startDate = LocalDate.parse("2019-12-27");
        var endDate = LocalDate.parse("2019-12-29");

        verify(meterLogService, times(1)).findInDateFrame(startDate, endDate);
    }

    @Test
    public void testGetLogsInTimeFrameWithDaytime() {
        meterLogFacade.getLogsInTimeFrameWithDaytime("2019-12-27", "2019-12-29", DayTime.Day.toString());

        var startDate = LocalDate.parse("2019-12-27");
        var endDate = LocalDate.parse("2019-12-29");

        verify(meterLogService, times(1)).findInDateFrameWithDayTime(startDate, endDate, DayTime.Day);
    }


    private void initEntityAndDTOAttributes() {
        role = new UserRole();
        role.setRoleName(UserRole.USER_ROLE_NAME);

        user = new PortalUser();
        user.setId(1L);
        user.setFirstName("Patrik");
        user.setLastName("Valo");
        user.setUserRole(role);
        user.setCreatedTimestamp(LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.of(23, 59,59)));
        user.setPasswordHash("#*##23e");
        user.setEmail("valo.patrik@muni.cz");
        user.setPhone("+999111999");

        address = new Address();
        address.setId(1L);
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");

        house = new House();
        house.setId(1L);
        house.setAddress(address);
        house.setName("Test house");
        house.setRunning(true);
        user.addHouse(house);

        smartMeter = new SmartMeter();
        smartMeter.setId(1L);
        smartMeter.setCumulativePowerConsumption(100);
        smartMeter.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 23), LocalTime.of(16, 30)));
        smartMeter.setPowerConsumptionSinceLastLog(100);
        smartMeter.setRunning(true);
        house.addSmartMeter(smartMeter);

        LocalDate date = LocalDate.of(2020, 12, 1);
        LocalTime time = LocalTime.of(11, 20);
        Long measure = 25L;

        meterLogCreateDTO = new MeterLogCreateDTO();
        meterLogCreateDTO.setMeasure(measure);
        meterLogCreateDTO.setLogDate(date.toString());
        meterLogCreateDTO.setSmartMeterId(smartMeter.getId());
        meterLogCreateDTO.setLogTime("11:20:00");

        meterLogDTO = new MeterLogDTO();
        meterLogDTO.setMeasure(measure);
        meterLogDTO.setLogDate(date.toString());
        meterLogDTO.setSmartMeterId(smartMeter.getId());
        meterLogDTO.setLogTime("11:10:15");
        meterLogDTO.setCreateStamp(LocalDateTime.now().toString());

        meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(date);
        meterLog.setLogTime(time);
        meterLog.setMeasure(measure);
    }
}