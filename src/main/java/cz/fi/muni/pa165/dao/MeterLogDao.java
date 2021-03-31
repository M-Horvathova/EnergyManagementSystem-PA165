package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.entity.MeterLog;

import java.time.LocalDate;
import java.util.List;


/**
 * @author Michaela Horváthová
 */
public interface MeterLogDao {
    public MeterLog findById(Long id);
    public void create(MeterLog ml);
    public void delete(MeterLog ml);
    public List<MeterLog> findAll();
    public List<MeterLog> findByDate(LocalDate date);
    public List<MeterLog> findByTimeOfDay(LocalDate date, DayTime dayTime);

}
