package cz.fi.muni.pa165.service;

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
     *
     * @param id
     * @return
     */
    public MeterLog findById(Long id);

    /**
     *
     * @return
     */
    public List<MeterLog> findAll();

    /**
     *
     * @param date
     * @return
     */
    public List<MeterLog> findByDate(LocalDate date);

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<MeterLog> findInDateFrame(LocalDate startDate, LocalDate endDate);

    /**
     *
     * @param startDate
     * @param endDate
     * @param dayTime
     * @return
     */
    public List<MeterLog> findInDateFrameWithDayTime(LocalDate startDate, LocalDate endDate, DayTime dayTime);

    /**
     *
     * @param ml
     */
    public void createMeterLog(MeterLog ml);

    /**
     *
     * @param ml
     */
    public void deleteMeterLog(MeterLog ml);

    /**
     *
     * @param ml
     * @param newMeasurement
     */
    public void changeMeasurement(MeterLog ml, Long newMeasurement);

    /**
     *
     * @param ml
     * @param newDate
     */
    public void changeDate(MeterLog ml, LocalDate newDate);

    /**
     *
     * @param ml
     * @param newTme
     */
    public void changeTime(MeterLog ml, LocalTime newTme);

    /**
     *
     * @param ml
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean isInDateFrame(MeterLog ml, LocalDate startDate, LocalDate endDate);

    /**
     *
     * @param ml
     * @param dayTime
     * @return
     */
    public boolean isInDayTime(MeterLog ml, DayTime dayTime);

    /**
     *
     * @param ml
     * @param startDate
     * @param endDate
     * @param dayTime
     * @return
     */
    public boolean isInDateFrameWithDayTime(MeterLog ml, LocalDate startDate, LocalDate endDate, DayTime dayTime);

    /**
     *
     * @param meterLogs
     * @param startDate
     * @param endDate
     * @return
     */
    public List<MeterLog> filterInDateFrame(List<MeterLog> meterLogs, LocalDate startDate, LocalDate endDate);

    /**
     *
     * @param meterLogs
     * @param dayTime
     * @return
     */
    public List<MeterLog> filterInDayTime(List<MeterLog> meterLogs, DayTime dayTime);

    /**
     *
     * @param startDate
     * @param endDate
     * @param dayTime
     * @return
     */
    public List<MeterLog> filterInDateFrameWithTimeDay(List<MeterLog> meterLogs, LocalDate startDate, LocalDate endDate, DayTime dayTime);
}
