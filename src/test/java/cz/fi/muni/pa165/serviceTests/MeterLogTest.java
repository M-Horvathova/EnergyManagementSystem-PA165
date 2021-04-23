package cz.fi.muni.pa165.serviceTests;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class MeterLogTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MeterLogDao meterLogDao;

    @Autowired
    @InjectMocks
    private MeterLogService meterLogService;

    private List<MeterLog> meterLogs;
    private List<MeterLog> sameDateMeterLogs1;
    private List<MeterLog> sameDateMeterLogs2;
    private List<MeterLog> meterLogAlone;

    @BeforeClass
    public void setup() throws ServiceException  {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareMeterLogs() {
        // 1.1.2020
        // night
        MeterLog meterLog0 = new MeterLog();
        meterLog0.setId(1L);
        meterLog0.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog0.setLogTime(LocalTime.of(0, 30));
        meterLog0.setMeasure(123L);

        // day
        MeterLog meterLog1 = new MeterLog();
        meterLog1.setId(2L);
        meterLog1.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog1.setLogTime(LocalTime.of(15, 30));
        meterLog1.setMeasure(1L);
        sameDateMeterLogs1 = new ArrayList<>(Arrays.asList(meterLog0, meterLog1));

        // 2.1.2020
        // night
        MeterLog meterLog2 = new MeterLog();
        meterLog2.setId(3L);
        meterLog2.setLogDate(LocalDate.of(2020, 1, 2));
        meterLog2.setLogTime(LocalTime.of(1, 0));
        meterLog2.setMeasure(21L);

        // day
        MeterLog meterLog3 = new MeterLog();
        meterLog3.setId(4L);
        meterLog3.setLogDate(LocalDate.of(2020, 1, 2));
        meterLog3.setLogTime(LocalTime.of(12, 0));
        meterLog3.setMeasure(2L);
        sameDateMeterLogs2 = new ArrayList<>(Arrays.asList(meterLog2, meterLog3));

        // 3.1.2020
        // day
        MeterLog meterLog4 = new MeterLog();
        meterLog4.setId(5L);
        meterLog4.setLogDate(LocalDate.of(2020, 1, 3));
        meterLog4.setLogTime(LocalTime.of(12, 0));
        meterLog4.setMeasure(15L);
        meterLogAlone = new ArrayList<>(Collections.singletonList(meterLog4));

        meterLogs = new ArrayList<>(Arrays.asList(meterLog0, meterLog1, meterLog2, meterLog3, meterLog4));
    }

    @AfterMethod
    void clearInvocations() {
        Mockito.clearInvocations(meterLogDao);
    }

    @Test
    public void findByIdBasicTest() {
        MeterLog meterLog = meterLogs.get(0);
        when(meterLogDao.findById(meterLog.getId())).thenReturn(meterLog);

        MeterLog result = meterLogService.findById(meterLog.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getLogDate(), meterLog.getLogDate());
        Assert.assertEquals(result.getLogTime(), meterLog.getLogTime());
        Assert.assertEquals(result.getMeasure(), meterLog.getMeasure());
        verify(meterLogDao, times(1)).findById(meterLog.getId());
    }

    @Test
    public void findByNotExistingIdTest() {
        when(meterLogDao.findById(any(Long.class))).thenReturn(null);

        MeterLog result = meterLogService.findById(5L);
        Assert.assertNull(result);
        verify(meterLogDao, times(1)).findById(5L);
    }

    @Test
    public void findAllTest() {
        when(meterLogDao.findAll()).thenReturn(new ArrayList<>(meterLogs));

        List<MeterLog> list = meterLogDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 5, "Length of the list should be same");
        verify(meterLogDao, times(1)).findAll();
    }

    @Test
    public void findAllEmptyTest() {
        when(meterLogDao.findAll()).thenReturn(new ArrayList<>());
        List<MeterLog> list = meterLogService.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0, "Length of the list should be same");
        verify(meterLogDao, times(1)).findAll();
    }

    @Test
    public void findByDateSingleTest() {
        MeterLog meterLog = meterLogAlone.get(0);
        when(meterLogDao.findByDate(meterLog.getLogDate())).thenReturn(new ArrayList<>(meterLogAlone));

        List<MeterLog> result = meterLogService.findByDate(meterLog.getLogDate());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLog);
        verify(meterLogDao, times(1)).findByDate(meterLog.getLogDate());
    }

    @Test
    public void findByDateMultipleTest() {
        when(meterLogDao.findByDate(sameDateMeterLogs1.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));

        List<MeterLog> result = meterLogService.findByDate(sameDateMeterLogs1.get(0).getLogDate());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0), sameDateMeterLogs1.get(0));
        Assert.assertEquals(result.get(1), sameDateMeterLogs1.get(1));
        verify(meterLogDao, times(1)).findByDate(sameDateMeterLogs1.get(0).getLogDate());
    }

    @Test
    public void findInDateFrameAllTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 5, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogs.get(0));
        Assert.assertEquals(result.get(1), meterLogs.get(1));
        Assert.assertEquals(result.get(2), meterLogs.get(2));
        Assert.assertEquals(result.get(3), meterLogs.get(3));
        Assert.assertEquals(result.get(4), meterLogs.get(4));
    }

    @Test
    public void findInDateFramePartTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(1).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0), sameDateMeterLogs1.get(0));
        Assert.assertEquals(result.get(1), sameDateMeterLogs1.get(1));
    }

    @Test
    public void findInDateFrameOneTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(4).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogAlone.get(0));
    }

    @Test
    public void findInDateFrameEmptyTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(4).getLogDate().plusDays(1);
        var endDate = meterLogs.get(4).getLogDate().plusDays(15);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0, "Length of the list should be same");
    }

    @Test
    public void findInDateFrameWithDayTimeAllDayTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3, "Length of the list should be same");
        Assert.assertEquals(result.get(0), sameDateMeterLogs1.get(1));
        Assert.assertEquals(result.get(1), sameDateMeterLogs2.get(1));
        Assert.assertEquals(result.get(2), meterLogAlone.get(0));
    }


    @Test
    public void findInDateFrameWithDayTimeAllNightTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0), sameDateMeterLogs1.get(0));
        Assert.assertEquals(result.get(1), sameDateMeterLogs2.get(0));
    }

    @Test
    public void findInDateFrameWithDayTimePartDayTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(3).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0), sameDateMeterLogs1.get(1));
        Assert.assertEquals(result.get(1), sameDateMeterLogs2.get(1));
    }

    @Test
    public void findInDateFrameWithDayTimePartNightTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs1));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(new ArrayList<>(sameDateMeterLogs2));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(new ArrayList<>(meterLogAlone));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(1).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0), sameDateMeterLogs1.get(0));
    }

    @Test
    public void createMeterLogTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        meterLogService.createMeterLog(meterLog);
        verify(meterLogDao, times(1)).create(meterLog);
    }

    @Test
    public void deleteMeterLogTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        meterLogService.deleteMeterLog(meterLog);
        verify(meterLogDao, times(1)).delete(meterLog);
    }

    @Test
    public void changeMeasurementTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        Long newMeasure = 25L;
        MeterLog result = meterLogService.changeMeasurement(meterLog, newMeasure);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getLogDate(), meterLog.getLogDate());
        Assert.assertEquals(result.getLogTime(), meterLog.getLogTime());
        Assert.assertEquals(result.getMeasure(), newMeasure);
        verify(meterLogDao, times(1)).update(result);
    }

    @Test
    public void changeDateTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        LocalDate newDate = LocalDate.of(2021, 1, 1);
        MeterLog result = meterLogService.changeDate(meterLog, newDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getLogDate(), newDate);
        Assert.assertEquals(result.getLogTime(), meterLog.getLogTime());
        Assert.assertEquals(result.getMeasure(), meterLog.getMeasure());
        verify(meterLogDao, times(1)).update(result);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void changeFutureDateTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        LocalDate newDate = LocalDate.of(2025, 1, 1);
        MeterLog result = meterLogService.changeDate(meterLog, newDate);
    }


    @Test
    public void changeTimeTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        LocalTime newTime = LocalTime.of(15, 30);
        MeterLog result = meterLogService.changeTime(meterLog, newTime);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getLogDate(), meterLog.getLogDate());
        Assert.assertEquals(result.getLogTime(), newTime);
        Assert.assertEquals(result.getMeasure(), meterLog.getMeasure());
        verify(meterLogDao, times(1)).update(result);
    }

    @Test
    public void isInDateFrameBasicTrueTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        var start = LocalDate.of(2020, 1, 1);
        var end = LocalDate.of(2020, 1, 1);

        boolean result = meterLogService.isInDateFrame(meterLog, start, end);
        Assert.assertTrue(result);
    }

    @Test
    public void isInDateFrameBasicFalseTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        var start = LocalDate.of(2020, 1, 2);
        var end = LocalDate.of(2020, 1, 3);

        boolean result = meterLogService.isInDateFrame(meterLog, start, end);
        Assert.assertFalse(result);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void isInDateFrameInvalidTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(0, 30));
        meterLog.setMeasure(123L);

        var start = LocalDate.of(2020, 1, 3);
        var end = LocalDate.of(2020, 1, 2);

        meterLogService.isInDateFrame(meterLog, start, end);
    }

    @Test
    public void isInDayTimeSoonDayTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(8, 0));
        meterLog.setMeasure(123L);

        boolean result = meterLogService.isInDayTime(meterLog, DayTime.Day);
        Assert.assertTrue(result);
        result = meterLogService.isInDayTime(meterLog, DayTime.Night);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDayTimeMiddleDayTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(15, 0));
        meterLog.setMeasure(123L);

        boolean result = meterLogService.isInDayTime(meterLog, DayTime.Day);
        Assert.assertTrue(result);
        result = meterLogService.isInDayTime(meterLog, DayTime.Night);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDayTimeLateDayTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(20, 59));
        meterLog.setMeasure(123L);

        boolean result = meterLogService.isInDayTime(meterLog, DayTime.Day);
        Assert.assertTrue(result);
        result = meterLogService.isInDayTime(meterLog, DayTime.Night);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDayTimeSoonNightTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(21, 0));
        meterLog.setMeasure(123L);

        boolean result = meterLogService.isInDayTime(meterLog, DayTime.Night);
        Assert.assertTrue(result);
        result = meterLogService.isInDayTime(meterLog, DayTime.Day);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDayTimeMiddleNightTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(3, 0));
        meterLog.setMeasure(123L);

        boolean result = meterLogService.isInDayTime(meterLog, DayTime.Night);
        Assert.assertTrue(result);
        result = meterLogService.isInDayTime(meterLog, DayTime.Day);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDayTimeLateNightTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(7, 59));
        meterLog.setMeasure(123L);

        boolean result = meterLogService.isInDayTime(meterLog, DayTime.Night);
        Assert.assertTrue(result);
        result = meterLogService.isInDayTime(meterLog, DayTime.Day);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDateFrameWithDayTimeDayTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(8, 0));
        meterLog.setMeasure(123L);

        var start = LocalDate.of(2020, 1, 1).minusDays(1);
        var end = LocalDate.of(2020, 1, 1).plusDays(1);
        boolean result = meterLogService.isInDateFrameWithDayTime(meterLog, start, end, DayTime.Day);
        Assert.assertTrue(result);
        result = meterLogService.isInDateFrameWithDayTime(meterLog, start, end, DayTime.Night);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDateFrameWithDayTimeNightTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(7, 59));
        meterLog.setMeasure(123L);

        var start = LocalDate.of(2020, 1, 1);
        var end = LocalDate.of(2020, 1, 1);
        boolean result = meterLogService.isInDateFrameWithDayTime(meterLog, start, end, DayTime.Night);
        Assert.assertTrue(result);
        result = meterLogService.isInDateFrameWithDayTime(meterLog, start, end, DayTime.Day);
        Assert.assertFalse(result);
    }

    @Test
    public void isInDateFrameWithDayTimeOutsideTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(7, 59));
        meterLog.setMeasure(123L);

        var start = LocalDate.of(2020, 1, 4);
        var end = LocalDate.of(2020, 1, 5);
        boolean result = meterLogService.isInDateFrameWithDayTime(meterLog, start, end, DayTime.Day);
        Assert.assertFalse(result);
        result = meterLogService.isInDateFrameWithDayTime(meterLog, start, end, DayTime.Night);
        Assert.assertFalse(result);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void isInDateFrameWithDayTimeInvalidTest() {
        MeterLog meterLog = new MeterLog();
        meterLog.setId(1L);
        meterLog.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog.setLogTime(LocalTime.of(7, 59));
        meterLog.setMeasure(123L);

        var start = LocalDate.of(2020, 1, 5);
        var end = LocalDate.of(2020, 1, 4);
        meterLogService.isInDateFrameWithDayTime(meterLog, start, end, DayTime.Day);
    }

    @Test
    public void filterInDateFrameAllTest() {
        var start = meterLogs.get(0).getLogDate();
        var end = meterLogs.get(4).getLogDate();
        List<MeterLog> result = meterLogService.filterInDateFrame(meterLogs, start, end);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 5, "Length of the list should be same");
    }

    @Test
    public void filterInDateFramePartTest() {
        var start = meterLogs.get(0).getLogDate().minusDays(1);
        var end = meterLogs.get(1).getLogDate();
        List<MeterLog> result = meterLogService.filterInDateFrame(new ArrayList<>(meterLogs), start, end);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogs.get(0));
        Assert.assertEquals(result.get(1), meterLogs.get(1));
    }

    @Test
    public void filterInDateFramePart2Test() {
        var start = meterLogs.get(2).getLogDate();
        var end = meterLogs.get(4).getLogDate();
        List<MeterLog> result = meterLogService.filterInDateFrame(new ArrayList<>(meterLogs), start, end);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogs.get(2));
        Assert.assertEquals(result.get(1), meterLogs.get(3));
        Assert.assertEquals(result.get(2), meterLogs.get(4));
    }

    @Test
    public void filterInDateFrameEmptyTest() {
        var start = meterLogs.get(4).getLogDate().plusDays(5);
        var end = meterLogs.get(4).getLogDate().plusDays(8);
        List<MeterLog> result = meterLogService.filterInDateFrame(meterLogs, start, end);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0, "Length of the list should be same");
    }

    @Test
    public void filterInDayTimeDayTest() {
        List<MeterLog> result = meterLogService.filterInDayTime(new ArrayList<>(meterLogs), DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogs.get(1));
        Assert.assertEquals(result.get(1), meterLogs.get(3));
        Assert.assertEquals(result.get(2), meterLogs.get(4));
    }

    @Test
    public void filterInDayTimeNightTest() {
        List<MeterLog> result = meterLogService.filterInDayTime(new ArrayList<>(meterLogs), DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogs.get(0));
        Assert.assertEquals(result.get(1), meterLogs.get(2));
    }

    @Test
    public void filterInDateFrameWithTimeDayPartDayTest() {
        var start = meterLogs.get(2).getLogDate();
        var end = meterLogs.get(3).getLogDate();
        List<MeterLog> result = meterLogService.filterInDateFrameWithTimeDay(new ArrayList<>(meterLogs), start, end, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogs.get(3));
    }

    @Test
    public void filterInDateFrameWithTimeDayPartNightTest() {
        var start = meterLogs.get(0).getLogDate();
        var end = meterLogs.get(1).getLogDate();
        List<MeterLog> result = meterLogService.filterInDateFrameWithTimeDay(new ArrayList<>(meterLogs), start, end, DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0), meterLogs.get(0));
    }

    @Test
    public void filterInDateFrameWithTimeDayEmptyTest() {
        var start = meterLogs.get(4).getLogDate().plusDays(5);
        var end = meterLogs.get(4).getLogDate().plusDays(8);
        List<MeterLog> result = meterLogService.filterInDateFrameWithTimeDay(new ArrayList<>(meterLogs), start, end, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0, "Length of the list should be same");
    }
}
