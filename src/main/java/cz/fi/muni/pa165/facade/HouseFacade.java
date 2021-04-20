package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.HouseCreateDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.NewAddressDTO;

import java.util.List;

/**
 * @author Patrik Valo
 */
public interface HouseFacade {
    Long createHouse(HouseCreateDTO h);
    void deleteHouse(Long id);
    void changeAddress(NewAddressDTO newAddressDTO);
    HouseDTO getHouseWithId(Long id);
    List<HouseDTO> getHousesByUser(Long userId);
    List<HouseDTO> getHousesByName(String name);
    List<HouseDTO> getHousesByAddress(Long addressId);
    List<HouseDTO> getAllHouses();
    void changeName(Long id, String houseName);
    void changeRunning(Long id, Boolean isRunning);
    Boolean isRunning(Long houseId);
}
