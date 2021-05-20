export default interface SmartMeterHouseDTO {
    id: number;
    running: boolean;
    powerConsumptionSinceLastLog: number;
    cumulativePowerConsumption: number;
    lastLogTakenAt: string | null;
    smartMeterDescription: string;
}
