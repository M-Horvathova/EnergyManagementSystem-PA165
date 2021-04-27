package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.UserRoleDao;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class UserSampleData {

    private PortalUserDao portalUserDao;
    private UserRoleDao userRoleDao;

    @Autowired
    public UserSampleData(PortalUserDao portalUserDao, UserRoleDao userRoleDao) {
        this.portalUserDao = portalUserDao;
        this.userRoleDao = userRoleDao;
    }

    public PortalUser generateUser1() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.of(23, 59, 59));
        String email = "test.user@muni.cz";
        String passwordHash = "#*##23e";
        String firstName = "FirstName";
        String lastName = "LastName";
        String phone = "+999111999";
        UserRole role = new UserRole();
        role.setRoleName(UserRole.USER_ROLE_NAME);
        userRoleDao.create(role);

        PortalUser user = new PortalUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(role);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);
        portalUserDao.create(user);
        return user;
    }
}
