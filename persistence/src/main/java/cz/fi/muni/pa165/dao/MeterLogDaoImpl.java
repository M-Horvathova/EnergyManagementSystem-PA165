package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michaela Horváthová
 */
@Repository
public class MeterLogDaoImpl implements MeterLogDao {

    @PersistenceContext
    private EntityManager em;

    private static Logger log = LoggerFactory.getLogger(MeterLogDaoImpl.class);

    @Override
    public MeterLog findMeterLog(MeterLog ml) {
        MeterLog found = null;
        try {
            found = em.find(MeterLog.class, ml.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return found;
    }

    @Override
    public MeterLog findById(Long id) {
        MeterLog found = null;
        try {
            found = em.find(MeterLog.class, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return found;
    }

    @Override
    public MeterLog create(MeterLog ml) {
        em.persist(ml);
        return ml;
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


    @Override
    public List<MeterLog> findByDate(LocalDate date) {
        List<MeterLog> resultList = em.createQuery("select m from MeterLog m where m.logDate=:date",
                MeterLog.class).setParameter("date", date).getResultList();
        return resultList;
    }

    @Override
    public List<MeterLog> findByDateAndSmartMeter(LocalDate from, LocalDate to, SmartMeter smartMeter) {
        if (smartMeter == null) {
            return new ArrayList<MeterLog>();
        }

        if (from != null && to != null) {
            return em.createQuery("select m from MeterLog m where m.logDate >= :from AND m.logDate <= :to AND  m.smartMeter = :smartMeter", MeterLog.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .setParameter("smartMeter", smartMeter)
                    .getResultList();
        } else if (from != null && to == null) {
            return em.createQuery("select m from MeterLog m where m.logDate >= :from AND m.smartMeter = :smartMeter", MeterLog.class)
                    .setParameter("from", from)
                    .setParameter("smartMeter", smartMeter)
                    .getResultList();
        } else if (from == null && to != null) {
            return em.createQuery("select m from MeterLog m where m.logDate <= :to AND m.smartMeter = :smartMeter", MeterLog.class)
                    .setParameter("to", to)
                    .setParameter("smartMeter", smartMeter)
                    .getResultList();
        } else {
            return em.createQuery("select m from MeterLog m where m.smartMeter = :smartMeter", MeterLog.class)
                    .setParameter("smartMeter", smartMeter)
                    .getResultList();
        }

    }

    @Override
    public List<MeterLog> findByTimeOfDay(LocalDate date, DayTime dayTime) {
        List<MeterLog> resultList = em.createQuery("select m from MeterLog m where m.logDate=:date",
                MeterLog.class).setParameter("date", date).getResultList();
        resultList.removeIf(m -> !(m.isWithinDayTime(dayTime)));
        return resultList;
    }
}
