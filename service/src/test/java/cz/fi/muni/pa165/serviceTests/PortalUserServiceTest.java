package cz.fi.muni.pa165.serviceTests;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.dao.UserRoleDao;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.service.serviceInterface.PortalUserService;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * @author Matej Rišňovský
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PortalUserServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private PortalUserDao portalUserDao;

    @Mock
    private UserRoleDao userRoleDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    @InjectMocks
    private PortalUserService portalUserService;

    private UserRole userRoleAdmin;
    private UserRole userRoleUser;
    private PortalUser user1;
    private PortalUser user2;
    private House house1;
    private House house2;
    private Address address1;
    private Address address2;
    private List<PortalUser> userList;

    private static final String PASSWORD_HASH = "dsqd54331123sds";
    private static final String NEW_PASSWORD_HASH = "dsqd54331123###";

    @BeforeMethod
    public void prepare() {
        userRoleAdmin = new UserRole();
        userRoleAdmin.setRoleName(UserRole.ADMINISTRATOR_ROLE_NAME);

        userRoleUser = new UserRole();
        userRoleUser.setRoleName(UserRole.USER_ROLE_NAME);

        user1 = new PortalUser();
        user1.setId(1L);
        user1.setFirstName("Firstname");
        user1.setLastName("Lastname");
        user1.setEmail("test@muni.cz");
        user1.setActive(true);

        user2 = new PortalUser();
        user2.setId(2L);
        user2.setFirstName("Firstname2");
        user2.setLastName("Lastname2");
        user2.setEmail("test2@muni.cz");
        user2.setActive(true);

        address1 = new Address();
        address1.setCountry("Slovakia");
        address1.setCode("SK");
        address1.setCity("Mesto");
        address1.setStreet("Hlavna");
        address1.setPostCode("96501");

        address2 = new Address();
        address2.setCountry("Cech Republic");
        address2.setCode("CZ");
        address2.setCity("Brno");
        address2.setStreet("Vedlajsia");
        address2.setPostCode("61200");

        house1 = new House();
        house1.setName("Test house1");
        house1.setRunning(true);
        house1.setAddress(address1);
        house1.setPortalUser(user1);

        house2 = new House();
        house2.setName("Test house2");
        house2.setRunning(true);
        house2.setAddress(address2);
        house2.setPortalUser(user2);

        userList = new ArrayList<PortalUser>();
        userList.add(user1);
        userList.add(user2);

        when(passwordEncoder.encode(any(String.class))).thenReturn(PASSWORD_HASH);
    }


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    void clearInvocations() {
        Mockito.clearInvocations(portalUserDao, userRoleDao, passwordEncoder);
    }

    @Test
    public void registerUserTest() {
        when(userRoleDao.findByName(UserRole.USER_ROLE_NAME)).thenReturn(userRoleUser);
        portalUserService.registerUser(user1, "12345678");

        Assert.assertEquals(user1.getUserRole(), userRoleUser);
        Assert.assertEquals(user1.getPasswordHash(), PASSWORD_HASH);

        verify(passwordEncoder, times(1)).encode(any(String.class));
        verify(userRoleDao, times(1)).findByName(UserRole.USER_ROLE_NAME);
        verify(portalUserDao, times(1)).create(any(PortalUser.class));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void registerNullUserTest() {
        portalUserService.registerUser(null, "12345678");
        verify(portalUserDao, times(0)).create(any(PortalUser.class));
        verify(passwordEncoder, times(0)).encode(any(String.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registerUserEmptyPasswordTest() {
        when(userRoleDao.findByName(UserRole.USER_ROLE_NAME)).thenReturn(userRoleUser);
        portalUserService.registerUser(user1, "");

        verify(userRoleDao, times(0)).findByName(UserRole.USER_ROLE_NAME);
        verify(portalUserDao, times(0)).create(any(PortalUser.class));
        verify(passwordEncoder, times(0)).encode(any(String.class));
    }

    @Test
    public void registerAdminTest() {
        when(userRoleDao.findByName(UserRole.ADMINISTRATOR_ROLE_NAME)).thenReturn(userRoleAdmin);
        portalUserService.registerAdministrator(user2, "12345678");

        Assert.assertEquals(user2.getUserRole(), userRoleAdmin);
        Assert.assertEquals(user2.getPasswordHash(), PASSWORD_HASH);
        verify(userRoleDao, times(1)).findByName(UserRole.ADMINISTRATOR_ROLE_NAME);
        verify(portalUserDao, times(1)).create(any(PortalUser.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void registerNullAdminTest() {
        portalUserService.registerAdministrator(null, "12345678");
        verify(portalUserDao, times(0)).create(any(PortalUser.class));
        verify(passwordEncoder, times(0)).encode(any(String.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registerAdminEmptyPasswordTest() {
        portalUserService.registerUser(user2, "");

        verify(userRoleDao, times(0)).findByName(UserRole.ADMINISTRATOR_ROLE_NAME);
        verify(portalUserDao, times(0)).create(any(PortalUser.class));
        verify(passwordEncoder, times(0)).encode(any(String.class));
    }

    @Test
    public void getAllUsersTest() {
        when(portalUserDao.findAll()).thenReturn(userList);
        List<PortalUser> users = portalUserService.getAllUsers();

        Assert.assertTrue(users.size() == userList.size());
        Assert.assertTrue(users.get(0).getId().equals(user1.getId()) && users.get(1).getId().equals(user2.getId()));
        verify(portalUserDao, times(1)).findAll();
    }

    @Test
    public void getAllUsersOneUserTest() {
        when(portalUserDao.findAll()).thenReturn(Arrays.asList(user1));

        List<PortalUser> users = portalUserService.getAllUsers();

        Assert.assertTrue(users.size() == 1);
        Assert.assertTrue(users.get(0).getId().equals(user1.getId()));
        verify(portalUserDao, times(1)).findAll();
    }

    @Test
    public void getAllUsersNoneUserTest() {
        when(portalUserDao.findAll()).thenReturn(new ArrayList<>());

        List<PortalUser> users = portalUserService.getAllUsers();

        Assert.assertTrue(users.size() == 0);
        verify(portalUserDao, times(1)).findAll();
    }

    @Test
    public void authenticateFalseTest() {
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(false);
        user1.setPasswordHash(PASSWORD_HASH);
        boolean success = portalUserService.authenticate(user1, "12345678");

        Assert.assertEquals(success, false);
        verify(passwordEncoder, times(1)).matches(any(CharSequence.class), any(String.class));
    }

    @Test
    public void authenticateTrueTest() {
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(true);
        user1.setPasswordHash(PASSWORD_HASH);
        boolean success = portalUserService.authenticate(user1, "12345678");

        Assert.assertEquals(success, true);
        verify(passwordEncoder, times(1)).matches(any(CharSequence.class), any(String.class));
    }

    @Test
    public void findByIdTest() {
        when(portalUserDao.findById(any(Long.class))).thenReturn(user1);
        PortalUser user = portalUserService.findUserById(Long.valueOf(1L));

        Assert.assertEquals(user.getId(), user1.getId());
        verify(portalUserDao, times(1)).findById(any(Long.class));
    }

    @Test
    public void findByIdNotFoundTest() {
        when(portalUserDao.findById(any(Long.class))).thenReturn(null);
        PortalUser user = portalUserService.findUserById(Long.valueOf(1L));

        Assert.assertEquals(user, null);
        verify(portalUserDao, times(1)).findById(any(Long.class));
    }

    @Test
    public void findByEmailTest() {
        when(portalUserDao.findByUserName(any(String.class))).thenReturn(user1);
        PortalUser user = portalUserService.findUserByEmail("test@muni.cz");

        Assert.assertEquals(user.getEmail(), user1.getEmail());
        verify(portalUserDao, times(1)).findByUserName(any(String.class));
    }

    @Test
    public void findByEmailNotFoundTest() {
        when(portalUserDao.findByUserName(any(String.class))).thenReturn(null);
        PortalUser user = portalUserService.findUserByEmail("test@muni.cz");

        Assert.assertEquals(user, null);
        verify(portalUserDao, times(1)).findByUserName(any(String.class));
    }

    @Test
    public void updateBasicInfoTest() {
        portalUserService.updateBasicUserInfo(user1);
        verify(portalUserDao, times(1)).update(any(PortalUser.class));
    }

    @Test
    public void deleteTest() {
        portalUserService.delete(user1);
        verify(portalUserDao, times(1)).delete(any(PortalUser.class));
    }

    @Test
    public void addHouseTest() {
        portalUserService.addHouse(user1, house1);
        Assert.assertTrue(user1.getHouses().contains(house1));

        portalUserService.addHouse(user1, house2);
        Assert.assertTrue(user1.getHouses().contains(house1) && user1.getHouses().contains(house2));
    }

    @Test
    public void removeHouseTest() {
        house1.setPortalUser(user1);
        user1.addHouse(house1);

        portalUserService.removeHouse(user1, house1);
        Assert.assertTrue(user1.getHouses().isEmpty());
    }

    @Test
    public void removeNotAddedHouseTest() {
        Assert.assertTrue(user1.getHouses().isEmpty());
        portalUserService.removeHouse(user1, house1);

        Assert.assertTrue(user1.getHouses().isEmpty());
    }

    @Test
    public void deactiveUserTest() {
        when(portalUserDao.findById(1L)).thenReturn(user1);
        user1.setActive(true);
        portalUserService.deactivateUser(1L);

        Assert.assertFalse(user1.isActive());
        verify(portalUserDao, times(1)).update(user1);
    }

    @Test
    public void deactiveUserAlreadyDeactivatedTest() {
        when(portalUserDao.findById(1L)).thenReturn(user1);
        user1.setActive(false);
        portalUserService.deactivateUser(1L);

        Assert.assertFalse(user1.isActive());
        verify(portalUserDao, times(1)).update(user1);
    }

    @Test
    public void activateUserTest() {
        when(portalUserDao.findById(1L)).thenReturn(user1);
        user1.setActive(false);
        portalUserService.reactivateUser(1L);

        Assert.assertTrue(user1.isActive());
        verify(portalUserDao, times(1)).update(user1);
        verify(portalUserDao, times(1)).findById(1L);
    }

    @Test
    public void activateUserAlreadyActivatedTest() {
        when(portalUserDao.findById(1L)).thenReturn(user1);
        user1.setActive(true);
        portalUserService.reactivateUser(1L);

        Assert.assertTrue(user1.isActive());
        verify(portalUserDao, times(1)).update(user1);
        verify(portalUserDao, times(1)).findById(1L);
    }

    @Test
    public void changePasswordMatchingPasswordsTest() {
        when(portalUserDao.findById(1L)).thenReturn(user1);
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(true);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(NEW_PASSWORD_HASH);

        user1.setPasswordHash(PASSWORD_HASH);
        boolean success = portalUserService.changePassword(1L, "12345678", "987654321");

        Assert.assertTrue(success);
        Assert.assertEquals(user1.getPasswordHash(), NEW_PASSWORD_HASH);
        verify(portalUserDao, times(1)).update(user1);
        verify(portalUserDao, times(1)).findById(1L);
    }

    @Test
    public void changePasswordNotMatchingPasswordsTest() {
        when(portalUserDao.findById(1L)).thenReturn(user1);
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(false);

        user1.setPasswordHash(PASSWORD_HASH);
        boolean success = portalUserService.changePassword(1L, "12345678", "987654321");

        Assert.assertFalse(success);
        Assert.assertEquals(user1.getPasswordHash(), PASSWORD_HASH);
        verify(portalUserDao, times(0)).update(user1);
        verify(portalUserDao, times(1)).findById(1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void changePasswordEmptyPasswordTest() {
        boolean success = portalUserService.changePassword(1L, "12345678", "");

        Assert.assertFalse(success);
        verify(portalUserDao, times(0)).update(any(PortalUser.class));
        verify(portalUserDao, times(0)).findById(any(Long.class));
    }
}
