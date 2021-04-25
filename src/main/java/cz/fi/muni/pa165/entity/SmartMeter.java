package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
/**
 * @author Matej Rišňovský
 */

@Entity
public class SmartMeter implements Serializable{

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
    private LocalDateTime lastLogTakenAt;

    @Column(nullable = true)
    private String smartMeterDescription;

    @ManyToOne/*(optional = false)*/
    private House house;

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
        if (lastLogTakenAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Future dateTime is not allowed");
        }
        this.lastLogTakenAt = lastLogTakenAt;
    }

    public String getSmartMeterDescription() {
        return smartMeterDescription;
    }

    public void setSmartMeterDescription(String description) {
        this.smartMeterDescription = description;
    }

    public Set<MeterLog> getMeterLogs() {
        return meterLogs;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeter )) return false;

        SmartMeter sm = (SmartMeter) o;
        return isRunning() == sm.isRunning() && Double.compare(sm.getPowerConsumptionSinceLastLog(), getPowerConsumptionSinceLastLog()) == 0
                && Double.compare(sm.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0
                && ((getHouse() == null && sm.getHouse() == null) || (getHouse() != null && getHouse().equals(sm.getHouse())))
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) || (getSmartMeterDescription() != null && getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getPowerConsumptionSinceLastLog(), getCumulativePowerConsumption(), getHouse(), getSmartMeterDescription());
    }
}
