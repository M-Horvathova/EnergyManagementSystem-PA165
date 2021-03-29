package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@Repository
@Transactional
public class MeterLogDaoImpl implements MeterLogDao{

    @PersistenceContext
    private EntityManager em;

    /*
    Will be implemented in later milestone
     */
    @Override
    public MeterLog findById(Long id) {
        return null;
    }

    @Override
    public void create(MeterLog ml) {
        em.persist(ml);
    }

    @Override
    public void delete(MeterLog ml) {
        em.remove(ml);
    }

    @Override
    public void update(MeterLog ml, LocalDate date, LocalTime time, Long value) {
        ml = em.find(MeterLog.class, ml.getId());
        ml.setLogDate(date);
        ml.setLogTime(time);
        ml.setMeasure(value);
        ml = em.merge(ml);
        em.persist(ml);
    }


    /*
    Will be implemented in later milestone
     */
    @Override
    public List<MeterLog> findAll() {
        return null;
    }



    /*
    Will be implemented in later milestone
     */
    @Override
    public List<MeterLog> findByDate(LocalDate date) {
        return null;
    }

    /*
        Will be implemented in later milestone
    */
    @Override
    public List<MeterLog> findByTimeOfDay(LocalDate date, DayTime dayTime) {
        return null;
    }
}
