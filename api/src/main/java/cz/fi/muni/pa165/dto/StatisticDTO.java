package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Martin Podhora
 */
public class StatisticDTO {
    private String userName;
    private double fromToTotalSpent;
    private double fromToAverageSpent;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getFromToTotalSpent() {
        return fromToTotalSpent;
    }

    public void setFromToTotalSpent(double fromToTotalSpent) {
        this.fromToTotalSpent = fromToTotalSpent;
    }

    public double getFromToAverageSpent() {
        return fromToAverageSpent;
    }

    public void setFromToAverageSpent(double fromToAverageSpent) {
        this.fromToAverageSpent = fromToAverageSpent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof StatisticDTO)) return false;
        StatisticDTO that = (StatisticDTO) o;
        return Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getFromToTotalSpent(), that.getFromToTotalSpent()) && Objects.equals(getFromToAverageSpent(), that.getFromToAverageSpent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getFromToTotalSpent(), getFromToAverageSpent());
    }
}
