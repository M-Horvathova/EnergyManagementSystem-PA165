package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
/**
 * @author Matej Rišňovský
 */
@Transactional
@Repository
public class SmartMeterDaoImpl implements SmartMeterDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(SmartMeter smartMeter) {
        em.persist(smartMeter);
    }

    @Override
    public SmartMeter update(SmartMeter smartMeter) {
        return em.merge(smartMeter);
    }

    @Override
    public SmartMeter findById(Long id) {
        SmartMeter sm = em.find(SmartMeter.class, id);
        return sm;
    }

    @Override
    public List<SmartMeter> findAll() {
        List<SmartMeter> smartMeters = em.createQuery("select sm from SmartMeter sm", SmartMeter.class).getResultList();
        return smartMeters;
    }

    @Override
    public List<SmartMeter> findByRunning(boolean running) {
        List<SmartMeter> smartMeters = em.createQuery("select sm from SmartMeter sm where sm.isRunning = :running", SmartMeter.class)
                .setParameter("running", running)
                .getResultList();
        return smartMeters;
    }

    @Override
    public void delete(SmartMeter smartMeter) {
        smartMeter = em.merge(smartMeter);
        em.remove(smartMeter);
    }
}
