package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserSampleData {
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
        Long id = 1L;
        UserRole role = new UserRole();
        role.setRoleName(UserRole.USER_ROLE_NAME);
        PortalUser user = new PortalUser();
        user.setUserRole(role);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setId(id);
        user.setPasswordHash(passwordHash);
        this.user1 = user;
        return user;
    }

    public PortalUser generateUser2() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2020, 8, 15), LocalTime.of(10, 30, 59));
        String email = "test2.user@muni.cz";
        String passwordHash = "#*##45e";
        String firstName = "FirstName2";
        String lastName = "LastName2";
        String phone = "+999111999";
        Long id = 2L;
        UserRole role = new UserRole();
        role.setRoleName(UserRole.ADMINISTRATOR_ROLE_NAME);
        PortalUser user = new PortalUser();
        user.setUserRole(role);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setId(id);
        user.setPasswordHash(passwordHash);
        this.user2 = user;
        return user;
    }
}
