package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.AddressDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.SmartMeterHouseDTO;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface HouseToDTOMapper {

    AddressDTO addressToAddressDTO(Address address);

    @Mappings({ @Mapping(target = "portalUserId", source = "portalUser.id") })
    HouseDTO houseToHouseDTO(House house);

    @Mappings({ @Mapping(target = "lastLogTakenAt", source = "lastLogTakenAt", dateFormat = "dd-MM-yyyy HH:mm:ss")})
    SmartMeterHouseDTO smartMeterToSmartMeterHouseDTO(SmartMeter smartMeter);

    List<HouseDTO> convertHouseListToHouseDTOList(List<House> houses);
    Set<SmartMeterHouseDTO> convertSmartMeterSetToSmartMeterHouseDTOSet(Set<SmartMeter> smartMeters);
}
