package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.house.HouseEditDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.dto.house.HouseCreateDTO;
import cz.fi.muni.pa165.dto.house.HouseDTO;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.service.mappers.HouseToDTOMapper;
import cz.fi.muni.pa165.service.serviceInterface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Valo
 */
@Service
@Transactional
public class HouseFacadeImpl implements HouseFacade {

    private HouseService houseService;
    private AddressService addressService;
    private PortalUserService portalUserService;
    private SmartMeterService smartMeterService;
    private MeterLogService meterLogService;
    private HouseToDTOMapper houseToDTOMapper;

    @Autowired
    public HouseFacadeImpl(HouseService houseService, AddressService addressService, PortalUserService portalUserService, SmartMeterService smartMeterService, MeterLogService meterLogService, HouseToDTOMapper houseToDTOMapper) {
        this.houseService = houseService;
        this.addressService = addressService;
        this.portalUserService = portalUserService;
        this.smartMeterService = smartMeterService;
        this.meterLogService = meterLogService;
        this.houseToDTOMapper = houseToDTOMapper;
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
        List<SmartMeter> smartMeters = smartMeterService.findByHouse(house);

        portalUserService.removeHouse(house.getPortalUser(), house);
        houseService.deleteHouse(house);

        for (SmartMeter smartMeter : smartMeters) {
            Set<MeterLog> meterLogs = smartMeter.getMeterLogs();

            smartMeterService.delete(smartMeter);

            for (MeterLog meterLog : meterLogs) {
                meterLogService.deleteMeterLog(meterLog);
            }
        }
    }

    @Override
    public void editHouse(Long id, HouseEditDTO houseEditDTO) {
        Address newAddress = new Address();
        newAddress.setStreet(houseEditDTO.getStreet());
        newAddress.setCode(houseEditDTO.getCode());
        newAddress.setCity(houseEditDTO.getCity());
        newAddress.setPostCode(houseEditDTO.getPostCode());
        newAddress.setCountry(houseEditDTO.getCountry());
        Address createdAddress = addressService.createAddress(newAddress);

        House house = houseService.findById(id);
        houseService.changeName(house, houseEditDTO.getName());
        houseService.changeAddress(house, createdAddress);
    }

    @Override
    public HouseDTO getHouseWithId(Long id) {
        House house = houseService.findById(id);
        return houseToDTOMapper.houseToHouseDTO(house);
    }

    @Override
    public List<HouseDTO> getHousesByUser(Long userId) {
        List<House> houses = houseService.findByUser(portalUserService.findUserById(userId));
        return houseToDTOMapper.convertHouseListToHouseDTOList(houses);
    }

    @Override
    public List<HouseDTO> getHousesByName(String name) {
        return houseToDTOMapper.convertHouseListToHouseDTOList(houseService.findByName(name));
    }

    @Override
    public List<HouseDTO> getHousesByAddress(Long addressId) {
        return houseToDTOMapper.convertHouseListToHouseDTOList(houseService.findByAddress(addressService.findById(addressId)));
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        return houseToDTOMapper.convertHouseListToHouseDTOList(houseService.findAll());
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
