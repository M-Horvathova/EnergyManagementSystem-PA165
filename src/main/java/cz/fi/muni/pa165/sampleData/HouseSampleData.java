package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

@Service
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class HouseSampleData {
    @Autowired
    private HouseDao houseDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private PortalUserDao portalUserDao;


    private House house10;
    private House house20;

    public House generateHouse10() {
        House house = new House();
        house.setName("Test house");
        house.setRunning(true);
        PortalUser u = portalUserDao.findByUserName("test.user@muni.cz");
        house.setPortalUser(u);
        Address address = addressDao.find("Hrázní", "35", "Brno", "Czech Republic", "654321");
        house.setAddress(address);
        this.house10 = house;
        houseDao.create(house);
        return house;
    }

    public House generateHouse20() {
        House house = new House();
        house.setName("Test house2");
        house.setRunning(false);
        PortalUser u = portalUserDao.findByUserName("user2.myemail@gmail.com");
        house.setPortalUser(u);
        Address address = addressDao.find("Botanická", "15", "Brno", "Czech Republic", "123456");
        house.setAddress(address);
        this.house20 = house;
        houseDao.create(house);
        return house;
    }

    public House getHouse10() {
        if (house10 == null) {
            return generateHouse10();
        }
        return house10;
    }

    public void setHouse10(House house10) {
        this.house10 = house10;
    }

    public House getHouse20() {
        if (house20 == null) {
            return generateHouse20();
        }
        return house20;
    }

    public void setHouse20(House house20) {
        this.house20 = house20;
    }

    public void generateHouses() {
        generateHouse10();
        generateHouse20();
    }
}
