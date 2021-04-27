package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.SmartMeter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author Michaela Horváthová
 */
public class MeterLogDTO {
    private LocalDate logDate;
    private LocalTime logTime;
    private Long measure;
    private SmartMeter smartMeter;
    private LocalDateTime createStamp;

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

    public LocalDateTime getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(LocalDateTime createStamp) {
        this.createStamp = createStamp;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeterLogDTO)) return false;
        MeterLogDTO that = (MeterLogDTO) o;
        return getLogDate().equals(that.getLogDate())
                && getLogTime().equals(that.getLogTime())
                && getMeasure().equals(that.getMeasure())
                && getSmartMeter().equals(that.getSmartMeter())
                && Objects.equals(getCreateStamp(), that.getCreateStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogDate(), getLogTime(), getMeasure(), getSmartMeter(), getCreateStamp());
    }

    @Override
    public String toString() {
        return "MeterLogDTO{" +
                "logDate=" + logDate +
                ", logTime=" + logTime +
                ", measure=" + measure +
                ", smartMeter=" + smartMeter +
                '}';
    }
}
