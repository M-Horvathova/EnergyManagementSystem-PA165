package cz.fi.muni.pa165.service.PortalUser;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.UserRoleDao;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class PortalUserServiceImpl implements PortalUserService {
    private PortalUserDao portalUserDao;

    private UserRoleDao userRoleDao;

    private PasswordEncoder passwordEncoder;

    public PortalUserServiceImpl(
            PortalUserDao portaluserDao,
            UserRoleDao userRoleDao,
            PasswordEncoder passwordEncoder) {
        this.portalUserDao = portaluserDao;
        this.userRoleDao = userRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(PortalUser portalUser, String unencryptedPassword) {
        UserRole userRole = userRoleDao.findByName(UserRole.USER_ROLE_NAME);
        portalUser.setUserRole(userRole);
        portalUser.setPasswordHash(passwordEncoder.encode(unencryptedPassword));

        portalUserDao.create(portalUser);
    }

    public void registerAdministrator(PortalUser portalUser, String unencryptedPassword) {
        UserRole userRole = userRoleDao.findByName(UserRole.ADMINISTRATOR_ROLE_NAME);
        portalUser.setPasswordHash(passwordEncoder.encode(unencryptedPassword));

        portalUserDao.create(portalUser);
    }

    public List<PortalUser> getAllUsers() {
        return portalUserDao.findAll();
    }

    public boolean authenticate(PortalUser portalUser, String password) {
        return passwordEncoder.matches(password, portalUser.getPasswordHash());
    }

    public PortalUser findUserById(long id) {
        return portalUserDao.findById(id);
    }

    public PortalUser findUserByEmail(String email) {
        return portalUserDao.findByUserName(email);
    }

    public void updateBasicUserInfo(PortalUser portalUser) {
        portalUserDao.update(portalUser);
    }

    public void deactivateUser(long id) {
        PortalUser user = portalUserDao.findById(id);
        user.setActive(false);
        portalUserDao.update(user);
    }

    public void reactivateUser(long id) {
        PortalUser user = portalUserDao.findById(id);
        user.setActive(true);
        portalUserDao.update(user);
    }

    public void changePassword(long id, String oldPassword, String newPassword) {
        PortalUser user = portalUserDao.findById(id);
        boolean doPasswordsMatch = authenticate(user, oldPassword);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        portalUserDao.update(user);
    }

    public void delete(PortalUser portalUser) {
        portalUserDao.delete(portalUser);
    }
}
