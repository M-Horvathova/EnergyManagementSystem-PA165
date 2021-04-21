package cz.fi.muni.pa165.facade.PortalUser;

import cz.fi.muni.pa165.dto.PortalUser.NewPortalUserDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserDTO;
import cz.fi.muni.pa165.entity.PortalUser;

import java.util.List;

/**
 * @author Martin Podhora
 */
public interface PortalUserFacade {
    void registerUser(NewPortalUserDTO portalUser, String unencryptedPassword);

    void registerAdministrator(NewPortalUserDTO portalUser, String unencryptedPassword);

    List<PortalUserDTO> getAllUsers();

    boolean authenticate(PortalUserDTO portalUser, String password);

    PortalUserDTO findUserById(long id);

    PortalUserDTO findUserByEmail(String email);

    void updateBasicUserInfo(PortalUserDTO portalUser);

    void deactivateUser(long id);

    void reactivateUser(long id);

    void changePassword(long id, String oldPassword, String newPassword);

    void delete(long id);
}
