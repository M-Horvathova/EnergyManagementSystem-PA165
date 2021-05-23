package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.PortalUser;

import java.util.List;

/**
 * @author Martin Podhora
 */
public interface PortalUserFacade {
    /**
     * Performs registration of the user
     *
     * @param portalUserRegistrationDTO user data
     */
    void registerUser(PortalUserRegistrationDTO portalUserRegistrationDTO);

    /**
     * Performs registration of the administrator
     *
     * @param portalUserRegistrationDTO user data
     */
    void registerAdministrator(PortalUserRegistrationDTO portalUserRegistrationDTO);

    /**
     * Gets all users
     *
     * @return all users
     */
    List<PortalUserDTO> getAllUsers();

    PortalUserListingDTO getAllUsers(int page, int itemsCount);

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
     * @param portalUserChangeBasicInfoDTO updated user info
     */
    void updateBasicUserInfo(PortalUserChangeBasicInfoDTO portalUserChangeBasicInfoDTO);

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
