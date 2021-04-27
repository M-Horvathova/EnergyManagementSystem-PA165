package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.UserRoleDao;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class UserSampleData {
    @Autowired
    private PortalUserDao portalUserDao;

    @Autowired
    private UserRoleDao userRoleDao;

    private PortalUser user1;

    private PortalUser user2;

    public void setUser1(PortalUser user1) {
        this.user1 = user1;
    }

    public void setUser2(PortalUser user2) {
        this.user2 = user2;
    }

    public PortalUser getUser1() {
        if (user1 == null) {
            return generateUser1();
        }
        return user1;
    }

    public PortalUser getUser2() {
        if (user2 == null) {
            return generateUser2();
        }
        return user2;
    }

    public PortalUser generateUser1() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.of(23, 59, 59));
        String email = "test.user@muni.cz";
        String passwordHash = "#*##23e";
        String firstName = "FirstName";
        String lastName = "LastName";
        String phone = "+999111999";
        UserRole role = userRoleDao.findByName(UserRole.USER_ROLE_NAME);
        PortalUser user = new PortalUser();
        user.setUserRole(role);
        user.setActive(true);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setPasswordHash(passwordHash);
        user.setCreatedTimestamp(dateTime);
        this.user1 = user;
        portalUserDao.create(user);
        return user;
    }

    public PortalUser generateUser2() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2020, 8, 15), LocalTime.of(10, 30, 59));
        String email = "user2.myemail@gmail.com";
        String passwordHash = "#*##45e";
        String firstName = "FirstName2";
        String lastName = "LastName2";
        String phone = "+999111999";
        UserRole role = userRoleDao.findByName(UserRole.ADMINISTRATOR_ROLE_NAME);
        PortalUser user = new PortalUser();
        user.setUserRole(role);
        user.setCreatedTimestamp(dateTime);
        user.setActive(false);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setPasswordHash(passwordHash);
        this.user2 = user;
        portalUserDao.create(user);
        return user;
    }

    public void generateUsers() {
        generateUser1();
        generateUser2();
    }
}
