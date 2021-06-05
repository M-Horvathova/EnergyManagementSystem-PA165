package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.meterLog.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.meterLog.MeterLogDTO;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
public interface MeterLogFacade {
    /**
     * Creates a meter Log objet from the DTO
     * @param ml DTO with the data to pass to the meter log object
     * @return ID of the created object
     */
    public Long createMeterLog(MeterLogCreateDTO ml);

    /**
     * Deleted the meter log with the given id
     * @param meterLogId id of the object to be deleted
     */
    public void deleteMeterLog(Long meterLogId);

    /**
     * Finds the meter log object by the given id
     * @param id id of the object to be found
     * @return DTO of the found object or null
     */
    public MeterLogDTO getMeterLogWithId(Long id);

    /**
     * Finds all meter logs
     * @return List of DTOs of the found logs
     */
    public List<MeterLogDTO> getAllMeterLogs();

    /**
     * Finds all logs done in given time frame (including on given days)
     * @param startDate string representation of the starting date
     * @param endDate string representation of the ending date
     * @return list of DTOs of logs taken in given dates
     */
    public List<MeterLogDTO> getLogsInTimeFrame(String startDate, String endDate);

    /**
     * Finds all logs done in given time frame (including on given days) in given time of day
     * @param startDate string representation of the starting date
     * @param endDate string representation of the ending date
     * @param dayTime string representation of the time of day
     * @return list of DTOs of logs taken in given dates and times of day
     */
    public List<MeterLogDTO> getLogsInTimeFrameWithDaytime(String startDate, String endDate, String dayTime);
}
