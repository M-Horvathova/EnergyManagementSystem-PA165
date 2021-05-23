import StatisticDTO from "./StatisticDTO";

export default class StatisticsDTO {
    from : Date|null = null;
    to: Date|null = null;
    totalSpent: number = 0;
    averageSpent: number = 0;
    statistics: Array<StatisticDTO> = [];
}