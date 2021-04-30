package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.SmartMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Matej Rišňovský
 */
@Service
@Transactional
public class SmartMeterFacadeImpl implements SmartMeterFacade {

    private SmartMeterService smartMeterService;
    private MeterLogService meterLogService;
    private HouseService houseService;

    private BeanMappingService beanMappingService;

    @Autowired
    public SmartMeterFacadeImpl(
            SmartMeterService smartMeterService,
            HouseService houseService,
            BeanMappingService beanMappingService, MeterLogService meterLogService) {
        this.smartMeterService = smartMeterService;
        this.houseService = houseService;
        this.beanMappingService = beanMappingService;
        this.meterLogService = meterLogService;
    }

    @Override
    public Long createSmartMeter(SmartMeterCreateDTO smartMeter) {
        SmartMeter sm = beanMappingService.mapTo(smartMeter, SmartMeter.class);
        sm = smartMeterService.create(sm);

        houseService.addSmartMeter(sm.getHouse(), sm);
        return sm.getId();
    }

    @Override
    public SmartMeterDTO updateSmartMeter(SmartMeterDTO smartMeter) {
        SmartMeter sm = beanMappingService.mapTo(smartMeter, SmartMeter.class);
        return (smartMeterService.update(sm) == null) ? null : beanMappingService.mapTo(sm, SmartMeterDTO.class);
    }

    @Override
    public SmartMeterDTO getSmartMeter(Long id) {
        SmartMeter sm = smartMeterService.findById(id);
        return  sm == null ? null : beanMappingService.mapTo(sm, SmartMeterDTO.class);
    }

    @Override
    public List<SmartMeterDTO> getAllSmartMeters() {
        return beanMappingService.mapTo(smartMeterService.findAll(), SmartMeterDTO.class);
    }

    @Override
    public List<SmartMeterDTO> getRunningSmartMetes() {
        return beanMappingService.mapTo(smartMeterService.getRunningSmartMeters(), SmartMeterDTO.class);
    }

    @Override
    public void deleteSmartMeter(SmartMeterDTO smartMeter) {
        SmartMeter sm = beanMappingService.mapTo(smartMeter, SmartMeter.class);
        houseService.removeSmartMeter(sm.getHouse(), sm);
        smartMeterService.delete(sm);
    }

    @Override
    public double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeterDTO smartMeter) {
        SmartMeter sm =  beanMappingService.mapTo(smartMeter, SmartMeter.class);
        return smartMeterService.getPowerSpentForDateForSmartMeter(date, sm);
    }

    @Override
    public double getPowerSpentForSmartMeterInTimeRange(LocalDateTime from, LocalDateTime to, SmartMeterDTO smartMeter) {
        SmartMeter sm =  beanMappingService.mapTo(smartMeter, SmartMeter.class);
        return smartMeterService.sumPowerFromLogs(meterLogService.filterInDateFrame(new ArrayList<>(sm.getMeterLogs()), from.toLocalDate(), to.toLocalDate()));
    }

    @Override
    public double getPowerSpentForDateFrameWithDayTime(LocalDate from, LocalDate to, SmartMeterDTO smartMeter, DayTime dayTime) {
        SmartMeter sm =  beanMappingService.mapTo(smartMeter, SmartMeter.class);
        return smartMeterService.sumPowerFromLogs(meterLogService.filterInDateFrameWithTimeDay(new ArrayList<>(sm.getMeterLogs()), from, to, dayTime));
    }

    @Override
    public double getAllPowerSpent() {
        return  smartMeterService.getAllPowerSpent();
    }
}
