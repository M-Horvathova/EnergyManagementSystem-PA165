package cz.fi.muni.pa165.dto.PortalUser;

import com.google.gson.Gson;
import cz.fi.muni.pa165.entity.House;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Martin Podhora
 */
public class PortalUserDTO {
    private long id;

    private String passwordHash;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private boolean isAdmin;

    private boolean isActive;

    private LocalDateTime createdTimestamp;

    private LocalDateTime lastLoginTimestamp;

    private Set<House> houses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortalUserDTO that = (PortalUserDTO) o;
        return getId() == that.getId()
                && isAdmin() == that.isAdmin()
                && isActive() == that.isActive()
                && Objects.equals(getPasswordHash(), that.getPasswordHash())
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getFirstName(), that.getFirstName())
                && Objects.equals(getLastName(), that.getLastName())
                && Objects.equals(getPhone(), that.getPhone())
                && Objects.equals(getCreatedTimestamp(), that.getCreatedTimestamp())
                && Objects.equals(getLastLoginTimestamp(), that.getLastLoginTimestamp())
                && Objects.equals(getHouses(), that.getHouses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(),
                getPasswordHash(),
                getEmail(),
                getFirstName(),
                getLastName(),
                getPhone(),
                isAdmin(),
                isActive(),
                getCreatedTimestamp(),
                getLastLoginTimestamp(),
                getHouses());
    }

    public String toString(){
        return new Gson().toJson(this);
    }
}
