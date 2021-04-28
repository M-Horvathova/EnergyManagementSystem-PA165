package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.SmartMeter;
/**
 * @author Matej Rišňovský
 */
import java.time.LocalDate;
import java.util.List;

public interface SmartMeterFacade {
    Long createSmartMeter(SmartMeterCreateDTO smartMeter);
    SmartMeterDTO updateSmartMeter(SmartMeterDTO smartMeter);
    SmartMeterDTO getSmartMeter(Long id);
    List<SmartMeterDTO> getAllSmartMeters();
    List<SmartMeterDTO> getRunningSmartMetes();
    void deleteSmartMeter(SmartMeterDTO smartMeter);
    double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeterDTO smartMeter);
    double getAllPowerSpent();

}
