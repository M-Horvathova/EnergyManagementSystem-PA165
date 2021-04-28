package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Valo
 */
@Entity
@Table(name = "house")
public class House implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isRunning;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private Set<SmartMeter> smartMeters = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private PortalUser portalUser;

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
        return Collections.unmodifiableSet(smartMeters);
    }

    public void setSmartMeters(Set<SmartMeter> smartMeters) {
        this.smartMeters = smartMeters;
    }

    public void addSmartMeter(SmartMeter smartMeter) {
        smartMeters.add(smartMeter);
    }

    public void removeSmartMeter(SmartMeter smartMeter) {
        smartMeters.remove(smartMeter);
    }

    public PortalUser getPortalUser() {
        return portalUser;
    }

    public void setPortalUser(PortalUser portalUser) {
        this.portalUser = portalUser;
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

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isRunning=" + isRunning +
                '}';
    }
}
