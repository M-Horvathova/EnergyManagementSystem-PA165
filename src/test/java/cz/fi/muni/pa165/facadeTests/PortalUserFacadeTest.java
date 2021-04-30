package cz.fi.muni.pa165.facadeTests;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserAuthenticateDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserChangePasswordDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserHouseDTO;
import cz.fi.muni.pa165.entity.*;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.facade.MeterLogFacade;
import cz.fi.muni.pa165.facade.MeterLogFacadeImpl;
import cz.fi.muni.pa165.facade.PortalUser.PortalUserFacade;
import cz.fi.muni.pa165.facade.PortalUser.PortalUserFacadeImpl;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.PortalUser.PortalUserService;
import cz.fi.muni.pa165.service.PortalUser.PortalUserServiceImpl;
import cz.fi.muni.pa165.service.SmartMeterService;
import cz.fi.muni.pa165.service.config.BeanMappingConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * @author Matej Rišňovský
 */
@ContextConfiguration(classes = BeanMappingConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PortalUserFacadeTest extends AbstractTestNGSpringContextTests
{
    @Mock
    private PortalUserService portalUserService;

    @Mock
    private BeanMappingService beanMappingService;

    private PortalUserFacade portalUserFacade;

    private PortalUserAuthenticateDTO portalUserAuthenticateDTO;
    private PortalUserChangePasswordDTO portalUserChangePasswordDTO;
    private PortalUserDTO portalUserDTO1;
    private PortalUserDTO portalUserDTO2;
    private PortalUser user1;
    private PortalUser user2;
    private List<PortalUser> userList;
    private UserRole userRoleAdmin;
    private UserRole userRoleUser;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeMethod
    public void prepare() {
        portalUserAuthenticateDTO = new PortalUserAuthenticateDTO();
        portalUserAuthenticateDTO.setUserName("test@muni.cz");
        portalUserAuthenticateDTO.setPassword("Password");

        portalUserChangePasswordDTO = new PortalUserChangePasswordDTO();
        portalUserChangePasswordDTO.setOldPassword("Password");
        portalUserChangePasswordDTO.setNewPassword("NewPassword");
        portalUserChangePasswordDTO.setId(1L);

        portalUserDTO1 = new PortalUserDTO();
        portalUserDTO1.setFirstName("Firstname");
        portalUserDTO1.setLastName("Lastname");
        portalUserDTO1.setEmail("test@muni.cz");
        portalUserDTO1.setId(1L);

        portalUserDTO2 = new PortalUserDTO();
        portalUserDTO2.setFirstName("Firstname2");
        portalUserDTO2.setLastName("Lastname2");
        portalUserDTO2.setEmail("test2@muni.cz");
        portalUserDTO2.setId(2L);

        userRoleAdmin = new UserRole();
        userRoleAdmin.setRoleName(UserRole.ADMINISTRATOR_ROLE_NAME);

        userRoleUser = new UserRole();
        userRoleUser.setRoleName(UserRole.USER_ROLE_NAME);

        user1 = new PortalUser();
        user1.setId(1L);
        user1.setEmail("test@muni.cz");
        user1.setUserRole(userRoleUser);

        user2 = new PortalUser();
        user2.setId(2L);
        user1.setEmail("test2@muni.cz");
        user2.setUserRole(userRoleAdmin);

        userList = new ArrayList<PortalUser>();
        userList.add(user1);
        userList.add(user2);

        portalUserFacade = new PortalUserFacadeImpl(portalUserService, beanMappingService);

        when(portalUserService.findUserByEmail(any(String.class))).thenReturn(user1);
        when(portalUserService.findUserById(any(Long.class))).thenReturn(user1);
    }

    @AfterMethod
    void clearInvocations() {
        Mockito.clearInvocations(portalUserService);
    }


    @Test
    public void registerUserTest() {
        when(portalUserService.registerUser(any(PortalUser.class), any(String.class))).thenReturn(user1.getId());
        portalUserFacade.registerUser(portalUserDTO1, "12345678");

        Assert.assertEquals(portalUserDTO1.getId(), user1.getId());
        verify(portalUserService, times(1)).registerUser(any(PortalUser.class), any(String.class));
    }

    @Test
    public void registerAdminTest() {
        when(portalUserService.registerAdministrator(any(PortalUser.class), any(String.class))).thenReturn(user2.getId());
        portalUserFacade.registerAdministrator(portalUserDTO2, "12345678");

        Assert.assertEquals(portalUserDTO2.getId(), user2.getId());
        verify(portalUserService, times(1)).registerAdministrator(any(PortalUser.class), any(String.class));
    }

    @Test
    public void getAllUsersTest() {
        when(portalUserService.getAllUsers()).thenReturn(userList);
        List<PortalUserDTO> userDTOS = portalUserFacade.getAllUsers();

        Assert.assertTrue(userDTOS.size() == userList.size());
        Assert.assertTrue(userDTOS.get(0).getId() == user1.getId() && userDTOS.get(1).getId() == user2.getId());
        verify(portalUserService, times(1)).getAllUsers();
    }

    @Test
    public void authenticateFalseTest() {
        when(portalUserService.authenticate(any(PortalUser.class), any(String.class))).thenReturn(false);
        boolean success = portalUserFacade.authenticate(portalUserAuthenticateDTO);

        Assert.assertFalse(success);
        verify(portalUserService, times(1)).findUserByEmail(any(String.class));
        verify(portalUserService, times(1)).authenticate(any(PortalUser.class), any(String.class));
    }

    @Test
    public void authenticateTrueTest() {
        when(portalUserService.authenticate(any(PortalUser.class), any(String.class))).thenReturn(true);
        boolean success = portalUserFacade.authenticate(portalUserAuthenticateDTO);

        Assert.assertTrue(success);
        verify(portalUserService, times(1)).findUserByEmail(any(String.class));
        verify(portalUserService, times(1)).authenticate(any(PortalUser.class), any(String.class));
    }

    @Test
    public void findByIdTest() {
        PortalUserDTO userDTO = portalUserFacade.findUserById(Long.valueOf(1L));

        Assert.assertEquals(userDTO.getId(), user1.getId());
        verify(portalUserService, times(1)).findUserById(any(Long.class));
    }

    @Test
    public void findByEmailTest() {
        PortalUserDTO userDTO = portalUserFacade.findUserByEmail("test@muni.cz");

        Assert.assertEquals(userDTO.getEmail(), user1.getEmail());
        verify(portalUserService, times(1)).findUserByEmail(any(String.class));
    }

    @Test
    public void updateBasicInfoTest() {
        portalUserFacade.updateBasicUserInfo(portalUserDTO1);

        verify(portalUserService, times(1)).findUserById(any(Long.class));
        verify(portalUserService, times(1)).updateBasicUserInfo(any(PortalUser.class));
    }

    @Test
    public void deleteTest() {
        portalUserFacade.delete(1L);

        verify(portalUserService, times(1)).findUserById(any(Long.class));
        verify(portalUserService, times(1)).delete(any(PortalUser.class));
    }

    @Test
    public void deactiveUserTest() {
        portalUserFacade.deactivateUser(1L);
        verify(portalUserService, times(1)).deactivateUser(any(Long.class));
    }

    @Test
    public void activateUserTest() {
        portalUserFacade.reactivateUser(1L);
        verify(portalUserService, times(1)).reactivateUser(any(Long.class));
    }

    @Test
    public void changePasswordMatchingPasswordsTest() {
        when(portalUserService.changePassword(any(Long.class), any(String.class), any(String.class))).thenReturn(true);
        boolean success = portalUserFacade.changePassword(portalUserChangePasswordDTO);

        Assert.assertTrue(success);
        verify(portalUserService, times(1)).changePassword(any(Long.class), any(String.class), any(String.class));
    }

    @Test
    public void changePasswordNotMatchingPasswordsTest() {
        when(portalUserService.changePassword(any(Long.class), any(String.class), any(String.class))).thenReturn(false);
        boolean success = portalUserFacade.changePassword(portalUserChangePasswordDTO);

        Assert.assertFalse(success);
        verify(portalUserService, times(1)).changePassword(any(Long.class), any(String.class), any(String.class));
    }
}
