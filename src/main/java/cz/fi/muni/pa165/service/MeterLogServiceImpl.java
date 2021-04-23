package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

/**
 * @author Michaela Horváthová
 */
@Service
public class MeterLogServiceImpl implements MeterLogService{


    private MeterLogDao meterLogDao;
    private static Logger log = LoggerFactory.getLogger(MeterLogServiceImpl.class);

    @Autowired
    public MeterLogServiceImpl(MeterLogDao meterLogDao) {
        this.meterLogDao = meterLogDao;
    }

    @Override
    public MeterLog findById(Long id) {
        return meterLogDao.findById(id);
    }

    @Override
    public List<MeterLog> findAll() {
        return meterLogDao.findAll();
    }

    @Override
    public List<MeterLog> findByDate(LocalDate date) {
        return meterLogDao.findByDate(date);
    }

    @Override
    public List<MeterLog> findInDateFrame(LocalDate startDate, LocalDate endDate) {
        List<MeterLog> results = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            results.addAll(findByDate(date));
        }
        return results;
    }

    @Override
    public List<MeterLog> findInDateFrameWithDayTime(LocalDate startDate, LocalDate endDate, DayTime dayTime) {
        List<MeterLog> results = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            List<MeterLog> inDate = findByDate(date);
            inDate.removeIf(m -> !isInDayTime(m, dayTime));
            results.addAll(findByDate(date));
        }
        return results;
    }

    @Override
    public void createMeterLog(MeterLog ml) {
        meterLogDao.create(ml);
    }

    @Override
    public void deleteMeterLog(MeterLog ml) {
        meterLogDao.delete(ml);
    }

    @Override
    public MeterLog changeMeasurement(MeterLog ml, Long newMeasurement) {
            ml.setMeasure(newMeasurement);
            meterLogDao.update(ml);
            return ml;
    }

    @Override
    public MeterLog changeDate(MeterLog ml, LocalDate newDate) {
       try {
           ml.setLogDate(newDate);
       } catch (IllegalArgumentException e) {
           throw new IllegalArgumentException(e.getMessage());
       }
       meterLogDao.update(ml);
       return ml;
    }

    @Override
    public MeterLog changeTime(MeterLog ml, LocalTime newTme) {
        ml.setLogTime(newTme);
        meterLogDao.update(ml);
        return ml;
    }

    @Override
    public boolean isInDateFrame(MeterLog ml, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            log.error("MeterLogService error, called isInDateFrame with  start date: "
                    +  startDate.toString() + " after end date: "+ endDate.toString());
            throw new IllegalArgumentException("Start date of the time frame cannot be after end date");
        }
        return !ml.getLogDate().isBefore(startDate) && !ml.getLogDate().isAfter(endDate);
    }

    @Override
    public boolean isInDayTime(MeterLog ml, DayTime dayTime) {
        switch (dayTime) {
            case Day: {
                return !ml.getLogTime().isAfter(DayTime.Day.getEnd()) &&
                        !ml.getLogTime().isBefore(DayTime.Day.getStart());
            }
            case Night: {
                if (ml.getLogTime().equals(DayTime.Night.getEnd()) ||
                        ml.getLogTime().equals(DayTime.Night.getStart())) {
                    return true;
                }
                if (ml.getLogTime().isAfter(DayTime.Night.getStart()) ||
                        ml.getLogTime().isBefore(DayTime.Night.getEnd())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInDateFrameWithDayTime(MeterLog ml, LocalDate startDate, LocalDate endDate, DayTime dayTime) {
        return isInDateFrame(ml, startDate, endDate) && isInDayTime(ml, dayTime);
    }

    @Override
    public List<MeterLog> filterInDateFrame(List<MeterLog> meterLogs, LocalDate startDate, LocalDate endDate) {
        meterLogs.removeIf(m -> !isInDateFrame(m, startDate, endDate));
        return meterLogs;
    }

    @Override
    public List<MeterLog> filterInDayTime(List<MeterLog> meterLogs, DayTime dayTime) {
        meterLogs.removeIf(m -> !isInDayTime(m, dayTime));
        return meterLogs;
    }

    @Override
    public List<MeterLog> filterInDateFrameWithTimeDay(List<MeterLog> meterLogs, LocalDate startDate, LocalDate endDate, DayTime dayTime) {
        meterLogs.removeIf(m -> (!isInDayTime(m, dayTime) || !isInDateFrame(m, startDate, endDate)));
        return meterLogs;
    }


}
