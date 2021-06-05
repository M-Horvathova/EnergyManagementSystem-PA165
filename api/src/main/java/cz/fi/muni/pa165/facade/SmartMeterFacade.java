package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.smartMeter.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.smartMeter.SmartMeterDTO;
import cz.fi.muni.pa165.dto.smartMeter.SmartMeterEditDTO;
import cz.fi.muni.pa165.enums.DayTime;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Matej Rišňovský
 */
public interface SmartMeterFacade {
    /**
     * Create smart meter
     * @param smartMeter to be created
     * @return smart meter ID
     */
    Long createSmartMeter(SmartMeterCreateDTO smartMeter);

    /**
     * Update smart meter
     * @param smartMeter to be updated
     * @return updated smart meter DTO
     */
    SmartMeterDTO updateSmartMeter(SmartMeterEditDTO smartMeter);

    /**
     * Get smart meter by id
     * @param id id of smart meter to be found
     * @return found smart meter DTO
     */
    SmartMeterDTO getSmartMeter(Long id);

    /**
     * Get all smart meters for given house
     * @param id id of the house
     * @return List of smart meters
     */
    List<SmartMeterDTO> getSmartMeterByHouse(Long id);

    /**
     * Get all smart meters
     * @return List of smart meters
     */
    List<SmartMeterDTO> getAllSmartMeters();

    /**
     * Get all running smart meters
     * @return List of smart meters
     */
    List<SmartMeterDTO> getRunningSmartMetes();

    /**
     * Get power spent for smart meter on particular date
     * @param smartMeter Smart meter to be deleted
     */
    void deleteSmartMeter(SmartMeterDTO smartMeter);

    /**
     * Get power spent for smart meter on particular date
     * @param date date for which we want power spent
     * @param smartMeter Smart meter to get power spent for
     * @return power spent number
     */
    double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeterDTO smartMeter);

    /**
     * Get power spent for smart meter in date range
     * @param from start of date range
     * @param to end of date range
     * @param smartMeter Smart meter to get power spent for
     * @return power spent number
     */
    double getPowerSpentForSmartMeterInDateRange(LocalDate from, LocalDate to, SmartMeterDTO smartMeter);

    /**
     * Get average power spent for smart meter in day time
     * @param id smart meter id
     * @param dayTime day time to get power for
     * @return average power spent number in day time
     */
    double getAveragePowerSpentForDayTimeSmartMeter(Long id, DayTime dayTime);

    /**
     * Get power spent across all smart meters
     * @return power spent number
     */
    double getAllPowerSpent();

    /**
     * Gets power spent for smart meter in time range and time of day
     * @param from start of time range
     * @param to end of time range
     * @param smartMeter Smart meter to get power spent for
     * @param dayTime Time of day to sum power for
     * @return power spent number
     */
    double getPowerSpentForDateFrameWithDayTime(LocalDate from, LocalDate to, SmartMeterDTO smartMeter, DayTime dayTime);
}
