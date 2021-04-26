package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;

public class HouseSampleData {
    private House house10;
    private House house20;

    public House generateHouse10() {
        House house = new House();
        house.setId(10L);
        house.setName("Test house");
        house.setRunning(true);
        this.house10 = house;
        return house;
    }

    public House generateHouse20() {
        House house = new House();
        house.setId(20L);
        house.setName("Test house2");
        house.setRunning(false);
        this.house20 = house;
        return house;
    }

    public House getHouse10() {
        if (house10 == null) {
            return generateHouse10();
        }
        return house10;
    }

    public void setHouse10(House house10) {
        this.house10 = house10;
    }

    public House getHouse20() {
        if (house20 == null) {
            return generateHouse20();
        }
        return house20;
    }

    public void setHouse20(House house20) {
        this.house20 = house20;
    }
}
