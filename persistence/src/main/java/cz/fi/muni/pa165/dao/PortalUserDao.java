package cz.fi.muni.pa165.dao;


import cz.fi.muni.pa165.entity.PortalUser;

import java.util.List;

/**
 * Interface for user dao
 *
 * @author Martin Podhora
 */
public interface PortalUserDao {
    /**
     * Creates user
     *
     * @param portalUser user to create
     */
    void create(PortalUser portalUser);

    /**
     * Finds user by id
     *
     * @param id numeric unique identifier of user
     * @return user with specified id or null in case no user with specified id exists
     */
    PortalUser findById(Long id);

    /**
     * Retrieves all user in the database
     *
     * @return all users from the database, empty list in case of the empty database
     */
    List<PortalUser> findAll();

    /**
     * Finds user by specified user name, in our portal user name is email
     *
     * @param userName email of the user
     * @return user with specified user name or null in case no user exists
     */
    PortalUser findByUserName(String userName);

    /**
     * Updates user
     *
     * @param portalUser user to update
     */
    void update(PortalUser portalUser);

    /**
     * Deletes user
     *
     * @param portalUser user to delete
     */
    void delete(PortalUser portalUser);
}
