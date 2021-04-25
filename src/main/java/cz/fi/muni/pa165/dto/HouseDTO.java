package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.dto.PortalUser.PortalUserDTO;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Patrik Valo
 */
public class HouseDTO {

    private Long id;
    private String name;
    private Boolean isRunning;
    private AddressDTO address;
    private Set<SmartMeterDTO> smartMeters = new HashSet<>();
    private PortalUserDTO portalUser;

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
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Set<SmartMeterDTO> getSmartMeters() {
        return smartMeters;
    }

    public void setSmartMeters(Set<SmartMeterDTO> smartMeters) {
        this.smartMeters = smartMeters;
    }

    public PortalUserDTO getPortalUser() {
        return portalUser;
    }

    public void setPortalUser(PortalUserDTO portalUser) {
        this.portalUser = portalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseDTO houseDTO = (HouseDTO) o;

        if (!Objects.equals(id, houseDTO.id)) return false;
        if (!Objects.equals(name, houseDTO.name)) return false;
        if (!Objects.equals(isRunning, houseDTO.isRunning)) return false;
        return Objects.equals(address, houseDTO.address);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isRunning != null ? isRunning.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isRunning=" + isRunning +
                ", address=" + address +
                ", smartMeters=" + smartMeters +
                ", portalUser=" + portalUser +
                '}';
    }
}
