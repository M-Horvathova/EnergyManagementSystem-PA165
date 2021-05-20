package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterCreateDTO {
    private boolean running;
    private String smartMeterDescription;
    private long houseId;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getSmartMeterDescription() {
        return smartMeterDescription;
    }

    public void setSmartMeterDescription(String description) {
        this.smartMeterDescription = description;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeterCreateDTO)) return false;

        SmartMeterCreateDTO sm = (SmartMeterCreateDTO) o;
        return isRunning() == sm.isRunning()
                && getHouseId() == sm.getHouseId()
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) || (getSmartMeterDescription() != null && getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getHouseId(), getSmartMeterDescription());
    }

    @Override
    public String toString() {
        return "SmartMeterCreateDTO{" +
                "isRunning=" + running +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                ", houseId=" + houseId +
                '}';
    }
}
