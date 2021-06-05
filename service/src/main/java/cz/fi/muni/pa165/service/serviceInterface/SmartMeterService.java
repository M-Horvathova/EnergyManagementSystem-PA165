package cz.fi.muni.pa165.service.serviceInterface;

import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Matej Rišňovský
 */
public interface SmartMeterService {
    /**
     * Create smart meter
     * @param smartMeter to be created
     * @return created smart meter
     */
    SmartMeter create(SmartMeter smartMeter);

    /**
     * Update smart meter
     * @param smartMeter to be updated
     * @return updated smart meter
     */
    SmartMeter update(SmartMeter smartMeter);

    /**
     * Get smart meter by id
     * @param id id of smart meter to be found
     * @return found smart meter
     */
    SmartMeter findById(Long id);

    /**
     * Find all meters belonging to a house
     * @param house house for which to find meters
     * @return list of meters
     */
    List<SmartMeter> findByHouse(House house);

    /**
     * Get all smart meters
     * @return list of meters
     */
    List<SmartMeter> findAll();

    /**
     * Get all running smart meters
     * @return list of meters
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
     * @return power spent
     */
    double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeter smartMeter);

    /**
     * Get power spent in given day time
     * @param smartMeter smart meter to get power for
     * @param dayTime given day time
     * @return power spent in day time
     */
    double getAveragePowerSpentForDayTimeSmartMeter(SmartMeter smartMeter, DayTime dayTime);

    /**
     * Get power spent in time range for all meters
     * @param from date from
     * @param to date to
     * @param smartMeters meters to get power for
     * @return power spent
     */
    double getPowerSpentForIntervalForSmartMeters(LocalDate from, LocalDate to, List<SmartMeter> smartMeters);

    /**
     * Get average power spent in time range for all meters
     * @param from date from
     * @param to date to
     * @param smartMeters meters to get power for
     * @return average power spent
     */
    double getAveragePowerSpentForIntervalForSmartMeters(LocalDate from, LocalDate to, List<SmartMeter> smartMeters);

    /**
     * Get power spent in time range for all meters
     * @param from date from
     * @param to date to
     * @param smartMeter meter to get power for
     * @return power spent
     */
    double getPowerSpentForDateForSmartMeter(LocalDate from, LocalDate to, SmartMeter smartMeter);

    /**
     * Get average power spent in time range for all meters
     * @param from date from
     * @param to date to
     * @param smartMeter meters to get power for
     * @return average power spent
     */
    double getAveragePowerSpentForDateForSmartMeter(LocalDate from, LocalDate to, SmartMeter smartMeter);

    /**
     * Get average power spent
     * @return average pwoer spent
     */
    double getAveragePowerSpent();

    /**
     * Get power spent across all smart meters
     * @return power spent from all meters
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
     * @return total power spent sum from meters
     */
    double sumPowerFromLogs(List<MeterLog> logs);
}
