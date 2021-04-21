package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Patrik Valo
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MeterLogTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MeterLogDao meterLogDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void basicCreateTest() {
        var date = LocalDate.of(2021, 1, 23);
        var time = LocalTime.of(15, 30);
        Long measure = 123L;

        MeterLog meterLog = new MeterLog();
        meterLog.setLogDate(date);
        meterLog.setLogTime(time);
        meterLog.setMeasure(measure);

        meterLogDao.create(meterLog);

        MeterLog result = findEntityInDb(meterLog.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getLogDate(), date);
        Assert.assertEquals(result.getLogTime(), time);
        Assert.assertEquals(result.getMeasure(), measure);
    }


    @Test
    public void nullableDateCreateTest() {
        var time = LocalTime.of(15, 30);
        Long measure = 123L;

        MeterLog meterLog = new MeterLog();
        meterLog.setLogTime(time);
        meterLog.setMeasure(measure);

        meterLogDao.create(meterLog);
    }

    @Test
    public void nullableTimeCreateTest() {
        var date = LocalDate.of(2021, 1, 23);
        Long measure = 123L;

        MeterLog meterLog = new MeterLog();
        meterLog.setLogDate(date);
        meterLog.setMeasure(measure);

        meterLogDao.create(meterLog);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void nullableMeasureCreateTest() {
        var date = LocalDate.of(2021, 1, 23);
        var time = LocalTime.of(15, 30);

        MeterLog meterLog = new MeterLog();
        meterLog.setLogDate(date);
        meterLog.setLogTime(time);

        meterLogDao.create(meterLog);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setFutureDateTest() {
        var date = LocalDate.now().plusDays(1);

        MeterLog meterLog = new MeterLog();
        meterLog.setLogDate(date);
    }

    @Test
    public void deleteTest() {
        var date = LocalDate.of(2021, 1, 15);
        var time = LocalTime.of(7, 0);
        Long measure = 1L;

        MeterLog meterLog = createEntityMeterLog(date, time, measure);

        meterLogDao.delete(meterLog);
        MeterLog resultAfterDelete = findEntityInDb(meterLog.getId());
        Assert.assertNull(resultAfterDelete);
    }

    @Test
    public void updateDateTest() {
        var date = LocalDate.of(2021, 1, 15);
        var time = LocalTime.of(7, 0);
        Long measure = 1L;

        MeterLog meterLog = createEntityMeterLog(date, time, measure);

        var newDate = LocalDate.of(2020, 1, 16);
        meterLog.setLogDate(newDate);
        meterLogDao.update(meterLog);
        MeterLog resultAfterUpdateDate = findEntityInDb(meterLog.getId());
        Assert.assertEquals(resultAfterUpdateDate.getLogDate(), newDate);
    }


    @Test
    public void updateTimeTest() {
        var date = LocalDate.of(2021, 1, 15);
        var time = LocalTime.of(7, 0);
        Long measure = 1L;

        MeterLog meterLog = createEntityMeterLog(date, time, measure);

        var newTime = LocalTime.of(7, 0);
        meterLog.setLogTime(newTime);
        meterLogDao.update(meterLog);
        MeterLog resultAfterUpdateTime = findEntityInDb(meterLog.getId());
        Assert.assertEquals(resultAfterUpdateTime.getLogTime(), newTime);
    }

    @Test
    public void updateMeasureTest() {
        var date = LocalDate.of(2021, 1, 15);
        var time = LocalTime.of(7, 0);
        Long measure = 1L;

        MeterLog meterLog = createEntityMeterLog(date, time, measure);

        Long newMeasure = 5L;
        meterLog.setMeasure(newMeasure);
        meterLogDao.update(meterLog);
        MeterLog resultAfterUpdateMeasure = findEntityInDb(meterLog.getId());
        Assert.assertEquals(resultAfterUpdateMeasure.getMeasure(), newMeasure);
    }

    @Test
    public void findMeterLogTest() {
        var date = LocalDate.of(2016, 12, 15);
        var time = LocalTime.of(0, 0);
        Long measure = 11L;

        MeterLog meterLog = createEntityMeterLog(date, time, measure);

        MeterLog result = meterLogDao.findMeterLog(meterLog);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getLogDate(), date);
        Assert.assertEquals(result.getLogTime(), time);
        Assert.assertEquals(result.getMeasure(), measure);
    }

    @Test
    public void findNotExistingMeterLogTest() {
        var date = LocalDate.of(2016, 12, 15);
        var time = LocalTime.of(0, 0);
        Long measure = 11L;

        MeterLog meterLog = createEntityMeterLog(date, time, measure);
        meterLogDao.delete(meterLog);

        MeterLog result = meterLogDao.findMeterLog(meterLog);
        Assert.assertNull(result);
    }

    @Test
    public void findByIdTest() {
        var date = LocalDate.of(2016, 12, 31);
        var time = LocalTime.of(0, 30);
        Long measure = 8L;

        MeterLog meterLog = createEntityMeterLog(date, time, measure);
        // create another entity
        createEntityMeterLog(LocalDate.of(2018, 12, 31), LocalTime.of(0, 30), 1L);

        MeterLog result = meterLogDao.findById(meterLog.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getLogDate(), date);
        Assert.assertEquals(result.getLogTime(), time);
        Assert.assertEquals(result.getMeasure(), measure);
    }

    @Test
    public void findByNotExistingIdTest() {
        MeterLog result = meterLogDao.findById(16L);
        Assert.assertNull(result);
    }

    @Test
    public void findAllTest() {
        var date1 = LocalDate.of(2016, 12, 31);
        var time1 = LocalTime.of(0, 30);
        Long measure1 = 8L;
        createEntityMeterLog(date1, time1, measure1);

        var date2 = LocalDate.of(2021, 2, 11);
        var time2 = LocalTime.of(12, 30);
        Long measure2 = 1L;
        createEntityMeterLog(date2, time2, measure2);

        var date3 = LocalDate.of(2018, 5, 5);
        var time3 = LocalTime.of(18, 15);
        Long measure3 = 88L;
        createEntityMeterLog(date3, time3, measure3);

        List<MeterLog> list = meterLogDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 3, "Length of the list should be same");
    }

    @Test
    public void findAllEmptyTest() {
        List<MeterLog> list = meterLogDao.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0, "Length of the list should be same");
    }

    @Test
    public void findByDateSingleTest() {
        List<MeterLog> list = createEntitiesForFindByDateTime();

        MeterLog first = list.get(0);
        List<MeterLog> result = meterLogDao.findByDate(first.getLogDate());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), first.getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), first.getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), first.getMeasure());
    }

    @Test
    public void findByDateMultipleTest() {
        List<MeterLog> list = createEntitiesForFindByDateTime();

        // these has same values of date
        List<MeterLog> lastTwo = list.subList(list.size() - 2, list.size());

        List<MeterLog> result = meterLogDao.findByDate(lastTwo.get(0).getLogDate());
        result.sort(Comparator.comparing(MeterLog::getId));
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), lastTwo.get(0).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), lastTwo.get(0).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), lastTwo.get(0).getMeasure());
        Assert.assertEquals(result.get(1).getLogDate(), lastTwo.get(1).getLogDate());
        Assert.assertEquals(result.get(1).getLogTime(), lastTwo.get(1).getLogTime());
        Assert.assertEquals(result.get(1).getMeasure(), lastTwo.get(1).getMeasure());
    }

    @Test
    public void findByDateExtremeMultipleTest() {
        List<MeterLog> list = createEntitiesForFindByDateTime();
        list = list.subList(1, 7);

        List<MeterLog> result = meterLogDao.findByDate(LocalDate.of(2021, 3, 5));
        result.sort(Comparator.comparing(MeterLog::getId));
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 6, "Length of the list should be same");
        for (int i = 0; i < 6; i++) {
            Assert.assertEquals(result.get(i).getLogDate(), list.get(i).getLogDate());
            Assert.assertEquals(result.get(i).getLogTime(), list.get(i).getLogTime());
            Assert.assertEquals(result.get(i).getMeasure(), list.get(i).getMeasure());
        }
    }

    @Test
    public void findByDateEmptyTest() {
        createEntitiesForFindByDateTime();

        List<MeterLog> result = meterLogDao.findByDate(LocalDate.of(2021, 3, 11));

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0, "Length of the list should be same");
    }

    @Test
    public void findByTimeOfDayDayTest() {
        List<MeterLog> list = createEntitiesForFindByDateTime();

        // day cases which have same date
        MeterLog[] dayArray = {list.get(2), list.get(3), list.get(4)};
        List<MeterLog> dayList = new ArrayList<>(Arrays.asList(dayArray));

        List<MeterLog> result = meterLogDao.findByTimeOfDay(dayList.get(0).getLogDate(), DayTime.Day);
        result.sort(Comparator.comparing(MeterLog::getId));
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), dayList.get(0).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), dayList.get(0).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), dayList.get(0).getMeasure());
        Assert.assertEquals(result.get(1).getLogDate(), dayList.get(1).getLogDate());
        Assert.assertEquals(result.get(1).getLogTime(), dayList.get(1).getLogTime());
        Assert.assertEquals(result.get(1).getMeasure(), dayList.get(1).getMeasure());
        Assert.assertEquals(result.get(2).getLogDate(), dayList.get(2).getLogDate());
        Assert.assertEquals(result.get(2).getLogTime(), dayList.get(2).getLogTime());
        Assert.assertEquals(result.get(2).getMeasure(), dayList.get(2).getMeasure());
    }

    @Test
    public void findByTimeOfDayNightTest() {
        List<MeterLog> list = createEntitiesForFindByDateTime();

        // night cases which have same date
        MeterLog[] nightArray = {list.get(1), list.get(5), list.get(6)};
        List<MeterLog> nightList = new ArrayList<>(Arrays.asList(nightArray));

        List<MeterLog> result = meterLogDao.findByTimeOfDay(nightList.get(0).getLogDate(), DayTime.Night);
        result.sort(Comparator.comparing(MeterLog::getId));
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 3, "Length of the list should be same");
        Assert.assertEquals(result.get(0).getLogDate(), nightList.get(0).getLogDate());
        Assert.assertEquals(result.get(0).getLogTime(), nightList.get(0).getLogTime());
        Assert.assertEquals(result.get(0).getMeasure(), nightList.get(0).getMeasure());
        Assert.assertEquals(result.get(1).getLogDate(), nightList.get(1).getLogDate());
        Assert.assertEquals(result.get(1).getLogTime(), nightList.get(1).getLogTime());
        Assert.assertEquals(result.get(1).getMeasure(), nightList.get(1).getMeasure());
        Assert.assertEquals(result.get(2).getLogDate(), nightList.get(2).getLogDate());
        Assert.assertEquals(result.get(2).getLogTime(), nightList.get(2).getLogTime());
        Assert.assertEquals(result.get(2).getMeasure(), nightList.get(2).getMeasure());
    }

    @Test
    public void findByTimeOfDayEmptyTest() {
        createEntitiesForFindByDateTime();

        List<MeterLog> resultDay = meterLogDao.findByTimeOfDay(LocalDate.of(2021, 3, 11), DayTime.Day);
        List<MeterLog> resultNight = meterLogDao.findByTimeOfDay(LocalDate.of(2021, 3, 11), DayTime.Night);

        Assert.assertNotNull(resultDay);
        Assert.assertEquals(resultDay.size(), 0, "Length of the list should be same");
        Assert.assertNotNull(resultNight);
        Assert.assertEquals(resultNight.size(), 0, "Length of the list should be same");
    }

    private MeterLog findEntityInDb(Long id) {
        return em.find(MeterLog.class, id);
    }

    private MeterLog createEntityMeterLog(LocalDate date, LocalTime time, Long measure) {
        MeterLog meterLog = new MeterLog();
        meterLog.setLogDate(date);
        meterLog.setLogTime(time);
        meterLog.setMeasure(measure);
        em.persist(meterLog);
        return meterLog;
    }

    private List<MeterLog> createEntitiesForFindByDateTime() {
        List<MeterLog> list = new ArrayList<>();

        var date1 = LocalDate.of(2021, 1, 1);
        var time1 = LocalTime.of(0, 0);
        Long measure1 = 0L;
        list.add(createEntityMeterLog(date1, time1, measure1));

        var date2 = LocalDate.of(2021, 3, 5);
        var time2 = LocalTime.of(7, 59);
        Long measure2 = 1L;
        list.add(createEntityMeterLog(date2, time2, measure2));

        var date3 = LocalDate.of(2021, 3, 5);
        var time3 = LocalTime.of(8, 0);
        Long measure3 = 88L;
        list.add(createEntityMeterLog(date3, time3, measure3));

        var date4 = LocalDate.of(2021, 3, 5);
        var time4 = LocalTime.of(13, 30);
        Long measure4 = 20L;
        list.add(createEntityMeterLog(date4, time4, measure4));

        var date5 = LocalDate.of(2021, 3, 5);
        var time5 = LocalTime.of(20, 59);
        Long measure5 = 2L;
        list.add(createEntityMeterLog(date5, time5, measure5));

        var date6 = LocalDate.of(2021, 3, 5);
        var time6 = LocalTime.of(21, 0);
        Long measure6 = 4L;
        list.add(createEntityMeterLog(date6, time6, measure6));

        var date7 = LocalDate.of(2021, 3, 5);
        var time7 = LocalTime.of(23, 0);
        Long measure7 = 8L;
        list.add(createEntityMeterLog(date7, time7, measure7));

        var date8 = LocalDate.of(2021, 3, 10);
        var time8 = LocalTime.of(23, 59);
        Long measure8 = 3L;
        list.add(createEntityMeterLog(date8, time8, measure8));


        var date9 = LocalDate.of(2021, 3, 10);
        var time9 = LocalTime.of(13, 0);
        Long measure9 = 2L;
        list.add(createEntityMeterLog(date9, time9, measure9));

        return list;
    }
}
