package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterStatisticsDTO {
    private String smartMeterDescription;
    private  boolean running;
    private double cumulativePowerConsumption;
    private double averageSpentPerNight;
    private double averageSpentPerDay;

    public String getSmartMeterDescription() {
        return smartMeterDescription;
    }

    public void setSmartMeterDescription(String description) {
        this.smartMeterDescription = description;
    }

    public double getCumulativePowerConsumption() {
        return cumulativePowerConsumption;
    }

    public void setCumulativePowerConsumption(double cumulativePowerConsumption) {
        this.cumulativePowerConsumption = cumulativePowerConsumption;
    }

    public double getAverageSpentPerNight() {
        return averageSpentPerNight;
    }

    public void setAverageSpentPerNight(double average) {
        this.averageSpentPerNight = average;
    }

    public double getAverageSpentPerDay() {
        return averageSpentPerDay;
    }

    public void setAverageSpentPerDay(double average) {
        this.averageSpentPerDay = average;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SmartMeterStatisticsDTO)) return false;
        SmartMeterStatisticsDTO that = (SmartMeterStatisticsDTO) o;
        return Objects.equals(getSmartMeterDescription(), that.getSmartMeterDescription()) && Objects.equals(getAverageSpentPerDay(),
                that.getAverageSpentPerDay()) && Objects.equals(getAverageSpentPerNight(), that.getAverageSpentPerNight())
                && Objects.equals(getCumulativePowerConsumption(), that.getCumulativePowerConsumption()) && Objects.equals(isRunning(), that.isRunning());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSmartMeterDescription(), getAverageSpentPerDay(), getAverageSpentPerNight(), getCumulativePowerConsumption(), isRunning());
    }
}
