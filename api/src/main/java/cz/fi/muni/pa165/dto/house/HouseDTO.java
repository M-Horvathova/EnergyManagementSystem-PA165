package cz.fi.muni.pa165.dto.house;

import cz.fi.muni.pa165.dto.AddressDTO;
import cz.fi.muni.pa165.dto.smartMeter.SmartMeterHouseDTO;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Patrik Valo
 */
public class HouseDTO {

    private Long id;
    private String name;
    private Boolean running;
    private AddressDTO address;
    private Set<SmartMeterHouseDTO> smartMeters = new HashSet<>();
    private Long portalUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Set<SmartMeterHouseDTO> getSmartMeters() {
        return smartMeters;
    }

    public void setSmartMeters(Set<SmartMeterHouseDTO> smartMeters) {
        this.smartMeters = smartMeters;
    }

    public Long getPortalUserId() {
        return portalUserId;
    }

    public void setPortalUserId(Long portalUserId) {
        this.portalUserId = portalUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseDTO)) return false;
        HouseDTO houseDTO = (HouseDTO) o;
        return getId().equals(houseDTO.getId()) &&
                getName().equals(houseDTO.getName()) &&
                getRunning().equals(houseDTO.getRunning()) &&
                getAddress().equals(houseDTO.getAddress()) &&
                Objects.equals(getSmartMeters(), houseDTO.getSmartMeters()) &&
                getPortalUserId().equals(houseDTO.getPortalUserId());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (running != null ? running.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isRunning=" + running +
                ", address=" + address +
                ", smartMeters=" + smartMeters +
                ", portalUserId=" + portalUserId +
                '}';
    }
}
