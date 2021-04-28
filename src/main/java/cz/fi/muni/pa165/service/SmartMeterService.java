package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
/**
 * @author Matej Rišňovský
 */

import java.time.LocalDate;
import java.util.List;
/**
 * @author Matej Rišňovský
 */
public interface SmartMeterService {
    SmartMeter create(SmartMeter smartMeter);
    SmartMeter update(SmartMeter smartMeter);
    SmartMeter findById(Long id);
    List<SmartMeter> findAll();
    List<SmartMeter> getRunningSmartMeters();
    void delete(SmartMeter smartMeter);
    double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeter smartMeter);
    double getAllPowerSpent();
    /**
     * Add the meter log to smart meter
     * @param smartMeter SmartMeter, which meter log will be assigned to
     * @param meterLog MeterLog to be assigned to smart meter
     */
    void addMeterLog(SmartMeter smartMeter, MeterLog meterLog);

    /**
     * Remove the smartMeter from the house
     * @param smartMeter SmartMeter, from which meter log should be removed
     * @param meterLog MeterLog, which should be removed
     */
    void removeMeterLog(SmartMeter smartMeter, MeterLog meterLog);
}
