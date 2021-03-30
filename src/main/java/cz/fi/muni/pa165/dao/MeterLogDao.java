package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.entity.MeterLog;

import java.time.LocalDate;
import java.util.List;


/**
 * @author Michaela Horváthová
 */
public interface MeterLogDao {
    /**
     * Finds a MeterLog object that is connected to
     * @param id The id that should belong to the searched object
     * @return MeterLog object with the provided id or null
     */
    public MeterLog findById(Long id);

    /**
     * Persists the provided MeterLog object in the database
     * @param ml MeterLog object to create in database
     */
    public void create(MeterLog ml);

    /**
     * Deletes the provided MeterLog object from the database
     * @throws IllegalArgumentException Thrown when requesting a delete of non-existent object
     * @param ml MeterLog object to be deleted
     */
    public void delete(MeterLog ml) throws IllegalArgumentException;

    /**
     *
     * @param ml MeterLog to be updated
     * @param date New date to be saved
     * @param time New time to be saved
     * @param value New measurement value to be saved
     */
    public void update(MeterLog ml);

    /**
     *
     * @return List of all MeterLogs stored
     */
    public List<MeterLog> findAll();

    /**
     *
     * @param date Date in the LocalDate format, at which the measurements were to be done
     * @return List of MeterLogs taken on a given date
     */
    public List<MeterLog> findByDate(LocalDate date);

    /**
     *
     * @param date Date in LocalDate format when the measurement was done
     * @param dayTime Time of the day, given as enumerated form, in which the measurement was done
     * @return List of MeterLogs of measurements taken on given day within given time of day
     */
    public List<MeterLog> findByTimeOfDay(LocalDate date, DayTime dayTime);

}
