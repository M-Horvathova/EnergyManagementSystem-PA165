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

        // 3.1.2020
        // day
        MeterLog meterLog4 = new MeterLog();
        meterLog4.setId(5L);
        meterLog4.setLogDate(LocalDate.of(2020, 1, 3));
        meterLog4.setLogTime(LocalTime.of(12, 0));
        meterLog4.setMeasure(15L);

        meterLogs = Arrays.asList(meterLog0, meterLog1, meterLog2, meterLog3, meterLog4);
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
        when(meterLogDao.findAll()).thenReturn(meterLogs);

        List<MeterLog> list = meterLogDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 5, "Length of the list should be same");
        Assert.assertNotNull(list);
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
        MeterLog meterLog = meterLogs.get(4);
        when(meterLogDao.findByDate(meterLog.getLogDate())).thenReturn(Collections.singletonList(meterLog));

        List<MeterLog> result = meterLogService.findByDate(meterLog.getLogDate());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), meterLog.getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), meterLog.getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), meterLog.getMeasure());
        verify(meterLogDao, times(1)).findByDate(meterLog.getLogDate());
    }

    @Test
    public void findByDateMultipleTest() {
        List<MeterLog> sameDateMeterLogs = Arrays.asList(meterLogs.get(0), meterLogs.get(1));
        when(meterLogDao.findByDate(sameDateMeterLogs.get(0).getLogDate())).thenReturn(sameDateMeterLogs);

        List<MeterLog> result = meterLogService.findByDate(sameDateMeterLogs.get(0).getLogDate());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), sameDateMeterLogs.get(0).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), sameDateMeterLogs.get(0).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), sameDateMeterLogs.get(0).getMeasure());
        Assert.assertEquals(result.get(1).getLogDate(), sameDateMeterLogs.get(1).getLogDate());
        Assert.assertEquals(result.get(1).getLogTime(), sameDateMeterLogs.get(1).getLogTime());
        Assert.assertEquals(result.get(1).getMeasure(), sameDateMeterLogs.get(1).getMeasure());
        verify(meterLogDao, times(1)).findByDate(sameDateMeterLogs.get(0).getLogDate());
    }

    @Test
    public void findInDateFrameAllTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 5, "Length of the list should be same");
    }

    @Test
    public void findInDateFramePartTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(1).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), meterLogs.get(0).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), meterLogs.get(0).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), meterLogs.get(0).getMeasure());
        Assert.assertEquals(result.get(1).getLogDate(), meterLogs.get(1).getLogDate());
        Assert.assertEquals(result.get(1).getLogTime(), meterLogs.get(1).getLogTime());
        Assert.assertEquals(result.get(1).getMeasure(), meterLogs.get(1).getMeasure());
    }

    @Test
    public void findInDateFrameOneTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(4).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), meterLogs.get(4).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), meterLogs.get(4).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), meterLogs.get(4).getMeasure());
    }

    @Test
    public void findInDateFrameEmptyTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(4).getLogDate().plusDays(1);
        var endDate = meterLogs.get(4).getLogDate().plusDays(15);
        List<MeterLog> result = meterLogService.findInDateFrame(startDate, endDate);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0, "Length of the list should be same");
    }

    @Test
    public void findInDateFrameWithDayTimeAllDayTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3, "Length of the list should be same");
    }


    @Test
    public void findInDateFrameWithDayTimeAllNightTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
    }

    @Test
    public void findInDateFrameWithDayTimePartDayTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(3).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
    }

    @Test
    public void findInDateFrameWithDayTimePartNightTest() {
        when(meterLogDao.findByDate(meterLogs.get(0).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(0), meterLogs.get(1)));
        when(meterLogDao.findByDate(meterLogs.get(2).getLogDate()))
                .thenReturn(Arrays.asList(meterLogs.get(2), meterLogs.get(3)));
        when(meterLogDao.findByDate(meterLogs.get(4).getLogDate()))
                .thenReturn(Collections.singletonList(meterLogs.get(4)));

        var startDate = meterLogs.get(0).getLogDate();
        var endDate = meterLogs.get(1).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.findInDateFrameWithDayTime(startDate, endDate, DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), meterLogs.get(0).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), meterLogs.get(0).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), meterLogs.get(0).getMeasure());
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

        var start = LocalDate.of(2020, 1, 1).minusDays(1);
        var end = LocalDate.of(2020, 1, 1).plusDays(1);

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

        var start = LocalDate.of(2020, 1, 1).minusDays(1);
        var end = LocalDate.of(2020, 1, 1).plusDays(1);
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
        var start = meterLogs.get(0).getLogDate().minusDays(1);
        var end = meterLogs.get(4).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.filterInDateFrame(meterLogs, start, end);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 5, "Length of the list should be same");
    }

    @Test
    public void filterInDateFramePartTest() {
        var start = meterLogs.get(0).getLogDate().minusDays(1);
        var end = meterLogs.get(1).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.filterInDateFrame(meterLogs, start, end);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
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
        List<MeterLog> result = meterLogService.filterInDayTime(meterLogs, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3, "Length of the list should be same");
    }

    @Test
    public void filterInDayTimeNightTest() {
        List<MeterLog> result = meterLogService.filterInDayTime(meterLogs, DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
    }

    @Test
    public void filterInDateFrameWithTimeDayPartDayTest() {
        var start = meterLogs.get(2).getLogDate().minusDays(1);
        var end = meterLogs.get(3).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.filterInDateFrameWithTimeDay(meterLogs, start, end, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), meterLogs.get(3).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), meterLogs.get(3).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), meterLogs.get(3).getMeasure());
    }

    @Test
    public void filterInDateFrameWithTimeDayPartNightTest() {
        var start = meterLogs.get(0).getLogDate().minusDays(1);
        var end = meterLogs.get(1).getLogDate().plusDays(1);
        List<MeterLog> result = meterLogService.filterInDateFrameWithTimeDay(meterLogs, start, end, DayTime.Night);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), meterLogs.get(0).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), meterLogs.get(0).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), meterLogs.get(0).getMeasure());
    }

    @Test
    public void filterInDateFrameWithTimeDayEmptyTest() {
        var start = meterLogs.get(4).getLogDate().plusDays(5);
        var end = meterLogs.get(4).getLogDate().plusDays(8);
        List<MeterLog> result = meterLogService.filterInDateFrameWithTimeDay(meterLogs, start, end, DayTime.Day);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0, "Length of the list should be same");
    }
}
