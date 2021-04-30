package cz.fi.muni.pa165.service.PortalUser;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.UserRoleDao;
import cz.fi.muni.pa165.entity.House;
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
    public Long registerUser(PortalUser portalUser, String unencryptedPassword) {
        if (unencryptedPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        UserRole userRole = userRoleDao.findByName(UserRole.USER_ROLE_NAME);
        portalUser.setUserRole(userRole);
        portalUser.setPasswordHash(passwordEncoder.encode(unencryptedPassword));

        portalUserDao.create(portalUser);
        return portalUser.getId();
    }

    @Override
    public Long registerAdministrator(PortalUser portalUser, String unencryptedPassword) {
        if (unencryptedPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        UserRole userRole = userRoleDao.findByName(UserRole.ADMINISTRATOR_ROLE_NAME);
        portalUser.setUserRole(userRole);
        portalUser.setPasswordHash(passwordEncoder.encode(unencryptedPassword));

        portalUserDao.create(portalUser);
        return portalUser.getId();
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
    public PortalUser findUserById(Long id) {
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
    public void addHouse(PortalUser portalUser, House house) {
        portalUser.addHouse(house);
    }

    @Override
    public void removeHouse(PortalUser portalUser, House house) {
        portalUser.removeHouse(house);
    }

    @Override
    public void deactivateUser(Long id) {
        PortalUser user = portalUserDao.findById(id);
        user.setActive(false);
        portalUserDao.update(user);
    }

    @Override
    public void reactivateUser(Long id) {
        PortalUser user = portalUserDao.findById(id);
        user.setActive(true);
        portalUserDao.update(user);
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        if (newPassword.isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty!");
        }
        PortalUser user = portalUserDao.findById(id);
        boolean doPasswordsMatch = authenticate(user, oldPassword);
        if (!doPasswordsMatch) {
            return false;
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        portalUserDao.update(user);
        return true;
    }

    @Override
    public void delete(PortalUser portalUser) {
        portalUserDao.delete(portalUser);
    }
}
