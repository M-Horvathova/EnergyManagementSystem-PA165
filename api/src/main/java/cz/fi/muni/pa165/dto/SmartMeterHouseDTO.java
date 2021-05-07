package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.SmartMeter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterHouseDTO {
    private Long id;
    private boolean isRunning;
    private double powerConsumptionSinceLastLog;
    private double cumulativePowerConsumption;
    private LocalDateTime lastLogTakenAt;
    private String smartMeterDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeter)) return false;

        SmartMeter sm = (SmartMeter) o;
        return isRunning() == sm.isRunning() && Double.compare(sm.getPowerConsumptionSinceLastLog(), getPowerConsumptionSinceLastLog()) == 0
                && Double.compare(sm.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) || (getSmartMeterDescription() != null && getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getPowerConsumptionSinceLastLog(), getCumulativePowerConsumption(), getSmartMeterDescription());
    }

    @Override
    public String toString() {
        return "SmartMeterDTO{" +
                "id=" + id +
                ", isRunning=" + isRunning +
                ", cumulativePowerConsumption=" + cumulativePowerConsumption +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                '}';
    }
}
