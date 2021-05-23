package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterPowerSpentDTO {
    private int year;
    private int month;
    private int day;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SmartMeterPowerSpentDTO)) return false;
        SmartMeterPowerSpentDTO that = (SmartMeterPowerSpentDTO) o;
        return Objects.equals(getDay(), that.getDay()) && Objects.equals(getMonth(), that.getMonth()) && Objects.equals(getYear(), that.getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDay(), getMonth(), getYear());
    }
}
