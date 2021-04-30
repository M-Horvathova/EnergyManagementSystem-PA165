package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.MeterLog;
import cz.fi.muni.pa165.entity.SmartMeter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
/**
 * @author Matej Rišňovský
 */
public class SmartMeterDTO {
    private Long id;
    private Set<MeterLog> meterLogs;
    private boolean isRunning;
    private double powerConsumptionSinceLastLog;
    private double cumulativePowerConsumption;
    private LocalDateTime lastLogTakenAt;
    private String smartMeterDescription;
    private House house;

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
        if (!(o instanceof SmartMeter)) return false;

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

    @Override
    public String toString() {
        return "SmartMeterDTO{" +
                "id=" + id +
                ", isRunning=" + isRunning +
                ", cumulativePowerConsumption=" + cumulativePowerConsumption +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                ", house=" + house +
                '}';
    }
}
