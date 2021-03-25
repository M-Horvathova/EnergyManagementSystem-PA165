package energySystem.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Michaela Horváthová
 */

@Entity
/*
  This class represents a single measurement of a particular smart meter
 */
public class MeterLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /*
        Date and time when the measurement was taken
     */
    @Column(nullable = false)
    private LocalDateTime logDate;

    /*
        Measured value
     */
    @Column(nullable = false)
    private Long measure;

    public MeterLog() {
    }

    public MeterLog(Long id, LocalDateTime logDate, Long measure) {
        this.id = id;
        this.logDate = logDate;
        this.measure = measure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public Long getMeasure() {
        return measure;
    }

    public void setMeasure(Long measure) {
        this.measure = measure;
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
}