package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.DayTime;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author Michaela Horváthová
 */

/*
  This class represents a single measurement of a particular smart meter
 */
@Entity
public class MeterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Date when the measurement was taken
     */
    @Column(nullable = false)
    private LocalDate logDate;

    /*
        Time when the measurement was taken
     */
    @Column(nullable = false)
    private LocalTime logTime;

    /*
        Measured value
     */
    @Column(nullable = false)
    private Long measure;

    @ManyToOne/*(optional = false)*/
    private SmartMeter smartMeter;

    @Column
    private LocalDateTime createStamp;

    @PrePersist
    protected void onCreate() {
        if (this.createStamp == null) {
            this.createStamp=LocalDateTime.now();
        }
        if (this.logDate == null) {
            this.logDate = LocalDate.of(this.createStamp.getYear(),
                    this.createStamp.getMonth(), this.createStamp.getDayOfMonth());
        }
        if (this.logTime == null) {
            this.logTime = LocalTime.of(this.createStamp.getHour(),
                    this.createStamp.getMinute(), this.createStamp.getSecond());
        }

        if (smartMeter != null) {
            smartMeter.setLastLogTakenAt(LocalDateTime.now());
        }
    }

    public MeterLog() {
    }

    public MeterLog(Long id, LocalDate logDate, LocalTime logTime, Long measure, SmartMeter smartMeter) {
        this.id = id;
        this.smartMeter = smartMeter;
        if (logDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Future date is not allowed");
        }
        this.logDate = logDate;
        this.logTime = logTime;
        this.measure = measure;
    }

    public LocalDateTime getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(LocalDateTime createStamp) {
        this.createStamp = createStamp;
        this.setLogDate(createStamp.toLocalDate());
        this.setLogTime(createStamp.toLocalTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLogDate() {
        if (logDate == null) {
            this.logDate = LocalDate.of(this.createStamp.getYear(),
                    this.createStamp.getMonth(), this.createStamp.getDayOfMonth());
        }
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        if (logDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Future date is not allowed");
        }
        this.logDate = logDate;
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

    public boolean isWithinDayTime(DayTime dayTime) {
       boolean inDay = true;
       if (this.logTime.isBefore(DayTime.Day.getStart()) || this.logTime.isAfter(DayTime.Day.getEnd())) {
           inDay = false;
       }
       if (dayTime == DayTime.Day) {
           return inDay;
       }
       return !inDay;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeterLog)) return false;
        MeterLog meterLog = (MeterLog) o;
        return Objects.equals(getLogDate(), meterLog.getLogDate()) && Objects.equals(getLogTime(),
                meterLog.getLogTime()) && getMeasure().equals(meterLog.getMeasure()) &&
                getSmartMeter().equals(meterLog.getSmartMeter()) && getCreateStamp().equals(meterLog.getCreateStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMeasure(), getSmartMeter(), getCreateStamp());
    }

    public LocalTime getLogTime() {
        if (logTime == null) {
            this.logTime = LocalTime.of(this.createStamp.getHour(),
                    this.createStamp.getMinute(), this.createStamp.getSecond());
        }
        return logTime;
    }

    public void setLogTime(LocalTime logTime) {
        this.logTime = logTime;
    }
}