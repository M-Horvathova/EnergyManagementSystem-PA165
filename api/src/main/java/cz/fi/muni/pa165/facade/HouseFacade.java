package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.house.HouseCreateDTO;
import cz.fi.muni.pa165.dto.house.HouseDTO;
import cz.fi.muni.pa165.dto.house.HouseEditDTO;

import java.util.List;

/**
 * @author Patrik Valo
 */
public interface HouseFacade {
    /**
     * Creates house object from the DTO
     * @param h DTO, which is used to create the house
     * @return id of the created house
     */
    Long createHouse(HouseCreateDTO h);

    /**
     * Deletes house with the id from the database
     * @param id id of the house, which should be deleted
     */
    void deleteHouse(Long id);

    /**
     * Edit the house
     * @param id id of the house
     * @param houseEditDTO DTO of the house
     */
    void editHouse(Long id, HouseEditDTO houseEditDTO);

    /**
     * Finds house with the given id
     * @param id id of the house
     * @return DTO of the house
     */
    HouseDTO getHouseWithId(Long id);

    /**
     * Finds houses by the user
     * @param userId id of the user
     * @return user's houses
     */
    List<HouseDTO> getHousesByUser(Long userId);

    /**
     * Finds houses by the name
     * @param name name of the houses
     * @return houses with given name
     */
    List<HouseDTO> getHousesByName(String name);

    /**
     * Finds houses with given address id
     * @param addressId address id
     * @return houses with given address
     */
    List<HouseDTO> getHousesByAddress(Long addressId);

    /**
     * Finds all houses
     * @return all houses in the system
     */
    List<HouseDTO> getAllHouses();

    /**
     * Changes the running status
     * @param id id of the house
     * @param isRunning status
     */
    void changeRunning(Long id, Boolean isRunning);

    /**
     * Returns status of the house
     * @param houseId house id
     * @return status of the house
     */
    Boolean isRunning(Long houseId);
}
