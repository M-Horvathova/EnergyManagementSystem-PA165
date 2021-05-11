package cz.fi.muni.pa165.dto;

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
        if (o == null || getClass() != o.getClass()) return false;

        SmartMeterHouseDTO that = (SmartMeterHouseDTO) o;

        if (running != that.running) return false;
        if (Double.compare(that.powerConsumptionSinceLastLog, powerConsumptionSinceLastLog) != 0) return false;
        if (Double.compare(that.cumulativePowerConsumption, cumulativePowerConsumption) != 0) return false;
        if (!id.equals(that.id)) return false;
        if (!Objects.equals(lastLogTakenAt, that.lastLogTakenAt))
            return false;
        return Objects.equals(smartMeterDescription, that.smartMeterDescription);
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
