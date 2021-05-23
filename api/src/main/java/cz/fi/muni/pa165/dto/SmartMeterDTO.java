package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.entity.MeterLog;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
/**
 * @author Matej Rišňovský
 */
public class SmartMeterDTO {
    private Long id;
    private Set<MeterLog> meterLogs;
    private boolean running;
    private double powerConsumptionSinceLastLog;
    private double cumulativePowerConsumption;
    private LocalDateTime lastLogTakenAt;
    private String smartMeterDescription;
    private Long houseId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }

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
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public LocalDateTime getLastLogTakenAt() {
        return  lastLogTakenAt;
    }

    public void setLastLogTakenAt(LocalDateTime lastLogTakenAt) {
        if (lastLogTakenAt != null && lastLogTakenAt.isAfter(LocalDateTime.now())) {
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

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeterDTO)) return false;

        SmartMeterDTO sm = (SmartMeterDTO) o;
        return isRunning() == sm.isRunning() && Double.compare(sm.getPowerConsumptionSinceLastLog(), getPowerConsumptionSinceLastLog()) == 0
                && Double.compare(sm.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0
                && getHouseId().equals(sm.getHouseId())
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) || (getSmartMeterDescription() != null && getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getPowerConsumptionSinceLastLog(), getCumulativePowerConsumption(), getHouseId(), getSmartMeterDescription());
    }

    @Override
    public String toString() {
        return "SmartMeterDTO{" +
                "id=" + id +
                ", isRunning=" + running +
                ", cumulativePowerConsumption=" + cumulativePowerConsumption +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                ", houseId=" + houseId +
                '}';
    }
}
