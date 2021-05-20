package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterEditDTO {
    private Long id;
    private boolean isRunning;
    private String smartMeterDescription;

    public Long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getSmartMeterDescription() {
        return smartMeterDescription;
    }

    public void setSmartMeterDescription(String description) {
        this.smartMeterDescription = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeterCreateDTO)) return false;

        SmartMeterCreateDTO sm = (SmartMeterCreateDTO) o;
        return isRunning() == sm.isRunning()
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) || (getSmartMeterDescription() != null && getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getSmartMeterDescription());
    }

    @Override
    public String toString() {
        return "SmartMeterCreateDTO{" +
                "isRunning=" + isRunning +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                '}';
    }
}
