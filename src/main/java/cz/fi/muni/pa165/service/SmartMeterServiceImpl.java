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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Matej Rišňovský
 */

@Service
public class SmartMeterServiceImpl implements SmartMeterService {

    private SmartMeterDao smartMeterDao;
    private MeterLogDao meterLogDao;

    @Autowired
    public SmartMeterServiceImpl(SmartMeterDao smartMeterDao, MeterLogDao meterLogDao) {
        this.smartMeterDao = smartMeterDao;
        this.meterLogDao = meterLogDao;
    }

    @Override
    public SmartMeter create(SmartMeter smartMeter) {
        return smartMeterDao.create(smartMeter);
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
        List<MeterLog> meterLogs = meterLogDao.findByDate(date);
        meterLogs.removeIf(meterLog -> !meterLog.getSmartMeter().equals(smartMeter));
        return meterLogs.stream().mapToDouble(meterLog -> (double)meterLog.getMeasure()).sum();
    }

    @Override
    public double getPowerSpentForSmartMeterInTimeRange(LocalDateTime from, LocalDateTime to, SmartMeter smartMeter) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From cannot be after to!");
        }

        LocalDateTime date = from;
        List<MeterLog> meterLogs = new ArrayList<MeterLog>();

        while (date.toLocalDate() != to.toLocalDate()) {
            meterLogs.addAll(meterLogDao.findByDate(date.toLocalDate()));
            date = date.plusDays(1);
        }

        meterLogs.removeIf(meterLog -> !meterLog.getSmartMeter().equals(smartMeter));
        meterLogs.removeIf(meterLog -> meterLog.getLogDate().isEqual(from.toLocalDate()) && meterLog.getLogTime().isBefore(from.toLocalTime()));
        meterLogs.removeIf(meterLog -> meterLog.getLogDate().isEqual(to.toLocalDate()) && meterLog.getLogTime().isAfter(to.toLocalTime()));

        return meterLogs.stream().mapToDouble(meterLog -> (double)meterLog.getMeasure()).sum();
    }

    @Override
    public double getAllPowerSpent() {
        return findAll().stream().mapToDouble(sm -> sm.getCumulativePowerConsumption()).sum();
    }

    @Override
    public void addMeterLog(SmartMeter smartMeter, MeterLog meterLog) {
        smartMeter.addMeterLog(meterLog);
    }

    @Override
    public void removeMeterLog(SmartMeter smartMeter, MeterLog meterLog) {
        smartMeter.removeMeterLog(meterLog);
    }

    @Override
    public double sumPowerFromLogs(List<MeterLog> logs) {
        return logs.stream().mapToDouble(lg -> lg.getMeasure()).sum();
    }


}
