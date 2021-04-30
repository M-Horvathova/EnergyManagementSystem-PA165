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
public class SmartMeterCreateDTO {
    private boolean isRunning;
    private double cumulativePowerConsumption;
    private String smartMeterDescription;
    private long houseId;

    public double getCumulativePowerConsumption() {
        return cumulativePowerConsumption;
    }

    public void setCumulativePowerConsumption(double cumulativePowerConsumption) {
        this.cumulativePowerConsumption = cumulativePowerConsumption;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getSmartMeterDescription() {
        return smartMeterDescription;
    }

    public void setSmartMeterDescription(String description) {
        this.smartMeterDescription = description;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeterCreateDTO)) return false;

        SmartMeterCreateDTO sm = (SmartMeterCreateDTO) o;
        return isRunning() == sm.isRunning()
                && Double.compare(sm.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0
                && getHouseId() == sm.getHouseId()
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) || (getSmartMeterDescription() != null && getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getCumulativePowerConsumption(), getHouseId(), getSmartMeterDescription());
    }

    @Override
    public String toString() {
        return "SmartMeterCreateDTO{" +
                "isRunning=" + isRunning +
                ", cumulativePowerConsumption=" + cumulativePowerConsumption +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                ", houseId=" + houseId +
                '}';
    }
}
