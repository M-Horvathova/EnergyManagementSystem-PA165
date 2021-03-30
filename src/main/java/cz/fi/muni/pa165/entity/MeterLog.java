package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.DayTime;

import javax.persistence.*;
import java.time.LocalDate;
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

    public MeterLog() {
    }

    public MeterLog(Long id, LocalDate logDate, LocalTime logTime, Long measure) throws IllegalArgumentException{
        this.id = id;
        if (logDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Future date is not allowed");
        }
        this.logDate = logDate;
        this.logTime = logTime;
        this.measure = measure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) throws IllegalArgumentException{
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

    public boolean isWithinDayTime(DayTime dayTime) {
        return !this.logTime.isAfter(dayTime.getEnd()) && !this.logTime.isBefore(dayTime.getStart());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeterLog)) return false;
        MeterLog meterLog = (MeterLog) o;
        return getLogDate().equals(meterLog.getLogDate()) && getMeasure().equals(meterLog.getMeasure());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogDate(), getMeasure());
    }

    public LocalTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalTime logTime) {
        this.logTime = logTime;
    }
}