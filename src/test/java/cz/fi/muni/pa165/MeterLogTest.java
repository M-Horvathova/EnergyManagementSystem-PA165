package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.entity.MeterLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Patrik Valo
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class MeterLogTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MeterLogDao meterLogDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @AfterMethod
    public void afterTest() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from MeterLog").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

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

    @Test(expectedExceptions = JpaSystemException.class)
    public void nullableDateCreateTest() {
        var time = LocalTime.of(15, 30);
        Long measure = 123L;

        MeterLog meterLog = new MeterLog();
        meterLog.setLogTime(time);
        meterLog.setMeasure(measure);

        meterLogDao.create(meterLog);
    }

    @Test(expectedExceptions = JpaSystemException.class)
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

    private MeterLog findEntityInDb(Long id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            MeterLog result = em.find(MeterLog.class, id);
            em.getTransaction().commit();

            return result;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private MeterLog createEntityMeterLog(LocalDate date, LocalTime time, Long measure) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            MeterLog meterLog = new MeterLog();
            meterLog.setLogDate(date);
            meterLog.setLogTime(time);
            meterLog.setMeasure(measure);
            em.persist(meterLog);

            em.getTransaction().commit();
            return meterLog;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
