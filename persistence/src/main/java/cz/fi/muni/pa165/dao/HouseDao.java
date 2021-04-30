package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;

import java.util.List;

/**
 * @author Patrik Valo
 */
public interface HouseDao {

    /**
     * Saves the house into db
     * @param house the house, which should be saved
     */
    void create(House house);

    /**
     * Updates the house in db
     * @param house the house, which should be updated
     * @return updated house
     */
    House update(House house);

    /**
     * Finds the house by the given id
     * @param id the id of the house
     * @return house with given id
     */
    House findById(Long id);

    /**
     * Finds the houses by the given name
     * @param name the name of the house
     * @return houses with the name
     */
    List<House> findByName(String name);

    /**
     * Finds the houses on the address
     * @param address the address of the houses
     * @return houses on the address
     */
    List<House> findByAddress(Address address);

    /**
     * Finds the houses by the user
     * @param user user
     * @return user's houses
     */
    List<House> findByUser(PortalUser user);


    /**
     * Finds all houses in db
     * @return all houses in the db
     */
    List<House> findAll();

    /**
     * Deletes the house
     * @param house the house, which should be deleted
     */
    void delete(House house);
}
