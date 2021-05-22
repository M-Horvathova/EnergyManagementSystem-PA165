import StatisticDTO from "./StatisticDTO";

export default interface StatisticsDTO {
    from : Date;
    to: Date;
    totalSpent: number;
    averageSpent: number;
    statistics: Array<StatisticDTO>;
}