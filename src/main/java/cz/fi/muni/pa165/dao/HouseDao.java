package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;

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
     * Finds the house by the given id
     * @param id the id of the house
     * @return house with given id
     */
    House findById(Long id);

    /**
     * Finds the house by the given name
     * @param name the name of the house
     * @return house with the name
     */
    House findByName(String name);

    /**
     * Finds the houses on the address
     * @param address the address of the houses
     * @return houses on the address
     */
    List<House> findByAddress(Address address);

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
