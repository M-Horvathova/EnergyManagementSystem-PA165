package cz.fi.muni.pa165.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Martin Podhora
 */
public class StatisticsDTO {
    private String from;
    private String to;
    private double totalSpent;
    private double averageSpent;
    private List<StatisticDTO> statistics = new ArrayList<StatisticDTO>();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<StatisticDTO> getStatistics() {
        return statistics;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public double getAverageSpent() {
        return averageSpent;
    }

    public void setAverageSpent(double averageSpent) {
        this.averageSpent = averageSpent;
    }

    public void setStatistics(List<StatisticDTO> statistics) {
        this.statistics = statistics;
    }

    public void addStatistic(StatisticDTO statistic) {
        statistics.add(statistic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof StatisticsDTO)) return false;
        StatisticsDTO that = (StatisticsDTO) o;
        return Objects.equals(getTotalSpent(), that.getTotalSpent()) && Objects.equals(getAverageSpent(), that.getAverageSpent()) && Objects.equals(getFrom(), that.getFrom()) && Objects.equals(getTo(), that.getTo()) && Objects.equals(getStatistics(), that.getStatistics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAverageSpent(), getTotalSpent(), getFrom(), getTo(), getStatistics());
    }
}
