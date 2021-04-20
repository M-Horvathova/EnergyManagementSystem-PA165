package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dto.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.SmartMeterService;
import cz.fi.muni.pa165.service.SmartMeterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void create(SmartMeterCreateDTO smartMeter) {
        SmartMeter sm = beanMappingService.mapTo(smartMeter, SmartMeter.class);
        smartMeterService.create(sm);
    }

    @Override
    public SmartMeterDTO update(SmartMeterDTO smartMeter) {
        SmartMeter sm = beanMappingService.mapTo(smartMeter, SmartMeter.class);
        return (smartMeterService.update(sm) == null) ? null : beanMappingService.mapTo(sm, SmartMeterDTO.class);
    }

    @Override
    public SmartMeterDTO findById(Long id) {
        SmartMeter sm = smartMeterService.findById(id);
        return  sm == null ? null : beanMappingService.mapTo(sm, SmartMeterDTO.class);
    }

    @Override
    public List<SmartMeterDTO> findAll() {
        return beanMappingService.mapTo(smartMeterService.findAll(), SmartMeterDTO.class);
    }

    @Override
    public List<SmartMeterDTO> findByRunning(boolean running) {
        return beanMappingService.mapTo(smartMeterService.findByRunning(running), SmartMeterDTO.class);
    }

    @Override
    public void delete(SmartMeterDTO smartMeter) {
        SmartMeter sm = beanMappingService.mapTo(smartMeter, SmartMeter.class);
        smartMeterService.delete(sm);
    }
}
