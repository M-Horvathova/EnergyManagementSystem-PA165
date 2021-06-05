package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.smartMeter.SmartMeterDTO;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SmartMeterMapper {
    @Mappings({
            @Mapping(target = "lastLogTakenAt", source = "lastLogTakenAt", dateFormat = "dd-MM-yyyy-HH:mm:ss"),
            @Mapping(target = "houseId", source = "house.id")
    })

    SmartMeterDTO smartMeterToDTO(SmartMeter smartMeter);

    List<SmartMeterDTO> smartMetersToDTOs(List<SmartMeter> smartMeters);
}
