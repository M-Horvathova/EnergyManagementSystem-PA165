package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.HouseCreateDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.NewAddressDTO;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.service.AddressService;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.PortalUser.PortalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Patrik Valo
 */
@Service
@Transactional
public class HouseFacadeImpl implements HouseFacade {

    private HouseService houseService;
    private AddressService addressService;
    private PortalUserService portalUserService;
    private BeanMappingService beanMappingService;

    @Autowired
    public HouseFacadeImpl(HouseService houseService, AddressService addressService, PortalUserService portalUserService, BeanMappingService beanMappingService) {
        this.houseService = houseService;
        this.addressService = addressService;
        this.portalUserService = portalUserService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long createHouse(HouseCreateDTO h) {
        Address address = new Address();
        address.setStreet(h.getStreet());
        address.setCode(h.getCode());
        address.setCity(h.getCity());
        address.setPostCode(h.getPostCode());
        address.setCountry(h.getCountry());

        Address createdAddress = addressService.createAddress(address);

        House house = new House();
        house.setName(h.getName());
        house.setRunning(h.getRunning());
        house.setAddress(createdAddress);

        portalUserService.addHouse(portalUserService.findUserById(h.getPortalUserId()), house);

        House createdHouse = houseService.createHouse(house);
        return createdHouse.getId();
    }

    @Override
    public void deleteHouse(Long houseId) {
        House house = houseService.findById(houseId);

        portalUserService.removeHouse(house.getPortalUser(), house);
        houseService.deleteHouse(house);
    }

    @Override
    public void changeAddress(NewAddressDTO newAddressDTO) {
        Address newAddress = new Address();
        newAddress.setStreet(newAddressDTO.getStreet());
        newAddress.setCode(newAddressDTO.getCode());
        newAddress.setCity(newAddressDTO.getCity());
        newAddress.setPostCode(newAddressDTO.getPostCode());
        newAddress.setCountry(newAddressDTO.getCountry());

        Address createdAddress = addressService.createAddress(newAddress);
        houseService.changeAddress(houseService.findById(newAddressDTO.getHouseId()), createdAddress);
    }

    @Override
    public HouseDTO getHouseWithId(Long id) {
        return beanMappingService.mapTo(houseService.findById(id), HouseDTO.class);
    }

    @Override
    public List<HouseDTO> getHousesByUser(Long userId) {
        List<House> houses = houseService.findByUser(portalUserService.findUserById(userId));
        return beanMappingService.mapTo(houses, HouseDTO.class);
    }

    @Override
    public List<HouseDTO> getHousesByName(String name) {
        return beanMappingService.mapTo(houseService.findByName(name), HouseDTO.class);
    }

    @Override
    public List<HouseDTO> getHousesByAddress(Long addressId) {
        return beanMappingService.mapTo(houseService.findByAddress(addressService.findById(addressId)), HouseDTO.class);
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        return beanMappingService.mapTo(houseService.findAll(), HouseDTO.class);
    }

    @Override
    public void changeName(Long id, String houseName) {
        houseService.changeName(houseService.findById(id), houseName);
    }

    @Override
    public void changeRunning(Long id, Boolean isRunning) {
        houseService.changeRunning(houseService.findById(id), isRunning);
    }

    @Override
    public Boolean isRunning(Long houseId) {
        return houseService.findById(houseId).getRunning();
    }
}
