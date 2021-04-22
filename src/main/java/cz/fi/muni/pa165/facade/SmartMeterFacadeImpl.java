package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.service.SmartMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
/**
 * @author Matej Rišňovský
 */
@Service
@Transactional
public class SmartMeterFacadeImpl implements SmartMeterFacade {

    private final SmartMeterService smartMeterService;

    private final BeanMappingService beanMappingService;

    @Autowired
    public SmartMeterFacadeImpl(SmartMeterService smartMeterService, BeanMappingService beanMappingService) {
        this.smartMeterService = smartMeterService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public void createSmartMeter(SmartMeterCreateDTO smartMeter) {
        SmartMeter sm = beanMappingService.mapTo(smartMeter, SmartMeter.class);
        smartMeterService.create(sm);
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
        smartMeterService.delete(sm);
    }

    @Override
    public double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeterDTO smartMeter) {
        SmartMeter sm =  beanMappingService.mapTo(smartMeter, SmartMeter.class);
        return smartMeterService.getPowerSpentForDateForSmartMeter(date, sm);
    }

    @Override
    public double getAllPowerSpent() {
        return  smartMeterService.getAllPowerSpent();
    }
}
