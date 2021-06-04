package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.SmartMeterEditDTO;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.facade.SmartMeterFacade;
import cz.fi.muni.pa165.dto.SmartMeterCreateDTO;
import cz.fi.muni.pa165.dto.SmartMeterDTO;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.enums.DayTime;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.SmartMeterService;
import cz.fi.muni.pa165.service.mappers.SmartMeterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Matej Rišňovský
 */
@Service
@Transactional
public class SmartMeterFacadeImpl implements SmartMeterFacade {

    private SmartMeterService smartMeterService;
    private MeterLogService meterLogService;
    private HouseService houseService;

    private SmartMeterMapper smartMeterMapper;

    @Autowired
    public SmartMeterFacadeImpl(
            SmartMeterService smartMeterService,
            HouseService houseService, MeterLogService meterLogService, SmartMeterMapper smartMeterMapper) {
        this.smartMeterService = smartMeterService;
        this.houseService = houseService;
        this.meterLogService = meterLogService;
        this.smartMeterMapper = smartMeterMapper;
    }

    @Override
    public Long createSmartMeter(SmartMeterCreateDTO smartMeter) {
        House house = houseService.findById(smartMeter.getHouseId());
        SmartMeter sm = new SmartMeter();
        sm.setHouse(house);
        sm.setRunning(smartMeter.isRunning());
        sm.setSmartMeterDescription(smartMeter.getSmartMeterDescription());

        sm = smartMeterService.create(sm);

        houseService.addSmartMeter(house, sm);
        return sm.getId();
    }

    @Override
    public SmartMeterDTO updateSmartMeter(SmartMeterEditDTO smartMeter) {
        SmartMeter sm = smartMeterService.findById(smartMeter.getId());
        sm.setRunning(smartMeter.isRunning());
        sm.setSmartMeterDescription(smartMeter.getSmartMeterDescription());

        sm = smartMeterService.update(sm);
        return (sm == null) ? null : smartMeterMapper.smartMeterToDTO(sm);
    }

    @Override
    public SmartMeterDTO getSmartMeter(Long id) {
        SmartMeter sm = smartMeterService.findById(id);
        return  sm == null ? null : smartMeterMapper.smartMeterToDTO(sm);
    }

    @Override
    public List<SmartMeterDTO> getSmartMeterByHouse(Long id) {
        House house = houseService.findById(id);
        return smartMeterMapper.smartMetersToDTOs(smartMeterService.findByHouse(house));
    }

    @Override
    public List<SmartMeterDTO> getAllSmartMeters() {
        return smartMeterMapper.smartMetersToDTOs(smartMeterService.findAll());
    }

    @Override
    public List<SmartMeterDTO> getRunningSmartMetes() {
        return smartMeterMapper.smartMetersToDTOs(smartMeterService.getRunningSmartMeters());
    }

    @Override
    public void deleteSmartMeter(SmartMeterDTO smartMeter) {
        SmartMeter sm = smartMeterService.findById(smartMeter.getId());
        Set<MeterLog> meterLogs = sm.getMeterLogs();

        if (meterLogs != null) {
            for (MeterLog meterLog : meterLogs) {
                meterLogService.deleteMeterLog(meterLog);
            }
        }

        smartMeterService.delete(sm);
        houseService.removeSmartMeter(sm.getHouse(), sm);
    }

    @Override
    public double getPowerSpentForDateForSmartMeter(LocalDate date, SmartMeterDTO smartMeter) {
        SmartMeter sm = smartMeterService.findById(smartMeter.getId());
        return smartMeterService.getPowerSpentForDateForSmartMeter(date, sm);
    }

    @Override
    public double getPowerSpentForSmartMeterInDateRange(LocalDate from, LocalDate to, SmartMeterDTO smartMeter) {
        SmartMeter sm = smartMeterService.findById(smartMeter.getId());
        return smartMeterService.sumPowerFromLogs(meterLogService.filterInDateFrame(new ArrayList<>(sm.getMeterLogs()), from, to));
    }

    @Override
    public double getAveragePowerSpentForDayTimeSmartMeter(Long id, DayTime dayTime) {
        SmartMeter sm = smartMeterService.findById(id);
        return  smartMeterService.getAveragePowerSpentForDayTimeSmartMeter(sm, dayTime);
    }

    @Override
    public double getPowerSpentForDateFrameWithDayTime(LocalDate from, LocalDate to, SmartMeterDTO smartMeter, DayTime dayTime) {
        SmartMeter sm = smartMeterService.findById(smartMeter.getId());
        return smartMeterService.sumPowerFromLogs(meterLogService.filterInDateFrameWithTimeDay(new ArrayList<>(sm.getMeterLogs()), from, to, dayTime));
    }



    @Override
    public double getAllPowerSpent() {
        return  smartMeterService.getAllPowerSpent();
    }
}
