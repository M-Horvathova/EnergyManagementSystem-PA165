package cz.fi.muni.pa165.service.mappers;


import cz.fi.muni.pa165.dto.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.dto.SmartMeterHouseDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MeterLogMapper {

    @Mappings({
            @Mapping(target = "logDate", source = "logDate", dateFormat = "dd-MM-yyyy"),
            @Mapping(target = "logTime", source = "logTime", dateFormat = "HH:mm:ss"),
            @Mapping(target = "smartMeterId", source = "smartMeter.id"),
    })
    MeterLogDTO meterLogToDTO(MeterLog meterLog);


    @Mappings({ @Mapping(target = "lastLogTakenAt", source = "lastLogTakenAt", dateFormat = "dd-MM-yyyy HH:mm:ss")})
    SmartMeterHouseDTO smartMeterToSmartMeterHouseDTO(SmartMeter smartMeter);

    List<MeterLogDTO> convertLogListToMeterLogDTOList(List<MeterLog> meterLogs);


}
