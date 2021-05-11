import AddressDTO from "./AddressDTO";
import SmartMeterHouseDTO from "./SmartMeterHouseDTO";

export default interface HouseDTO {
    id: number;
    name: string;
    address: AddressDTO;
    smartMeters: Array<SmartMeterHouseDTO>;
    running: boolean;
}
