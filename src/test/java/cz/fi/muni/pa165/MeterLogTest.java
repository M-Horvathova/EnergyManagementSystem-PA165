package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.entity.MeterLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class MeterLogTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MeterLogDao mtldao;

    @Test
    public void basicTest(){
        MeterLog ml = new MeterLog();
        ml.setLogDate(LocalDate.of(2021, 1, 23));
        ml.setLogTime(LocalTime.of(15, 30));
        ml.setMeasure(123L);

        mtldao.create(ml);


    }

}
