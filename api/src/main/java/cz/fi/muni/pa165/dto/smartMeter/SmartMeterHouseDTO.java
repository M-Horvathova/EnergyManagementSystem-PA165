package cz.fi.muni.pa165.dto.smartMeter;

import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterHouseDTO {
    private Long id;
    private boolean running;
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
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
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
        if (!(o instanceof SmartMeterHouseDTO)) return false;
        SmartMeterHouseDTO that = (SmartMeterHouseDTO) o;
        return isRunning() == that.isRunning() &&
                Double.compare(that.getPowerConsumptionSinceLastLog(), getPowerConsumptionSinceLastLog()) == 0 &&
                Double.compare(that.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0 &&
                Objects.equals(getLastLogTakenAt(), that.getLastLogTakenAt()) &&
                Objects.equals(getSmartMeterDescription(), that.getSmartMeterDescription());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + (running ? 1 : 0);
        temp = Double.doubleToLongBits(powerConsumptionSinceLastLog);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cumulativePowerConsumption);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (lastLogTakenAt != null ? lastLogTakenAt.hashCode() : 0);
        result = 31 * result + (smartMeterDescription != null ? smartMeterDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SmartMeterHouseDTO{" +
                "id=" + id +
                ", isRunning=" + running +
                ", powerConsumptionSinceLastLog=" + powerConsumptionSinceLastLog +
                ", cumulativePowerConsumption=" + cumulativePowerConsumption +
                ", lastLogTakenAt='" + lastLogTakenAt + '\'' +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                '}';
    }
}
