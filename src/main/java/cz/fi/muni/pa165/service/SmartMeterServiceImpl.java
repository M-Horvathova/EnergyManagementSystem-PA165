package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmartMeterServiceImpl implements SmartMeterService {

    private final SmartMeterDao smartMeterDao;
    private final MeterLogDao meterLogDao;

    @Autowired
    public SmartMeterServiceImpl(SmartMeterDao smartMeterDao, MeterLogDao meterLogDao) {
        this.smartMeterDao = smartMeterDao;
        this.meterLogDao = meterLogDao;
    }

    @Override
    public void create(SmartMeter smartMeter) {
        smartMeterDao.create(smartMeter);
    }

    @Override
    public SmartMeter update(SmartMeter smartMeter) {
        return smartMeterDao.update(smartMeter);
    }

    @Override
    public SmartMeter findById(Long id) {
        return smartMeterDao.findById(id);
    }

    @Override
    public List<SmartMeter> findAll() {
        return smartMeterDao.findAll();
    }

    @Override
    public List<SmartMeter> findByRunning(boolean running) {
        return smartMeterDao.findByRunning(running);
    }

    @Override
    public void delete(SmartMeter smartMeter) {
        smartMeterDao.delete(smartMeter);
    }

    //TODO vymysliet pokrocile metody, ktore budu davat zmysel
}
