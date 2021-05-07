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
    private String lastLogTakenAt;
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

    public String getLastLogTakenAt() {
        return  lastLogTakenAt;
    }

    public void setLastLogTakenAt(String lastLogTakenAt) {
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
        return "SmartMeterHouseDTO{" +
                "id=" + id +
                ", isRunning=" + isRunning +
                ", powerConsumptionSinceLastLog=" + powerConsumptionSinceLastLog +
                ", cumulativePowerConsumption=" + cumulativePowerConsumption +
                ", lastLogTakenAt='" + lastLogTakenAt + '\'' +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                '}';
    }
}
