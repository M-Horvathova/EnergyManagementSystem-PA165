package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.MeterLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class playTests extends AbstractTestNGSpringContextTests {

    @Autowired
    MeterLogService mls;

    @PersistenceUnit
    private EntityManagerFactory emf;


    @Test
    public void playTestMLS() {
        var date = LocalDate.of(2021, 1, 23);
        var time = DayTime.Day.getStart();
        Long measure = 123L;

        MeterLog meterLog = new MeterLog();
        meterLog.setLogDate(date);
        meterLog.setLogTime(time);
        meterLog.setMeasure(measure);
        mls.createMeterLog(meterLog);

        MeterLog meterLog2 = new MeterLog();
        meterLog2.setLogDate(LocalDate.of(2020, 1, 23));
        meterLog2.setLogTime(time);
        meterLog2.setMeasure(measure);
        mls.createMeterLog(meterLog2);

        MeterLog meterLog3 = new MeterLog();
        meterLog3.setLogDate(LocalDate.of(2021, 1, 22));
        meterLog3.setLogTime(time);
        meterLog3.setMeasure(measure);
        mls.createMeterLog(meterLog3);

        List<MeterLog> ml = new ArrayList<>();
        ml.add(meterLog);
        ml.add(meterLog2);
        ml.add(meterLog3);

        List<MeterLog> result = mls.filterInDateFrameWithTimeDay(ml, LocalDate.of(2020, 10, 23), LocalDate.of(2021, 10, 23), DayTime.Day);
        result.sort(Comparator.comparing(MeterLog::getId));
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0).getLogDate(), LocalDate.of(2021, 1, 23));
        Assert.assertEquals(result.get(1).getLogDate(), LocalDate.of(2021, 1, 22));


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

}
