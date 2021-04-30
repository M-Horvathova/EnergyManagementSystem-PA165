package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;

import java.util.List;

/**
 *
 * Interface for user role dao
 *
 * @author Martin Podhora
 */
public interface UserRoleDao {
    /**
     * Creates role
     *
     * @param userRole role to create
     */
    void create(UserRole userRole);

    /**
     * Finds user by id
     *
     * @param id numeric unique identifier of role
     * @return role with specified id or null in case no user with specified id exists
     */
    UserRole findById(Long id);

    /**
     * Retrieves all user in the database
     *
     * @return all users from the database, empty list in case of the empty database
     */
    List<UserRole> findAll();

    /**
     * Finds user by specified user name, in our portal user name is email
     *
     * @param userName email of the user
     * @return user with specified user name or null in case no user exists
     */
    UserRole findByName(String name);

    /**
     * Updates role
     *
     * @param userRole role to update
     */
    void update(UserRole userRole);

    /**
     * Deletes role
     *
     * @param userRole role to delete
     */
    void delete(UserRole userRole);
}
