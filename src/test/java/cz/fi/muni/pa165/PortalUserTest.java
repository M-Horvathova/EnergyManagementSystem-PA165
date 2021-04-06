package cz.fi.muni.pa165;

import cz.fi.muni.pa165.dao.PortalUserDao;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.enums.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Matej Rišňovský
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class PortalUserTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PortalUserDao portalUserDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private LocalDateTime dateTime;
    private String email;
    private String phone;
    private UserRole userRole;
    private String passwordHash;
    private String firstName;
    private String lastName;

    @BeforeClass
    public void Init() {
        // For user generation use PortalUserGenerator if possible!

        dateTime = LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.of(23, 59,59));
        email = "test.user@muni.cz";
        userRole = UserRole.user;
        passwordHash = "#*##23e";
        firstName = "FirstName";
        lastName = "LastName";
        phone = "+999111999";
    }

    @AfterMethod
    public void afterTest() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from PortalUser").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    public void createBasicTest() {
        PortalUser user = new PortalUser();
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        portalUserDao.create(user);

        PortalUser dbUser = findUserInDB(user.getId());

        Assert.assertNotNull(dbUser);
        Assert.assertNotNull(dbUser.getId());
        Assert.assertNotEquals(dbUser.getId(), 0);

        Assert.assertEquals(dbUser.getCreatedTimestamp(), dateTime);
        Assert.assertEquals(dbUser.getEmail(), email);
        Assert.assertEquals(dbUser.getFirstName(), firstName);
        Assert.assertEquals(dbUser.getLastName(), lastName);
        Assert.assertEquals(dbUser.getUserRole(), userRole);
        Assert.assertEquals(dbUser.getPhone(), phone);
        Assert.assertEquals(dbUser.getPasswordHash(), passwordHash);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNullEmailTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setPhone(phone);

        portalUserDao.create(user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNonUniqueEmailTest() {
        PortalUser user1 = new PortalUser();

        user1.setFirstName(firstName);
        user1.setLastName(lastName);
        user1.setUserRole(userRole);
        user1.setCreatedTimestamp(dateTime);
        user1.setPasswordHash(passwordHash);
        user1.setEmail(email);
        user1.setPhone(phone);

        portalUserDao.create(user1);

        PortalUser user2 = new PortalUser();

        user2.setFirstName("First");
        user2.setLastName("Last");
        user2.setUserRole(userRole);
        user2.setCreatedTimestamp(dateTime);
        user2.setPasswordHash("43244dsf32");
        user2.setEmail(email);
        user2.setPhone("+420111222");

        portalUserDao.create(user2);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void createInvalidEmailTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail("@muni.cz");
        user.setPhone(phone);

        portalUserDao.create(user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNullPhoneTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);

        portalUserDao.create(user);

        PortalUser dbUser = findUserInDB(user.getId());

        Assert.assertNotNull(dbUser);
        Assert.assertNotNull(dbUser.getId());
        Assert.assertNull(dbUser.getPhone());
        Assert.assertNotEquals(dbUser.getId(), 0);

        Assert.assertEquals(dbUser.getCreatedTimestamp(), dateTime);
        Assert.assertEquals(dbUser.getEmail(), email);
        Assert.assertEquals(dbUser.getFirstName(), firstName);
        Assert.assertEquals(dbUser.getLastName(), lastName);
        Assert.assertEquals(dbUser.getUserRole(), userRole);
        Assert.assertEquals(dbUser.getPasswordHash(), passwordHash);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void createInvalidPhoneTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone("+999/111 999");

        portalUserDao.create(user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNullPasswordHashTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setEmail(email);
        user.setPhone(phone);

        portalUserDao.create(user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNullFirstNameTest() {
        PortalUser user = new PortalUser();

        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordHash);

        portalUserDao.create(user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNullLastNameTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordHash);

        portalUserDao.create(user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNullUserRoleTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreatedTimestamp(dateTime);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordHash);

        portalUserDao.create(user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createNullCreatedTimeStampTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordHash);

        portalUserDao.create(user);
    }

    @Test
    public void deleteTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);
        
        PortalUser dbUser = createUserInDB(user);

        portalUserDao.delete(user);

        dbUser = findUserInDB(dbUser.getId());
        Assert.assertNull(dbUser);
    }

    @Test
    public void updatePasswordHashTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);
        user.setLastName(lastName);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setPasswordHash("213ewdssdf");

        portalUserDao.update(dbUser);
        dbUser = findUserInDB(dbUser.getId());

        Assert.assertNotEquals(dbUser.getPasswordHash(), passwordHash);
        Assert.assertEquals(dbUser.getPasswordHash(), "213ewdssdf");
    }

    @Test
    public void updateEmailTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setEmail("test.dbUser@muni.cz");

        portalUserDao.update(dbUser);
        dbUser = findUserInDB(dbUser.getId());

        Assert.assertNotEquals(dbUser.getEmail(), email);
        Assert.assertEquals(dbUser.getEmail(), "test.dbUser@muni.cz");
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void updateInvalidEmailTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setEmail("@muni.cz");

        portalUserDao.update(dbUser);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void updateNonUniqueEmailTest() {
        PortalUser user1 = new PortalUser();

        user1.setFirstName(firstName);
        user1.setLastName(lastName);
        user1.setUserRole(userRole);
        user1.setCreatedTimestamp(dateTime);
        user1.setPasswordHash(passwordHash);
        user1.setEmail(email);
        user1.setPhone(phone);

        createUserInDB(user1);

        PortalUser user2 = new PortalUser();

        user2.setFirstName("First");
        user2.setLastName("Last");
        user2.setUserRole(userRole);
        user2.setCreatedTimestamp(dateTime);
        user2.setPasswordHash("43244dsf32");
        user2.setEmail("test.user2@muni.cz");
        user2.setPhone("+420111222");

        user2 = createUserInDB(user2);

        user2.setEmail(email);

        portalUserDao.update(user2);
    }

    @Test
    public void updatePhoneTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setPhone("111999111");

        portalUserDao.update(dbUser);
        dbUser = findUserInDB(dbUser.getId());

        Assert.assertNotEquals(dbUser.getPhone(), phone);
        Assert.assertEquals(dbUser.getPhone(), "111999111");
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void updateInvalidPhoneTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setPhone("++111999111");

        portalUserDao.update(dbUser);
    }

    @Test
    public void updateFirstNameTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setFirstName("Test");

        portalUserDao.update(dbUser);
        dbUser = findUserInDB(dbUser.getId());

        Assert.assertNotEquals(dbUser.getFirstName(), firstName);
        Assert.assertEquals(dbUser.getFirstName(), "Test");
    }

    @Test
    public void updateLastNameTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setLastName("TestLast");

        portalUserDao.update(dbUser);
        dbUser = findUserInDB(dbUser.getId());

        Assert.assertNotEquals(dbUser.getLastName(), lastName);
        Assert.assertEquals(dbUser.getLastName(), "TestLast");
    }

    @Test
    public void updateUserRoleTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        dbUser.setUserRole(UserRole.administrator);

        portalUserDao.update(dbUser);
        dbUser = findUserInDB(dbUser.getId());

        Assert.assertNotEquals(dbUser.getUserRole(), userRole);
        Assert.assertEquals(dbUser.getUserRole(), UserRole.administrator);
    }

    @Test
    public void findUserByIdTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        PortalUser foundUser = portalUserDao.findById(dbUser.getId());

        Assert.assertNotNull(foundUser);
        Assert.assertEquals(foundUser.getId(), dbUser.getId());
        Assert.assertEquals(foundUser, dbUser);
    }

    @Test
    public void findUserByNotExistingIdTest() {
        PortalUser dbUser = portalUserDao.findById(0L);
        Assert.assertNull(dbUser);
    }

    @Test
    public void findUserByUsernameTest() {
        PortalUser user = new PortalUser();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserRole(userRole);
        user.setCreatedTimestamp(dateTime);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        user.setPhone(phone);

        PortalUser dbUser = createUserInDB(user);

        PortalUser foundUser = portalUserDao.findByUserName(dbUser.getEmail());

        Assert.assertNotNull(foundUser);
        Assert.assertEquals(foundUser.getEmail(), dbUser.getEmail());
        Assert.assertEquals(foundUser, dbUser);
    }

    @Test
    public void findUserByNotExistingUsernameTest() {
        PortalUser dbUser = portalUserDao.findByUserName("@invalidUsername");
        Assert.assertNull(dbUser);
    }

    @Test
    public void findAllTest() {
        PortalUser user1 = new PortalUser();

        user1.setFirstName(firstName);
        user1.setLastName(lastName);
        user1.setUserRole(userRole);
        user1.setCreatedTimestamp(dateTime);
        user1.setPasswordHash(passwordHash);
        user1.setEmail(email);
        user1.setPhone(phone);

        createUserInDB(user1);

        PortalUser user2 = new PortalUser();

        user2.setFirstName("First");
        user2.setLastName("Last");
        user2.setUserRole(userRole);
        user2.setCreatedTimestamp(dateTime);
        user2.setPasswordHash("43244dsf32");
        user2.setEmail("test.use2@muni.cz");
        user2.setPhone("+420111222");

        createUserInDB(user2);

        List<PortalUser> users = portalUserDao.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 2);

        for (PortalUser user : users) {
            Assert.assertNotNull(user);
        }
    }

    @Test
    public void findAllEmptyTest() {
        List<PortalUser> users = portalUserDao.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 0);
    }

    private PortalUser findUserInDB(Long id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            PortalUser result = em.find(PortalUser.class, id);
            em.getTransaction().commit();
            return result;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private PortalUser createUserInDB(PortalUser user) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
