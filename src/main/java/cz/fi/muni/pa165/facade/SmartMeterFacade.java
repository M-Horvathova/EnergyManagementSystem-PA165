package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.SmartMeter;

import java.util.List;

public interface SmartMeterFacade {
    void create(SmartMeterCreateDTO smartMeter);
    SmartMeterDTO update(SmartMeterDTO smartMeter);
    SmartMeterDTO findById(Long id);
    List<SmartMeterDTO> findAll();
    List<SmartMeterDTO> findByRunning(boolean running);
    void delete(SmartMeterDTO smartMeter);
}
