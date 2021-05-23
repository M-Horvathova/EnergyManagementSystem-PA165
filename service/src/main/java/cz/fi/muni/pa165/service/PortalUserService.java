package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;

import java.util.List;

/**
 * @author Martin Podhora
 */
public interface PortalUserService {
    /**
     * Method for registering ordinary user
     *
     * @param portalUser portal user
     * @param unencryptedPassword plaintext password
     */
    Long registerUser(PortalUser portalUser, String unencryptedPassword);

    /**
     * Method for registering administrator user
     *
     * @param portalUser portal user
     * @param unencryptedPassword plaintext password
     */
    Long registerAdministrator(PortalUser portalUser, String unencryptedPassword);

    /**
     * Method for getting all users
     *
     * @return all users
     */
    List<PortalUser> getAllUsers();

    List<PortalUser> getAllUsers(int page, int itemsCount);

    Long getTotalUsersCount();

    /**
     * Returns whether the user entered correct password
     *
     * @param portalUser portal user
     * @param password plaintext password
     * @return true if password is correct false otherwise
     */
    boolean authenticate(PortalUser portalUser, String password);

    /**
     * Method for finding user by id
     *
     * @param id user id
     * @return portal user
     */
    PortalUser findUserById(Long id);

    /**
     * Method for finding user by email
     *
     * @param email email
     * @return portal user
     */
    PortalUser findUserByEmail(String email);

    /**
     * Method updating basic user info
     *
     * @param portalUser portal user
     */
    void updateBasicUserInfo(PortalUser portalUser);

    /**
     * Method for adding house to user
     *
     * @param portalUser user
     * @param house house
     */
    void addHouse(PortalUser portalUser, House house);

    /**
     * Method for removing house from user
     *
     * @param portalUser user
     * @param house house
     */
    void removeHouse(PortalUser portalUser, House house);

    /**
     * Method used to deactivate user by id
     *
     * @param id user id
     */
    void deactivateUser(Long id);

    /**
     * Method used to reactivate user
     *
     * @param id user id
     */
    void reactivateUser(Long id);

    /**
     * Method used to change password
     *
     * @param id user id
     * @param oldPassword old password
     * @param newPassword new password
     * @return true if change was successfull false otherwise
     */
    boolean changePassword(Long id, String oldPassword, String newPassword);

    /**
     * Method used for deleting user
     *
     * @param portalUser portal user
     */
    void delete(PortalUser portalUser);
}
