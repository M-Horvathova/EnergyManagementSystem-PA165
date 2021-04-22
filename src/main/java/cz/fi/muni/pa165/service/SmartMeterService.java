package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.SmartMeter;
/**
 * @author Matej Rišňovský
 */

import java.time.LocalDate;
import java.util.List;

public interface SmartMeterService {
    void create(SmartMeter smartMeter);
    SmartMeter update(SmartMeter smartMeter);
    SmartMeter findById(Long id);
    List<SmartMeter> findAll();
    List<SmartMeter> getRunningSmartMeters();
    void delete(SmartMeter smartMeter);
    double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeter smartMeter);
    double getAllPowerSpent();
}
