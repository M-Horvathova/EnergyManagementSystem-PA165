package cz.fi.muni.pa165.dto.smartMeter;

import java.util.Objects;

/**
 * @author Michaela Horváthová
 */
public class SmartMeterStatsIntervalDTO {
    private double cumulativePowerConsumption;
    private double totalSpentPerNight;
    private double totalSpentPerDay;

    public double getCumulativePowerConsumption() {
        return cumulativePowerConsumption;
    }

    public void setCumulativePowerConsumption(double cumulativePowerConsumption) {
        this.cumulativePowerConsumption = cumulativePowerConsumption;
    }

    public double getTotalSpentPerNight() {
        return totalSpentPerNight;
    }

    public void setTotalSpentPerNight(double totalSpentPerNight) {
        this.totalSpentPerNight = totalSpentPerNight;
    }

    public double getTotalSpentPerDay() {
        return totalSpentPerDay;
    }

    public void setTotalSpentPerDay(double totalSpentPerDay) {
        this.totalSpentPerDay = totalSpentPerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeterStatsIntervalDTO)) return false;
        SmartMeterStatsIntervalDTO that = (SmartMeterStatsIntervalDTO) o;
        return Double.compare(that.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0 &&
                Double.compare(that.getTotalSpentPerNight(), getTotalSpentPerNight()) == 0 &&
                Double.compare(that.getTotalSpentPerDay(), getTotalSpentPerDay()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCumulativePowerConsumption(), getTotalSpentPerNight(), getTotalSpentPerDay());
    }
}
