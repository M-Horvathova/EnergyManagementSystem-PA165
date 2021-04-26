package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.sampleData.HouseSampleData;
import cz.fi.muni.pa165.sampleData.MeterLogSampleData;
import cz.fi.muni.pa165.sampleData.UserSampleData;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes= ServiceConfiguration.class)
public class HouseFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private HouseFacade houseFacade;

    @Autowired
    private MeterLogSampleData meterLogSampleData;

    @Autowired
    private SmartMeterDao smartMeterDao;


    private HouseDTO houseDTO;

    @Test
    public void prepareData() {
        MeterLog m1 = meterLogSampleData.getMeterLog100();
        SmartMeter sm1 = smartMeterDao.findById(1L);
        System.out.println(sm1.getId());
        System.out.println(sm1.getMeterLogs());

    }

}
