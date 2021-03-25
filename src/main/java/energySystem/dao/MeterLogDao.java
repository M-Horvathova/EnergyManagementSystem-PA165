package energySystem.dao;

import energySystem.entity.MeterLog;
import energySystem.enums.dayTime;

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
    public List<MeterLog> findByTimeOfDay(LocalDate date, dayTime dayTime);

}
