package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@Repository
@Transactional
public class MeterLogDaoImpl implements MeterLogDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MeterLog findMeterLog(MeterLog ml) {
        MeterLog found = em.find(MeterLog.class, ml.getId());
        return found;
    }

    @Override
    public MeterLog findById(Long id) {
        MeterLog found = em.find(MeterLog.class, id);
        return found;
    }

    @Override
    public void create(MeterLog ml) {
        em.persist(ml);
    }

    @Override
    public void delete(MeterLog ml) {
        ml = em.merge(ml);
        em.remove(ml);
    }

    @Override
    public void update(MeterLog ml) {
        em.merge(ml);

    }

    @Override
    public List<MeterLog> findAll() {
        List<MeterLog> resultList = em.createQuery("select m from MeterLog m", MeterLog.class).getResultList();
        return resultList;
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
