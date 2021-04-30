package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.SmartMeter;

import java.util.List;

/**
 * @author Patrik Valo
 */
public interface HouseService {

    /**
     * Creates the house
     * @param house the house, which should be created
     */
    House createHouse(House house);

    /**
     * Changes the address of the house. The old address is deleted
     * if there isn't house with same address. If there are more than one house
     * with that address, it is only changed.
     * @param house House, whose address should be changed
     * @param newAddress Address, which should be assigned to the house
     * @return house with the new address
     */
    House changeAddress(House house, Address newAddress);

    /**
     * Changes the name of the house
     * @param house House, whose name should be changed
     * @param newName Name, which should be assigned to the house
     * @return house with the new name
     */
    House changeName(House house, String newName);

    /**
     * Changes the running status
     * @param house House, whose status should be changed
     * @param isRunning Status, which should be changed in the house
     * @return house with the new status
     */
    House changeRunning(House house, Boolean isRunning);

    /**
     * Add the smartMeter to house
     * @param house House, where smartMeter should be placed
     * @param smartMeter SmartMeter, which should be placed to House
     */
    void addSmartMeter(House house, SmartMeter smartMeter);

    /**
     * Remove the smartMeter from the house
     * @param house House, where smartMeter should be removed
     * @param smartMeter SmartMeter, which should be removed
     */
    void removeSmartMeter(House house, SmartMeter smartMeter);

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
     * Deletes the house. The old address is also deleted
     * if there isn't house with same address. If there are more than one house
     * with that address, only house is deleted.
     * @param house the house, which should be deleted
     */
    void deleteHouse(House house);
}
