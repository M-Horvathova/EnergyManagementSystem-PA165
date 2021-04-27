package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.SmartMeterService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


/**
 * @author Michaela Horváthová
 */
@Service
@Transactional
public class MeterLogFacadeImpl implements MeterLogFacade {
    final static Logger log = LoggerFactory.getLogger(MeterLogFacadeImpl.class);

    private MeterLogService meterLogService;
    private SmartMeterService smartMeterService;
    private BeanMappingService beanMappingService;

    @Autowired
    public MeterLogFacadeImpl(MeterLogService meterLogService, SmartMeterService smartMeterService, BeanMappingService beanMappingService) {
        this.meterLogService = meterLogService;
        this.smartMeterService = smartMeterService;
        this.beanMappingService = beanMappingService;
    }


    @Override
    public Long createMeterLog(MeterLogCreateDTO ml) {
        SmartMeter mappedMeter = smartMeterService.findById(ml.getSmartMeterId());
        MeterLog mappedLog = new MeterLog();
        mappedLog.setSmartMeter(mappedMeter);
        mappedLog.setLogTime(ml.getLogTime());
        mappedLog.setLogDate(ml.getLogDate());
        mappedLog.setMeasure(ml.getMeasure());
        MeterLog createdLog = meterLogService.createMeterLog(mappedLog);
        return createdLog.getId();
    }

    @Override
    public void deleteMeterLog(Long meterLogId) {
        MeterLog ml = meterLogService.findById(meterLogId);
        meterLogService.deleteMeterLog(ml);
    }

    @Override
    public MeterLogDTO getMeterLogWithId(Long id) {
        MeterLog ml = meterLogService.findById(id);
        return (ml == null) ? null : beanMappingService.mapTo(ml, MeterLogDTO.class);
    }

    @Override
    public List<MeterLogDTO> getAllMeterLogs() {
        List<MeterLog> res = meterLogService.findAll();
        return beanMappingService.mapTo(res, MeterLogDTO.class);
    }

    @Override
    public List<MeterLogDTO> getLogsInTimeFrame(String startDate, String endDate) {
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);
        if (sDate == null || eDate == null) {
            log.error("MeterLogFacade method getLogsInTimeFrame with start date: "
                    + startDate + " end date: " + endDate + " - not able to process dates correctly.");
            throw new IllegalArgumentException("Dates cannot be converted properly");
        }
        List<MeterLog> res = meterLogService.findInDateFrame(sDate, eDate);
        return beanMappingService.mapTo(res, MeterLogDTO.class);
    }

    @Override
    public List<MeterLogDTO> getLogsInTimeFrameWithDaytime(String startDate, String endDate, String dayTime) {
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);
        DayTime dTime;
        try {
            dTime = DayTime.valueOf(dayTime);
        } catch (Exception e) {
            log.error("MeterLogFacade method getLogsInTimeFrameWithDaytime with time of day: "
                    + dayTime + " - is not correct time of day.");
            throw new IllegalArgumentException("Cannot convert time of day to enum");
        }
        if (sDate == null || eDate == null) {
            log.error("MeterLogFacade method getLogsInTimeFrameWithDaytime with start date: "
                    + startDate + " end date: " + endDate + " - not able to process dates correctly.");
            throw new IllegalArgumentException("Dates cannot be converted properly");
        }
        List<MeterLog> res = meterLogService.findInDateFrameWithDayTime(sDate, eDate, dTime);
        return beanMappingService.mapTo(res, MeterLogDTO.class);
    }
}
