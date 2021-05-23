package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Matej Rišňovský
 */
public class SmartMeterEditDTO {
    private String smartMeterDescription;
    private boolean running;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeterEditDTO)) return false;

        SmartMeterEditDTO sm = (SmartMeterEditDTO) o;
        return isRunning() == sm.isRunning()
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) || (getSmartMeterDescription() != null && getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getSmartMeterDescription());
    }

    @Override
    public String toString() {
        return "SmartMeterEditDTO{" +
                "isRunning=" + running +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                '}';
    }
}
