package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.sampleData.MeterLogSampleData;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Michaela Horváthová
 */
@Transactional
@ContextConfiguration(classes= ServiceConfiguration.class)
public class HouseFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private HouseFacade houseFacade;

    @Autowired
    private MeterLogSampleData meterLogSampleData;

    @Autowired
    private PortalUserDao portalUserDao;



    @BeforeClass
    private void generateData() {
        meterLogSampleData.generateData();
    }

    @Test
    public void test() {
        portalUserDao.findAll();
    }

}
