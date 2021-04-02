package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class SmartMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "smartMeter")
    private Set<MeterLog> meterLogs;

    @Column(nullable = false)
    private boolean isRunning;

    @Column(nullable = false)
    private double powerConsumptionSinceLastLog;

    @Column(nullable = false)
    private double cumulativePowerConsumption;

    @Column(nullable = true)
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastLogTakenAt;

    public SmartMeter() {
    }

    public SmartMeter(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Long getId() {
        return id;
    }

    public double getCumulativePowerConsumption() {
        return cumulativePowerConsumption;
    }

    public void setCumulativePowerConsumption(double cumulativePowerConsumption) {
        this.cumulativePowerConsumption = cumulativePowerConsumption;
    }

    public double getPowerConsumptionSinceLastLog() {
        return powerConsumptionSinceLastLog;
    }

    public void setPowerConsumptionSinceLastLog(double powerConsumptionSinceLastLog) {
        this.powerConsumptionSinceLastLog = powerConsumptionSinceLastLog;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public LocalDateTime getLastLogTakenAt() {
        return  lastLogTakenAt;
    }

    public void setLastLogTakenAt(LocalDateTime lastLogTakenAt) {
        this.lastLogTakenAt = lastLogTakenAt;
    }

    public Set<MeterLog> getMeterLogs() {
        return meterLogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeter )) return false;

        SmartMeter sm = (SmartMeter) o;
        return isRunning() == sm.isRunning() && Double.compare(sm.getPowerConsumptionSinceLastLog(), getPowerConsumptionSinceLastLog()) == 0
                && Double.compare(sm.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0
                && getLastLogTakenAt().equals(sm.getLastLogTakenAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getPowerConsumptionSinceLastLog(), getCumulativePowerConsumption(), getLastLogTakenAt());
    }
}
