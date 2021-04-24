package cz.fi.muni.pa165.service.PortalUser;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.UserRoleDao;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martin Podhora
 */
@Service
public class PortalUserServiceImpl implements PortalUserService {
    private PortalUserDao portalUserDao;

    private UserRoleDao userRoleDao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PortalUserServiceImpl(
            PortalUserDao portaluserDao,
            UserRoleDao userRoleDao,
            PasswordEncoder passwordEncoder) {
        this.portalUserDao = portaluserDao;
        this.userRoleDao = userRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(PortalUser portalUser, String unencryptedPassword) {
        UserRole userRole = userRoleDao.findByName(UserRole.USER_ROLE_NAME);
        portalUser.setUserRole(userRole);
        portalUser.setPasswordHash(passwordEncoder.encode(unencryptedPassword));

        portalUserDao.create(portalUser);
    }

    @Override
    public void registerAdministrator(PortalUser portalUser, String unencryptedPassword) {
        UserRole userRole = userRoleDao.findByName(UserRole.ADMINISTRATOR_ROLE_NAME);
        portalUser.setPasswordHash(passwordEncoder.encode(unencryptedPassword));

        portalUserDao.create(portalUser);
    }

    @Override
    public List<PortalUser> getAllUsers() {
        return portalUserDao.findAll();
    }

    @Override
    public boolean authenticate(PortalUser portalUser, String password) {
        return passwordEncoder.matches(password, portalUser.getPasswordHash());
    }

    @Override
    public PortalUser findUserById(long id) {
        return portalUserDao.findById(id);
    }

    @Override
    public PortalUser findUserByEmail(String email) {
        return portalUserDao.findByUserName(email);
    }

    @Override
    public void updateBasicUserInfo(PortalUser portalUser) {
        portalUserDao.update(portalUser);
    }

    @Override
    public void deactivateUser(long id) {
        PortalUser user = portalUserDao.findById(id);
        user.setActive(false);
        portalUserDao.update(user);
    }

    @Override
    public void reactivateUser(long id) {
        PortalUser user = portalUserDao.findById(id);
        user.setActive(true);
        portalUserDao.update(user);
    }

    @Override
    public void changePassword(long id, String oldPassword, String newPassword) {
        PortalUser user = portalUserDao.findById(id);
        boolean doPasswordsMatch = authenticate(user, oldPassword);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        portalUserDao.update(user);
    }

    @Override
    public void delete(PortalUser portalUser) {
        portalUserDao.delete(portalUser);
    }
}