package cz.fi.muni.pa165.sampleData;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@Transactional
public class SampleDataFacadeImpl implements SampleDataFacade{

    @Autowired
    private AddressSampleData addressSampleData;
    @Autowired
    private UserRoleSampleData userRoleSampleData;
    @Autowired
    private UserSampleData userSampleData;
    @Autowired
    private HouseSampleData houseSampleData;



    @Override
    public void loadData() throws IOException {
        addressSampleData.generateAddresses();
        userRoleSampleData.generateRoles();
        userSampleData.generateUsers();
        houseSampleData.generateHouses();
    }
}
