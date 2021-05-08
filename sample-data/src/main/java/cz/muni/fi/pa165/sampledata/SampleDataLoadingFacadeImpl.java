package cz.muni.fi.pa165.sampledata;

import cz.fi.muni.pa165.dao.UserRoleDao;
import cz.fi.muni.pa165.service.*;
import cz.fi.muni.pa165.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Michaela Hrováthová
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade{
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    private final MeterLogService meterLogService;
    private final SmartMeterService smartMeterService;
    private final HouseService houseService;
    private final PortalUserService portalUserService;
    private final AddressService addressService;
    private final UserRoleDao userRoleDao;

    @Autowired
    public SampleDataLoadingFacadeImpl(MeterLogService meterLogService, SmartMeterService smartMeterService, HouseService houseService, PortalUserService portalUserService, AddressService addressService, UserRoleDao userRoleDao) {
        this.meterLogService = meterLogService;
        this.smartMeterService = smartMeterService;
        this.houseService = houseService;
        this.portalUserService = portalUserService;
        this.addressService = addressService;
        this.userRoleDao = userRoleDao;
    }


    @SuppressWarnings("unused")
    @Override
    public void loadData() throws IOException {
        log.info("Starting to load sample data");
        log.info("Loading user roles");
        loadUserRoles();
        log.info("User roles loaded");

        log.info("Loading users");
        PortalUser user1 = user("John", "Smith", "smith@gmail.com",
                "111222333", true, LocalDateTime.of(2020, 12, 1, 1, 15),
                LocalDateTime.of(2021, 1, 1, 1, 15));
        PortalUser user2 = user("Tereza", "Nováková", "tnovakova@centrum.cz",
                "555555555", true, LocalDateTime.of(2019, 8, 1, 1, 15),
                LocalDateTime.of(2021, 1, 3, 1, 15));
        PortalUser admin = admin("Betty", "White", "bw@seznam.cz",
                "999888777", true, LocalDateTime.of(2020, 12, 1, 1, 15),
                LocalDateTime.of(2021, 1, 1, 1, 15));
        portalUserService.registerUser(user1, "password");
        portalUserService.registerUser(user2, "mojeheslo");
        portalUserService.registerAdministrator(admin, "admin");
        log.info("Users loaded");

        log.info("Loading addresses");
        Address address1 = address("Botanická", "15", "Brno", "63511", "CZ");
        Address address2 = address(null, "35", "Kuklík", "70522", "CZ");
        Address address3 = address("Hlavná", "1", "Košice", "1245", "SK");
        log.info("Addresses loaded");

        log.info("Loading houses");
        House house1 = house(address1, "My house", true, user1);
        House house2 = house(address2, "Parent's apartment", false, user1);
        House house3 = house(address3, "My studio", false, user2);
        House house4 = house(address2, "My apartment", true, user2);
        log.info("Houses loaded");

        log.info("Loading smart meters");
        SmartMeter smartMeter1 = smartMeter(true, 0, 0, house1);
        SmartMeter smartMeter2 = smartMeter(true, 10, 150, house2);
        SmartMeter smartMeter3 = smartMeter(false, 0, 0, house3);
        SmartMeter smartMeter4 = smartMeter(true, 3, 15, house1);
        log.info("Smart meters loaded");

        log.info("Loading meter logs");
        MeterLog meterLog1 = meterLog((long) -5, smartMeter1, LocalDateTime.of(2020, 8, 15, 15, 35));
        MeterLog meterLog2 = meterLog(5L, smartMeter1, LocalDateTime.of(2020, 8, 15, 18, 35));
        MeterLog meterLog3 = meterLog(10L, smartMeter2, LocalDateTime.of(2021, 1, 1, 23, 59));
        MeterLog meterLog4 = meterLog(11L, smartMeter2, LocalDateTime.of(2021, 1, 2, 23, 59));
        MeterLog meterLog5 = meterLog(39L, smartMeter2, LocalDateTime.of(2021, 1, 3, 23,59));
        MeterLog meterLog6 = meterLog(25L, smartMeter2, LocalDateTime.of(2021, 1, 4, 23, 59));
        MeterLog meterLog7 = meterLog(35L, smartMeter2, LocalDateTime.of(2021, 1, 5, 23,59));
        MeterLog meterLog8 = meterLog(30L, smartMeter2, LocalDateTime.of(2021, 1, 6, 23, 59));
        MeterLog meterLog9 = meterLog(9L, smartMeter4, LocalDateTime.of(2021, 2, 15, 8, 25));
        MeterLog meterLog10 = meterLog(2L, smartMeter4, LocalDateTime.of(2021, 2, 9, 6, 12));
        MeterLog meterLog11 = meterLog(4L, smartMeter4, LocalDateTime.of(2021, 2, 22, 13, 54));
        log.info("Meter logs loaded");
        log.info("Sample data loading complete");
    }


    private void loadUserRoles() {
        UserRole user = new UserRole();
        user.setRoleName(UserRole.USER_ROLE_NAME);
        this.userRoleDao.create(user);

        UserRole admin = new UserRole();
        admin.setRoleName(UserRole.ADMINISTRATOR_ROLE_NAME);
        this.userRoleDao.create(admin);
    }


    private PortalUser fillUser(String firstName,
                                String lastName, String email, String phone,
                                boolean active, LocalDateTime created,
                                LocalDateTime lastLogged) {
        PortalUser u = new PortalUser();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPhone(phone);
        u.setActive(active);
        u.setCreatedTimestamp(created);
        u.setLastLoginTimestamp(lastLogged);
        return u;
    }

    private PortalUser user(String firstName,
                            String lastName, String email, String phone,
                            boolean active, LocalDateTime created,
                            LocalDateTime lastLogged) {
        PortalUser u = fillUser(firstName, lastName, email, phone,
            active, created, lastLogged);
        portalUserService.registerUser(u, "user");
        return u;
    }

    private PortalUser admin(String firstName,
                             String lastName, String email, String phone,
                             boolean active, LocalDateTime created,
                             LocalDateTime lastLogged) {
        PortalUser u = fillUser(firstName, lastName, email, phone,
                active, created, lastLogged);
        portalUserService.registerAdministrator(u, "admin");
        return u;
    }

    private Address address(String street, String code,
                            String city, String postCode, String country) {
        Address a = new Address();
        a.setStreet(street);
        a.setCode(code);
        a.setCity(city);
        a.setCountry(country);
        a.setPostCode(postCode);
        addressService.createAddress(a);
        return a;
    }

    private House house(Address address, String name, Boolean running, PortalUser user) {
        House h = new House();
        h.setAddress(address);
        h.setName(name);
        h.setRunning(running);
        h.setPortalUser(user);
        houseService.createHouse(h);
        return h;
    }

    private SmartMeter smartMeter(Boolean isRunning, double power, double cumulativePower, House house) {
        SmartMeter sm = new SmartMeter();
        sm.setRunning(isRunning);
        sm.setPowerConsumptionSinceLastLog(power);
        sm.setCumulativePowerConsumption(cumulativePower);
        sm.setHouse(house);
        smartMeterService.create(sm);
        return sm;
    }

    private MeterLog meterLog(Long measure, SmartMeter smartMeter, LocalDateTime stamp) {
        MeterLog ml = new MeterLog();
        ml.setMeasure(measure);
        ml.setSmartMeter(smartMeter);
        ml.setCreateStamp(stamp);
        meterLogService.createMeterLog(ml);
        return ml;
    }


}
