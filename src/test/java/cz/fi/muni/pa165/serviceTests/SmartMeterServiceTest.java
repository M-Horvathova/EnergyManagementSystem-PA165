package cz.fi.muni.pa165.serviceTests;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.service.SmartMeterService;
import cz.fi.muni.pa165.service.SmartMeterServiceImpl;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Martin Podhora
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SmartMeterServiceTest {
    @Mock
    private SmartMeterDao smartMeterDao;

    @Mock
    private MeterLogDao meterLogDao;

    @Autowired
    private SmartMeterService smartMeterService;

    private Address testAddress1;
    private House testHouse1;
    private SmartMeter testSmartMeter1;
    private SmartMeter testSmartMeter2;
    private SmartMeter testSmartMeter3;
    private MeterLog testMeterLog11;
    private MeterLog testMeterLog12;
    private MeterLog testMeterLog21;

    @BeforeMethod
    public void prepareMeterLogs() {
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
        testSmartMeter3.setRunning(true);

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
        testSmartMeter1.addMeterLog(testMeterLog12);

        testMeterLog21 = new MeterLog();
        testMeterLog21.setLogDate(LocalDate.of(2021, 1, 30));
        testMeterLog21.setLogTime(LocalTime.of(0, 0));
        testMeterLog21.setMeasure(100L);
        testMeterLog21.setSmartMeter(testSmartMeter2);
        testSmartMeter2.addMeterLog(testMeterLog21);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        smartMeterService = new SmartMeterServiceImpl(smartMeterDao, meterLogDao);
    }

    @AfterMethod
    void clearInvocations() {
        Mockito.clearInvocations(smartMeterDao, meterLogDao);
    }

    @Test
    public void createTwoLogSmartMeterTest() {
        smartMeterService.create(testSmartMeter1);
        verify(smartMeterDao, times(1)).create(testSmartMeter1);
    }

    @Test
    public void createOneLogSmartMeterTest() {
        smartMeterService.create(testSmartMeter2);
        verify(smartMeterDao, times(1)).create(testSmartMeter1);
    }

    @Test
    public void createNoLogSmartMeterTest() {
        smartMeterService.create(testSmartMeter3);
        verify(smartMeterDao, times(1)).create(testSmartMeter1);
    }
    @Test
    public void updateRunningSmartMeterTest() {
        testSmartMeter1.setRunning(false);
        smartMeterService.update(testSmartMeter1);
        verify(smartMeterDao, times(1)).update(testSmartMeter1);
    }

    @Test
    public void updatePowerConsumptionSmartMeterTest() {
        testSmartMeter1.setPowerConsumptionSinceLastLog(1000);
        smartMeterService.update(testSmartMeter1);
        verify(smartMeterDao, times(1)).update(testSmartMeter1);
    }

    @Test
    public void updateLastLogTakenAtSmartMeterTest() {
        testSmartMeter1.setLastLogTakenAt(LocalDateTime.now());
        smartMeterService.update(testSmartMeter1);
        verify(smartMeterDao, times(1)).update(testSmartMeter1);
    }

    @Test
    public void updateDescriptionSmartMeterTest() {
        testSmartMeter1.setSmartMeterDescription("TEST Description");
        smartMeterService.update(testSmartMeter1);
        verify(smartMeterDao, times(1)).update(testSmartMeter1);
    }

    @Test
    public void updateCummulativePowerComsumptionSmartMeterTest() {
        testSmartMeter1.setCumulativePowerConsumption(1000);
        smartMeterService.update(testSmartMeter1);
        verify(smartMeterDao, times(1)).update(testSmartMeter1);
    }

    @Test
    public void findByIdTest() {
        when(smartMeterDao.findById(testSmartMeter1.getId())).thenReturn(testSmartMeter1);
        SmartMeter smartMeter = smartMeterService.findById(testSmartMeter1.getId());

        Assert.assertNotNull(smartMeter);
        Assert.assertEquals(smartMeter.getMeterLogs(), testSmartMeter1.getMeterLogs());
        Assert.assertEquals(smartMeter.getLastLogTakenAt(), testSmartMeter1.getLastLogTakenAt());
        Assert.assertEquals(smartMeter.getPowerConsumptionSinceLastLog(), testSmartMeter1.getPowerConsumptionSinceLastLog());
        Assert.assertEquals(smartMeter.getHouse(), testSmartMeter1.getHouse());
        Assert.assertEquals(smartMeter.getSmartMeterDescription(), testSmartMeter1.getSmartMeterDescription());
        verify(smartMeterDao, times(1)).findById(testSmartMeter1.getId());
    }

    @Test
    public void findByNotExistingIdTest() {
        when(smartMeterDao.findById(testSmartMeter1.getId())).thenReturn(testSmartMeter1);
        SmartMeter smartMeter = smartMeterService.findById(testSmartMeter1.getId());

        Assert.assertNotNull(smartMeter);
        Assert.assertEquals(smartMeter.getMeterLogs(), testSmartMeter1.getMeterLogs());
        Assert.assertEquals(smartMeter.getLastLogTakenAt(), testSmartMeter1.getLastLogTakenAt());
        Assert.assertEquals(smartMeter.getPowerConsumptionSinceLastLog(), testSmartMeter1.getPowerConsumptionSinceLastLog());
        Assert.assertEquals(smartMeter.getHouse(), testSmartMeter1.getHouse());
        Assert.assertEquals(smartMeter.getSmartMeterDescription(), testSmartMeter1.getSmartMeterDescription());
        verify(smartMeterDao, times(1)).findById(testSmartMeter1.getId());
    }

    @Test
    public void findAllTest() {
        List<SmartMeter> smList = new ArrayList<>();
        smList.add(testSmartMeter1);
        smList.add(testSmartMeter2);
        smList.add(testSmartMeter3);
        when(smartMeterDao.findAll()).thenReturn(smList);

        List<SmartMeter> list = smartMeterDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 3);
        verify(smartMeterDao, times(1)).findAll();
    }

    @Test
    public void findAllEmptyTest() {
        when(smartMeterDao.findAll()).thenReturn(new ArrayList<>());

        List<SmartMeter> list = smartMeterDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);
        verify(smartMeterDao, times(1)).findAll();
    }

    @Test
    public void getRunningSmartMetersOneRunsTest() {
        when(smartMeterDao.findByRunning(true)).thenReturn(new ArrayList<>());

        List<SmartMeter> runningMeters = smartMeterService.getRunningSmartMeters();
        Assert.assertEquals(runningMeters.size(), 0);
        verify(smartMeterDao, times(1)).findByRunning(true);
    }

    @Test
    public void getRunningSmartMetersNoneRunsTest() {
        List<SmartMeter> smList = new ArrayList<>();
        smList.add(testSmartMeter1);
        when(smartMeterDao.findByRunning(true)).thenReturn(smList);

        List<SmartMeter> runningMeters = smartMeterService.getRunningSmartMeters();
        Assert.assertEquals(runningMeters.size(), 1);
        verify(smartMeterDao, times(1)).findByRunning(true);
    }

    @Test
    public void getRunningSmartMetersThreeRunTest() {
        List<SmartMeter> smList = new ArrayList<>();
        smList.add(testSmartMeter1);
        smList.add(testSmartMeter2);
        smList.add(testSmartMeter3);
        when(smartMeterDao.findByRunning(true)).thenReturn(smList);

        List<SmartMeter> runningMeters = smartMeterService.getRunningSmartMeters();
        Assert.assertEquals(runningMeters.size(), 3);
        verify(smartMeterDao, times(1)).findByRunning(true);
    }

    @Test
    public void addMeterLogToNonemptySmartMeterTest() {
        int initialSize = testSmartMeter1.getMeterLogs().size();
        smartMeterService.addMeterLog(testSmartMeter1, testMeterLog11);
        Assert.assertEquals(testSmartMeter1.getMeterLogs().size(), initialSize + 1);
    }

    @Test
    public void addMeterLogToEmptySmartMeterTest() {
        int initialSize = testSmartMeter3.getMeterLogs().size();
        smartMeterService.addMeterLog(testSmartMeter3, testMeterLog11);
        Assert.assertEquals(testSmartMeter3.getMeterLogs().size(), initialSize + 1);
    }

    @Test
    public void deleteTest() {
        smartMeterService.delete(testSmartMeter1);
        verify(smartMeterDao, times(1)).delete(testSmartMeter1);
    }

    @Test
    public void getPowerSpentForDateForSmartMeterTest() {
        when(meterLogDao.findByDate(testMeterLog11.getLogDate())).thenReturn(new ArrayList<MeterLog>(Arrays.asList(testMeterLog11)));
        when(meterLogDao.findByDate(testMeterLog12.getLogDate())).thenReturn(new ArrayList<MeterLog>(Arrays.asList(testMeterLog12)));

        testSmartMeter1.addMeterLog(testMeterLog11);
        var result = smartMeterService.getPowerSpentForDateForSmartMeter(testMeterLog11.getLogDate(), testSmartMeter1);
        Assert.assertEquals(result, (double)testMeterLog11.getMeasure());
        result = smartMeterService.getPowerSpentForDateForSmartMeter(testMeterLog12.getLogDate(), testSmartMeter1);
        Assert.assertEquals(result, (double)testMeterLog12.getMeasure());
        verify(meterLogDao, times(2)).findByDate(any(LocalDate.class));
    }

    @Test
    public void getPowerSpentForDateForOneLogSmartMeterTest() {
        when(meterLogDao.findByDate(any(LocalDate.class))).thenReturn(new ArrayList<MeterLog>(testSmartMeter2.getMeterLogs()));
        var result = smartMeterService.getPowerSpentForDateForSmartMeter(testMeterLog21.getLogDate(), testSmartMeter2);
        Assert.assertEquals(0.0, (double)testMeterLog21.getMeasure() - result);
        verify(meterLogDao, times(1)).findByDate(any(LocalDate.class));
    }

    @Test
    public void getPowerSpentForDateForNoLogSmartMeterTest() {
        var result = smartMeterService.getPowerSpentForDateForSmartMeter(LocalDate.now(), testSmartMeter3);
        Assert.assertEquals(result, 0.0);
        verify(meterLogDao, times(1)).findByDate(any(LocalDate.class));
    }

    @Test
    public void getAllPowerSpentForThreeTest() {
        List<SmartMeter> smList = new ArrayList<>();
        smList.add(testSmartMeter1);
        smList.add(testSmartMeter2);
        smList.add(testSmartMeter3);
        when(smartMeterDao.findAll()).thenReturn(smList);

        double result = smartMeterService.getAllPowerSpent();
        Assert.assertEquals(300.0, result);
        verify(smartMeterDao, times(1)).findAll();
    }

    @Test
    public void getAllPowerSpentForOneTest() {
        List<SmartMeter> smList = new ArrayList<>();
        smList.add(testSmartMeter1);
        when(smartMeterDao.findAll()).thenReturn(smList);

        double result = smartMeterService.getAllPowerSpent();
        Assert.assertEquals(100.0, result);
        verify(smartMeterDao, times(1)).findAll();
    }

    @Test
    public void getAllPowerSpentForNoneTest() {
        List<SmartMeter> smList = new ArrayList<>();
        when(smartMeterDao.findAll()).thenReturn(smList);

        double result = smartMeterService.getAllPowerSpent();
        Assert.assertEquals(0.0, result);
        verify(smartMeterDao, times(1)).findAll();
    }

    @Test
    public void sumPowerFromLogs() {
        List<MeterLog> mlList = new ArrayList<>();
        mlList.add(testMeterLog11);
        mlList.add(testMeterLog12);

        double result = smartMeterService.sumPowerFromLogs(mlList);
        Assert.assertEquals(result, 150.0);
    }

    @Test
    public void sumPowerFromNoLogs() {
        List<MeterLog> mlList = new ArrayList<>();
        double result = smartMeterService.sumPowerFromLogs(mlList);
        Assert.assertEquals(result, 0.0);
    }
}
