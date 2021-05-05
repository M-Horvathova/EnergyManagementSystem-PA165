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
        log.info("Loading user roles.");
        loadUserRoles();
        log.info("User roles loaded");
        log.info("Loading users");
        PortalUser user = user("John", "Smith", "smith@gmail.com",
                "111222333", true, LocalDateTime.of(2020, 12, 1, 1, 15),
                LocalDateTime.of(2021, 1, 1, 1, 15));
        PortalUser admin = admin("Betty", "White", "bw@seznam.cz",
                "999888777", true, LocalDateTime.of(2020, 12, 1, 1, 15),
                LocalDateTime.of(2021, 1, 1, 1, 15));

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


}
