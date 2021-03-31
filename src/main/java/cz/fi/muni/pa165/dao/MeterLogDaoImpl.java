package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@Repository
public class MeterLogDaoImpl implements MeterLogDao{

    @PersistenceContext
    private EntityManager em;

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
