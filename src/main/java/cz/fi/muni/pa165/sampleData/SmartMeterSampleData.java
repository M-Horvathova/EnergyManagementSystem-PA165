package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Transactional
public class SmartMeterSampleData {

    private SmartMeter smartMeter50;

    private MeterLogSampleData meterLogSampleData = new MeterLogSampleData();

    public SmartMeter getSmartMeter50() {
        if (smartMeter50 == null) {
            return generateSmartMeter50();
        }
        return smartMeter50;
    }

    public void setSmartMeter50(SmartMeter smartMeter50) {
        this.smartMeter50 = smartMeter50;
    }

    public SmartMeter generateSmartMeter50() {
        SmartMeter sm1 = new SmartMeter();

        sm1.setCumulativePowerConsumption(100);
        sm1.setLastLogTakenAt(LocalDateTime.of(LocalDate.of(2021, 1, 23), LocalTime.of(16, 30)));
        sm1.setPowerConsumptionSinceLastLog(100);
        sm1.setRunning(true);
        this.smartMeter50 = sm1;
        return sm1;
    }
}
