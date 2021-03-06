package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/**
 * @author Matej Rišňovský
 */

@Entity
@Table(name = "smart_meter")
public class SmartMeter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "smartMeter", fetch = FetchType.LAZY)
    private Set<MeterLog> meterLogs = new HashSet<>();

    @Column(nullable = false)
    private boolean running;

    @Column(nullable = false)
    private double powerConsumptionSinceLastLog;

    @Column(nullable = false)
    private double cumulativePowerConsumption;

    @Column
    private LocalDateTime lastLogTakenAt;

    @Column
    private String smartMeterDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    public SmartMeter() {
    }

    public SmartMeter(boolean isRunning) {
        this.running = isRunning;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public double getCumulativePowerConsumption() {
        return cumulativePowerConsumption;
    }

    public void setCumulativePowerConsumption(double cumulativePowerConsumption) {
        this.cumulativePowerConsumption = cumulativePowerConsumption;
    }

    public double getPowerConsumptionSinceLastLog() {
        return powerConsumptionSinceLastLog;
    }

    public void setPowerConsumptionSinceLastLog(double powerConsumptionSinceLastLog) {
        this.powerConsumptionSinceLastLog = powerConsumptionSinceLastLog;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public LocalDateTime getLastLogTakenAt() {
        return  lastLogTakenAt;
    }

    public void setLastLogTakenAt(LocalDateTime lastLogTakenAt) {
        if (lastLogTakenAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Future dateTime is not allowed");
        }
        this.lastLogTakenAt = lastLogTakenAt;
    }

    public String getSmartMeterDescription() {
        return smartMeterDescription;
    }

    public void setSmartMeterDescription(String description) {
        this.smartMeterDescription = description;
    }

    public void addMeterLog(MeterLog ml) {
        if (meterLogs == null) {
            meterLogs = new HashSet<>();
        }
        meterLogs.add(ml);
    }

    public void removeMeterLog(MeterLog meterLog) {
        if (meterLogs != null) {
            meterLogs.remove(meterLog);
        }
    }

    public Set<MeterLog> getMeterLogs() {
        return meterLogs;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartMeter )) return false;

        SmartMeter sm = (SmartMeter) o;
        return isRunning() == sm.isRunning() &&
                Double.compare(sm.getPowerConsumptionSinceLastLog(), getPowerConsumptionSinceLastLog()) == 0
                && Double.compare(sm.getCumulativePowerConsumption(), getCumulativePowerConsumption()) == 0
                && ((getHouse() == null && sm.getHouse() == null) ||
                (getHouse() != null && getHouse().equals(sm.getHouse())))
                && ((getSmartMeterDescription() == null && sm.getSmartMeterDescription() == null) ||
                (getSmartMeterDescription() != null &&
                        getSmartMeterDescription().equals(sm.getSmartMeterDescription())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRunning(), getPowerConsumptionSinceLastLog(), getCumulativePowerConsumption(), getHouse(), getSmartMeterDescription());
    }

    @Override
    public String toString() {
        return "SmartMeter{" +
                "isRunning=" + running +
                ", cumulativePowerConsumption=" + cumulativePowerConsumption +
                ", smartMeterDescription='" + smartMeterDescription + '\'' +
                ", houseId=" + house.getId() +
                '}';
    }
}
