package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Matej Rišňovský
 */

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
    public List<SmartMeter> getRunningSmartMeters() {
        return smartMeterDao.findByRunning(true);
    }

    @Override
    public void delete(SmartMeter smartMeter) {
        smartMeterDao.delete(smartMeter);
    }

    @Override
    public double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeter smartMeter) {
        List<MeterLog> meterLogsForDate = new ArrayList<MeterLog>(smartMeter.getMeterLogs());
        meterLogsForDate.removeIf(meterLog -> meterLog.getLogDate() != date);
        return meterLogsForDate.stream().mapToDouble(meterLog -> (double)meterLog.getMeasure()).sum();
    }

    @Override
    public double getAllPowerSpent() {
        return findAll().stream().mapToDouble(sm -> sm.getCumulativePowerConsumption()).sum();
    }
}
