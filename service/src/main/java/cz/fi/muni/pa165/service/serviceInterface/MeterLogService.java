package cz.fi.muni.pa165.service.serviceInterface;

import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


/**
 * @author Michaela Horváthová
 *
 * Services for the MeterLog entities
 */
public interface MeterLogService {
    /**
     * Finds the meter log with given id
     * @param id Id of the object to be found
     * @return Found object or null
     */
    MeterLog findById(Long id);

    /**
     * Finds all meter logs
     * @return list of all meter log objects
     */
    List<MeterLog> findAll();

    /**
     * Finds all meter logs taken on given day
     * @param date Date in which the searched for logs were taken
     * @return List of meter logs taken on specified date
     */
    List<MeterLog> findByDate(LocalDate date);

    /**
     * Finds all meter logs given within time frame (including on edge days)
     * @param startDate  starting date of the time frame
     * @param endDate ending date of the time frame
     * @return List of meter logs taken within given timeframe
     */
    List<MeterLog> findInDateFrame(LocalDate startDate, LocalDate endDate);

    /**
     * Finds all meter logs given within time frame (including on edge days) and in given time of day
     * @param startDate starting date of the time frame
     * @param endDate ending date of the time frame
     * @param dayTime time of the day where logs were taken
     * @return List of meter logs given within time frame and in given time of day
     */
   List<MeterLog> findInDateFrameWithDayTime(LocalDate startDate, LocalDate endDate, DayTime dayTime);

    /**
     * Creates meter log in database
     * @param ml meter log to be created
     */
    MeterLog createMeterLog(MeterLog ml);

    /**
     * Deletes a meter log
     * @param ml meter log to be deleted
     */
    void deleteMeterLog(MeterLog ml);

    /**
     * Changes the measurement value of the given meter log to the given value
     * @param ml meter log to be updated
     * @param newMeasurement new measurement value
     * @return meter log with the updated value
     */
    MeterLog changeMeasurement(MeterLog ml, Long newMeasurement);

    /**
     * Changes the date taken of the given meter log to the given value
     * @param ml meter log to be updated
     * @param newDate new date value
     * @return meter log with the updated value
     */
    MeterLog changeDate(MeterLog ml, LocalDate newDate);

    /**
     * Changes the time taken of the given meter log to the given value
     * @param ml meter log to be updated
     * @param newTme new time value
     * @return meter log with the updated value
     */
    MeterLog changeTime(MeterLog ml, LocalTime newTme);

    /**
     * Checks if given meter log was taken in given time frame
     * @param ml meter log to be checked
     * @param startDate start date of the time frame
     * @param endDate end date of the time frame
     * @return true if meter log was taken in given time frame (edge days included), false otherwise
     */
    boolean isInDateFrame(MeterLog ml, LocalDate startDate, LocalDate endDate);

    /**
     * Checks if given meter log was taken in given time of day
     * @param ml meter log to be checked
     * @param dayTime time of day to be checked against
     * @return true if meter log was taken in given time of day, false otherwise
     */
    boolean isInDayTime(MeterLog ml, DayTime dayTime);

    /**
     * Checks if given meter log was taken in given time frame and daytime
     * @param ml meter log to be checked
     * @param startDate start date of the time frame
     * @param endDate end date of the time frame
     * @param dayTime time of day to be checked against
     * @return true if meter log was taken in given time frame (edge days included) and time of day, false otherwise
     */
    boolean isInDateFrameWithDayTime(MeterLog ml, LocalDate startDate, LocalDate endDate, DayTime dayTime);

    /**
     * Removes all entries from given list that were not taken within given date frame (edge days included)
     * @param meterLogs list of logs to be filtered
     * @param startDate start date of the time frame
     * @param endDate end date of the time frame
     * @return list of only those frames from the parameter that were taken in given time frame
     */
    List<MeterLog> filterInDateFrame(List<MeterLog> meterLogs, LocalDate startDate, LocalDate endDate);

    /**
     * Removes all entries from given list that were not taken within given time of day
     * @param meterLogs list of logs to be filtered
     * @param dayTime time of day to be included
     * @return list of only those frames from the parameter that were taken in given time of day
     */
    List<MeterLog> filterInDayTime(List<MeterLog> meterLogs, DayTime dayTime);

    /**
     * Removes all entries from given list that were not taken within given date frame (edge days included) and time of day
     * @param meterLogs list of logs to be filtered
     * @param startDate start date of the time frame
     * @param endDate end date of the time frame
     * @param dayTime time of day to be included
     * @return list of only those frames from the parameter that were taken in given time frame and time of day
     */
    List<MeterLog> filterInDateFrameWithTimeDay(List<MeterLog> meterLogs, LocalDate startDate, LocalDate endDate, DayTime dayTime);
}
