package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.dto.SmartMeterEditDTO;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matej Rišňovský
 */

/**
 * @author Matej Rišňovský
 */
public interface SmartMeterFacade {
    /**
     * Create smart meter
     * @param smartMeter to be created
     */
    Long createSmartMeter(SmartMeterCreateDTO smartMeter);

    /**
     * Update smart meter
     * @param smartMeter to be updated
     */
    SmartMeterDTO updateSmartMeter(SmartMeterEditDTO smartMeter);

    /**
     * Get smart meter by id
     * @param id id of smart meter to be found
     */
    SmartMeterDTO getSmartMeter(Long id);

    /**
     * Get all smart meters
     */
    List<SmartMeterDTO> getAllSmartMeters();

    /**
     * Get all running smart meters
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
     */
    double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeterDTO smartMeter);

    /**
     * Get power spent for smart meter in time range
     * @param from start of time range
     * @param to end of time range
     * @param smartMeter Smart meter to get power spent for
     */
    double getPowerSpentForSmartMeterInTimeRange(LocalDateTime from, LocalDateTime to, SmartMeterDTO smartMeter);

    double getAveragePowerSpentForDayTimeSmartMeter(Long id, DayTime dayTime);

    /**
     * Get power spent across all smart meters
     */
    double getAllPowerSpent();

    /**
     * Gets power spent for smart meter in time range and time of day
     * @param from start of time range
     * @param to end of time range
     * @param smartMeter Smart meter to get power spent for
     * @param dayTime Time of day to sum power for
     */
    double getPowerSpentForDateFrameWithDayTime(LocalDate from, LocalDate to, SmartMeterDTO smartMeter, DayTime dayTime);

}
