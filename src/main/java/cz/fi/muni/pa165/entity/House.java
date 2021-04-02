package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Patrik Valo
 */
@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isRunning;

    @ManyToOne
    private Address address;

    @OneToMany
    private Set<SmartMeter> smartMeters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Boolean getRunning() {
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<SmartMeter> getSmartMeters() {
        return smartMeters;
    }

    public void addSmartMeter(SmartMeter smartMeter) {
        smartMeters.add(smartMeter);
    }

    public void removeSmartMeter(SmartMeter smartMeter) {
        smartMeters.remove(smartMeter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;

        House house = (House) o;

        if (!getName().equals(house.getName())) return false;
        if (!isRunning.equals(house.isRunning)) return false;
        return getAddress().equals(house.getAddress());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + isRunning.hashCode();
        result = 31 * result + getAddress().hashCode();
        return result;
    }
}
