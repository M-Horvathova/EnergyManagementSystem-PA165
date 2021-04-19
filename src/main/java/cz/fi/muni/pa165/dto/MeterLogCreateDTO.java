package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.SmartMeter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class MeterLogCreateDTO {
    private Long measure;
    private SmartMeter smartMeter;
    private LocalDate logDate;
    private LocalTime logTime;

    public Long getMeasure() {
        return measure;
    }

    public void setMeasure(Long measure) {
        this.measure = measure;
    }

    public SmartMeter getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(SmartMeter smartMeter) {
        this.smartMeter = smartMeter;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public LocalTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalTime logTime) {
        this.logTime = logTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeterLogCreateDTO)) return false;
        MeterLogCreateDTO that = (MeterLogCreateDTO) o;
        return getMeasure().equals(that.getMeasure()) && getSmartMeter().equals(that.getSmartMeter()) && Objects.equals(getLogDate(), that.getLogDate()) && Objects.equals(getLogTime(), that.getLogTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMeasure(), getSmartMeter(), getLogDate(), getLogTime());
    }

    @Override
    public String toString() {
        return "MeterLogCreateDTO{" +
                "measure=" + measure +
                ", smartMeter=" + smartMeter +
                ", logDate=" + logDate +
                ", logTime=" + logTime +
                '}';
    }
}
