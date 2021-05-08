package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dto.AddressDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.SmartMeterHouseDTO;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BeanMapper {
    public static AddressDTO mapTo(Address address) {
        if (address == null) {
            return null;
        }
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setStreet(address.getCity());
        addressDTO.setCode(address.getCode());
        addressDTO.setCity(address.getCity());
        addressDTO.setPostCode(address.getPostCode());
        addressDTO.setCountry(address.getCountry());
        return addressDTO;
    }

    public static HouseDTO mapTo(House house) {
        if (house == null) {
            return null;
        }
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(house.getId());
        houseDTO.setName(house.getName());
        houseDTO.setRunning(house.getRunning());
        houseDTO.setAddress(BeanMapper.mapTo(house.getAddress()));
        houseDTO.setSmartMeters(BeanMapper.mapTo(house.getSmartMeters()));
        houseDTO.setPortalUserId(house.getPortalUser().getId());
        return houseDTO;
    }

    public static List<HouseDTO> mapTo(List<House> houses) {
        return houses.stream().map(BeanMapper::mapTo).collect(Collectors.toList());
    }

    public static SmartMeterHouseDTO mapTo(SmartMeter smartMeters) {
        if (smartMeters == null) {
            return null;
        }
        SmartMeterHouseDTO sm = new SmartMeterHouseDTO();
        sm.setId(smartMeters.getId());
        sm.setRunning(smartMeters.isRunning());
        sm.setPowerConsumptionSinceLastLog(smartMeters.getPowerConsumptionSinceLastLog());
        sm.setCumulativePowerConsumption(smartMeters.getCumulativePowerConsumption());
        sm.setLastLogTakenAt(smartMeters.getLastLogTakenAt() != null ? smartMeters.getLastLogTakenAt().toString() : null);
        sm.setSmartMeterDescription(smartMeters.getSmartMeterDescription());
        return sm;
    }

    public static Set<SmartMeterHouseDTO> mapTo(Set<SmartMeter> smartMeters) {
        return smartMeters.stream().map(BeanMapper::mapTo).collect(Collectors.toSet());
    }
}
