package cz.fi.muni.pa165.dto.smartMeter;

import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterPowerSpentInIntervalDTO {
    private int yearFrom;
    private int monthFrom;
    private int dayFrom;
    private int yearTo;
    private int monthTo;
    private int dayTo;

    public int getDayFrom() {
        return dayFrom;
    }

    public void setDayFrom(int dayFrom) {
        this.dayFrom = dayFrom;
    }

    public int getMonthFrom() {
        return monthFrom;
    }

    public void setMonthFrom(int monthFrom) {
        this.monthFrom = monthFrom;
    }

    public int getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(int yearFrom) {
        this.yearFrom = yearFrom;
    }

    public int getDayTo() {
        return dayTo;
    }

    public void setDayTo(int dayTo) {
        this.dayTo = dayTo;
    }

    public int getMonthTo() {
        return monthTo;
    }

    public void setMonthTo(int monthTo) {
        this.monthTo = monthTo;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeterPowerSpentInIntervalDTO)) return false;
        SmartMeterPowerSpentInIntervalDTO that = (SmartMeterPowerSpentInIntervalDTO) o;
        return Objects.equals(getDayFrom(), that.getDayFrom()) &&
                Objects.equals(getMonthFrom(), that.getMonthFrom()) &&
                Objects.equals(getYearFrom(), that.getYearFrom())
                && Objects.equals(getDayTo(), that.getDayTo()) &&
                Objects.equals(getMonthTo(), that.getMonthTo()) &&
                Objects.equals(getYearTo(), that.getYearTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDayFrom(), getMonthFrom(), getYearFrom(), getDayTo(), getMonthTo(), getYearTo());
    }
}
