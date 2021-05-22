package cz.fi.muni.pa165.dto;

import java.util.Objects;

public class StatisticDTO {
    private String userName;
    private Double fromToTotalSpent;
    private Double fromToAverageSpent;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getFromToTotalSpent() {
        return fromToTotalSpent;
    }

    public void setFromToTotalSpent(Double fromToTotalSpent) {
        this.fromToTotalSpent = fromToTotalSpent;
    }

    public Double getFromToAverageSpent() {
        return fromToAverageSpent;
    }

    public void setFromToAverageSpent(Double fromToAverageSpent) {
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
