package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
/**
 * @author Matej Rišňovský
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    List<SmartMeter> findByHouse(House house);

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

    double getAveragePowerSpentForDayTimeSmartMeter(SmartMeter smartMeter, DayTime dayTime);

    double getPowerSpentForIntervalForSmartMeters(LocalDate from, LocalDate to, List<SmartMeter> smartMeters);

    double getAveragePowerSpentForIntervalForSmartMeters(LocalDate from, LocalDate to, List<SmartMeter> smartMeters);

    double getPowerSpentForDateForSmartMeter(LocalDate from, LocalDate to, SmartMeter smartMeter);

    double getAveragePowerSpentForDateForSmartMeter(LocalDate from, LocalDate to, SmartMeter smartMeter);

    double getAveragePowerSpent();

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

    /**
     * Sums the measurements from given logs
     * @param logs List of Meter Logs, whose measures will be summed
     */
    double sumPowerFromLogs(List<MeterLog> logs);
}
