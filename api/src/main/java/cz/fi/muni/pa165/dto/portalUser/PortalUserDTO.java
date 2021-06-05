package cz.fi.muni.pa165.dto.portalUser;

import com.google.gson.Gson;
import cz.fi.muni.pa165.dto.house.HouseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Martin Podhora
 */
public class PortalUserDTO {
    private Long id;

    private String passwordHash;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private boolean isAdmin;

    private boolean isActive;

    private LocalDateTime createdTimestamp;

    private LocalDateTime lastLoginTimestamp;

    private List<HouseDTO> houses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(LocalDateTime lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    public List<HouseDTO> getHouses() {
        return houses;
    }

    public void setHouses(List<HouseDTO> houses) {
        this.houses = houses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortalUserDTO that = (PortalUserDTO) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
