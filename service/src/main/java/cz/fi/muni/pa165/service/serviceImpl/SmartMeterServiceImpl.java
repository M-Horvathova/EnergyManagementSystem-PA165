package cz.fi.muni.pa165.service.serviceImpl;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.serviceInterface.SmartMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

    public List<SmartMeter> findByHouse(House house) {
        return smartMeterDao.findByHouse(house);
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
    public double getAveragePowerSpentForDayTimeSmartMeter(SmartMeter smartMeter, DayTime dayTime) {
        Set<MeterLog> meterLogs  = smartMeter.getMeterLogs();
        meterLogs.removeIf(meterLog -> !meterLog.isWithinDayTime(dayTime));

        Map<LocalDate, List<MeterLog>> meterLogsGrouped =
                meterLogs.stream().collect(Collectors.groupingBy(ml -> ml.getLogDate()));

        List<Double> measurements = new ArrayList<Double>();
        for (List<MeterLog> value: meterLogsGrouped.values()) {
            measurements.add(Double.valueOf(value.stream().mapToDouble(ml -> (double)ml.getMeasure()).sum()));
        }
        return measurements.stream().mapToDouble(measurement -> measurement).average().orElse(0.0);
    }

    @Override
    public double getPowerSpentForDateForSmartMeter(LocalDate from, LocalDate to, SmartMeter smartMeter) {
        List<MeterLog> meterLogs = meterLogDao.findByDateAndSmartMeter(from, to, smartMeter);
        return meterLogs.stream().mapToDouble(meterLog -> (double)meterLog.getMeasure()).sum();
    }

    @Override
    public double getAveragePowerSpentForDateForSmartMeter(LocalDate from, LocalDate to, SmartMeter smartMeter) {
        List<MeterLog> meterLogs = meterLogDao.findByDateAndSmartMeter(from, to, smartMeter);
        return meterLogs.stream().mapToDouble(meterLog -> (double)meterLog.getMeasure()).average().orElse(0);
    }

    @Override
    public double getPowerSpentForIntervalForSmartMeters(LocalDate from, LocalDate to, List<SmartMeter> smartMeters) {
        double result = 0.0;
        for (SmartMeter smartMeter : smartMeters)
        {
            result += getPowerSpentForDateForSmartMeter(from, to, smartMeter);
        }

        return result;
    }

    @Override
    public double getAveragePowerSpentForIntervalForSmartMeters(LocalDate from, LocalDate to, List<SmartMeter> smartMeters) {
        double result = 0.0;
        for (SmartMeter smartMeter : smartMeters)
        {
            result += getAveragePowerSpentForDateForSmartMeter(from, to, smartMeter);
        }

        return result / (smartMeters.size() == 0 ? 1 : smartMeters.size());
    }

    @Override
    public double getAllPowerSpent() {
        return meterLogDao.findAll().stream().mapToDouble(ml -> (double)ml.getMeasure()).sum();
    }

    @Override
    public double getAveragePowerSpent() {
        return meterLogDao.findAll().stream().mapToDouble(ml -> (double)ml.getMeasure()).average().orElse(0);
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
