package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.MeterLogService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MeterLogFacadeImpl implements MeterLogFacade{
    final static Logger log = LoggerFactory.getLogger(MeterLogFacadeImpl.class);

    private final MeterLogService meterLogService;

    private final BeanMappingService beanMappingService;

    @Autowired
    public MeterLogFacadeImpl(MeterLogService meterLogService, BeanMappingService beanMappingService) {
        this.meterLogService = meterLogService;
        this.beanMappingService = beanMappingService;
    }


    @Override
    public Long createMeterLog(MeterLogCreateDTO ml) {
        MeterLog mappedLog = beanMappingService.mapTo(ml, MeterLog.class);

        return null;
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
            return null;
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
            return null;
        }
        if (sDate == null || eDate == null) {
            return null;
        }
        List<MeterLog> res = meterLogService.findInDateFrameWithDayTime(sDate, eDate, dTime);
        return beanMappingService.mapTo(res, MeterLogDTO.class);
    }
}