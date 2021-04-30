package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
/**
 * @author Matej Rišňovský
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @author Matej Rišňovský
 */
public interface SmartMeterService {
    /**
     * Create smart meter
     * @param smartMeter to be created
     */
    SmartMeter create(SmartMeter smartMeter);

    /**
     * Update smart meter
     * @param smartMeter to be updated
     */
    SmartMeter update(SmartMeter smartMeter);

    /**
     * Get smart meter by id
     * @param id id of smart meter to be found
     */
    SmartMeter findById(Long id);

    /**
     * Get all smart meters
     */
    List<SmartMeter> findAll();

    /**
     * Get all running smart meters
     */
    List<SmartMeter> getRunningSmartMeters();

    /**
     * Get power spent for smart meter on particular date
     * @param smartMeter Smart meter to be deleted
     */
    void delete(SmartMeter smartMeter);

    /**
     * Get power spent for smart meter on particular date
     * @param date date for which we want power spent
     * @param smartMeter Smart meter to get power spent for
     */
    double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeter smartMeter);

    /**
     * Get power spent for smart meter in time range
     * @param from start of time range
     * @param to end of time range
     * @param smartMeter Smart meter to get power spent for
     */
    double getPowerSpentForSmartMeterInTimeRange(LocalDateTime from, LocalDateTime to, SmartMeter smartMeter);

    /**
     * Get power spent across all smart meters
     */
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
