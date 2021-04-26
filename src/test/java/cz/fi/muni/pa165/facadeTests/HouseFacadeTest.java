package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.sampleData.HouseSampleData;
import cz.fi.muni.pa165.sampleData.UserSampleData;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

/**
 * @author Michaela Horváthová
 */
@ContextConfiguration(classes= ServiceConfiguration.class)
public class HouseFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private HouseFacade houseFacade;

    private PortalUser user;
    private House house;
    private HouseDTO houseDTO;

    public void prepareData() {
        UserSampleData userSampleData = new UserSampleData();
        HouseSampleData houseSampleData = new HouseSampleData();
        this.user = userSampleData.getUser1();
        this.house = houseSampleData.generateHouse10();


    }

}
