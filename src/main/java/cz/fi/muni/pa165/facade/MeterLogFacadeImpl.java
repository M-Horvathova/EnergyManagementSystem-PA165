package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.service.MeterLogService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createMeterLog(MeterLogCreateDTO ml) {
        MeterLog mappedLog = beanMappingService.mapTo(ml, MeterLog.class);



    }

    @Override
    public void addSmartMeter(Long meterLogId, Long smartMeterId) {

    }

    @Override
    public void removeSmartMeter(Long meterLogId, Long smartMeterId) {

    }

    @Override
    public void changeSmartMeter(Long meterLogId, Long smartMeterId) {

    }

    @Override
    public void deleteMeterLog(Long meterLogId) {

    }

    @Override
    public MeterLogDTO getMeterLogWithId(Long id) {
        return null;
    }

    @Override
    public List<MeterLogDTO> getAllMeterLogs() {
        return null;
    }

    @Override
    public List<MeterLogDTO> getLogsInTimeFrame(String startDate, String endDate) {
        return null;
    }

    @Override
    public List<MeterLogDTO> getLogsInTimeFrameWithDaytime(String startDate, String endDate, String dayTime) {
        return null;
    }
}
