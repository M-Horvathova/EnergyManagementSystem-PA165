package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.entity.SmartMeter;

import java.util.List;

public interface MeterLogFacade {
    public void createMeterLog(MeterLogCreateDTO ml);
    public void addSmartMeter(Long meterLogId, Long smartMeterId);
    public void removeSmartMeter(Long meterLogId, Long smartMeterId);
    public void changeSmartMeter(Long meterLogId, Long smartMeterId);
    public void deleteMeterLog(Long meterLogId);
    public MeterLogDTO getMeterLogWithId(Long id);
    public List<MeterLogDTO> getAllMeterLogs();
    public List<MeterLogDTO> getLogsInTimeFrame(String startDate, String endDate);
    public List<MeterLogDTO> getLogsInTimeFrameWithDaytime(String startDate, String endDate, String dayTime);
}
