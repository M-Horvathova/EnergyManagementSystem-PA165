package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Transactional
@Service
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class MeterLogSampleData {
    @Autowired
    private MeterLogDao meterLogDao;

    @Autowired
    private SmartMeterDao smartMeterDao;

    @Autowired
    private SmartMeterSampleData smartMeterSampleData;


    private MeterLog meterLog100;
    private MeterLog meterLog101;
    private MeterLog meterLog102;

    public MeterLog getMeterLog100() {
        if (meterLog100 == null) {
            return generateMeterLog100();
        }
        return meterLog100;
    }

    public void setMeterLog100(MeterLog meterLog100) {
        this.meterLog100 = meterLog100;
    }

    public MeterLog getMeterLog101() {
        if (meterLog101 == null) {
            return generateMeterLog101();
        }
        return meterLog101;
    }

    public void setMeterLog101(MeterLog meterLog101) {
        this.meterLog101 = meterLog101;
    }

    public MeterLog getMeterLog102() {
        if (meterLog102 == null) {
            return generateMeterLog102();
        }
        return meterLog102;
    }

    public void setMeterLog102(MeterLog meterLog102) {
        this.meterLog102 = meterLog102;
    }

    public MeterLog generateMeterLog100() {
        MeterLog meterLog0 = new MeterLog();
        meterLog0.setId(100L);
        meterLog0.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog0.setLogTime(LocalTime.of(0, 30));
        meterLog0.setMeasure(123L);
        SmartMeter sm = smartMeterSampleData.generateSmartMeter50();
        meterLog0.setSmartMeter(sm);
        this.meterLog100 = meterLog0;
        meterLogDao.create(meterLog0);
        return meterLog0;
    }

    public  MeterLog generateMeterLog101() {
        MeterLog meterLog1 = new MeterLog();
        meterLog1.setId(101L);
        meterLog1.setLogDate(LocalDate.of(2020, 1, 1));
        meterLog1.setLogTime(LocalTime.of(15, 30));
        meterLog1.setMeasure(1L);
        this.meterLog101 = meterLog1;
        return meterLog1;
    }

    public  MeterLog generateMeterLog102() {
        MeterLog meterLog2 = new MeterLog();
        meterLog2.setId(102L);
        meterLog2.setLogDate(LocalDate.of(2020, 1, 2));
        meterLog2.setLogTime(LocalTime.of(1, 0));
        meterLog2.setMeasure(21L);
        this.meterLog102 = meterLog2;
        return meterLog2;
    }
}
