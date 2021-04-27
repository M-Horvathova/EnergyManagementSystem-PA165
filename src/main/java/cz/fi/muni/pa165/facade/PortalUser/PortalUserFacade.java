package cz.fi.muni.pa165.facade.PortalUser;

import cz.fi.muni.pa165.dto.PortalUser.PortalUserAuthenticateDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserChangePasswordDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserDTO;
import cz.fi.muni.pa165.entity.PortalUser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Martin Podhora
 */
public interface PortalUserFacade {
    /**
     * Performs registration of the user
     *
     * @param portalUserDTO user data
     * @param unencryptedPassword password
     */
    void registerUser(PortalUserDTO portalUserDTO, String unencryptedPassword);

    /**
     * Performs registration of the administrator
     *
     * @param portalUserDTO user data
     * @param unencryptedPassword password
     */
    void registerAdministrator(PortalUserDTO portalUserDTO, String unencryptedPassword);

    /**
     * Gets all users
     *
     * @return all users
     */
    List<PortalUserDTO> getAllUsers();

    /**
     * Used to check whether the password for user matches during login
     *
     * @param portalUserAuthenticateDTO user data
     * @return true if the password for given user name is correct, false otherwise
     */
    boolean authenticate(PortalUserAuthenticateDTO portalUserAuthenticateDTO);

    /**
     * Finds user by id
     *
     * @param id user id
     * @return user
     */
    PortalUserDTO findUserById(Long id);

    /**
     * Finds user by email
     *
     * @param email user's email
     * @return user
     */
    PortalUserDTO findUserByEmail(String email);

    /**
     * Updates basic info about user
     *
     * @param portalUserDTO updated user info
     */
    void updateBasicUserInfo(PortalUserDTO portalUserDTO);

    /**
     * Deactivates given user
     *
     * @param id user's id
     */
    void deactivateUser(Long id);

    /**
     * Reactivates given user
     *
     * @param id user's id
     */
    void reactivateUser(Long id);

    /**
     * Changes password for given user
     *
     * @param portalUserChangePasswordDTO user dto for changing password
     * @return true if the password was changed correctly, false if the authentication was unsuccessful
     */
    boolean changePassword(PortalUserChangePasswordDTO portalUserChangePasswordDTO);

    /**
     * Deletes user by id
     *
     * @param id user's id
     */
    void delete(Long id);
}
