export default interface SmartMeterStatisticsDTO {
    smartMeterDescription: string;
    running: boolean;
    cumulativePowerConsumption: number;
    averageSpentPerNight: number;
    averageSpentPerDay: number;
}