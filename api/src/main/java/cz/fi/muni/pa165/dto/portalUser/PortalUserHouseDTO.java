package cz.fi.muni.pa165.dto.portalUser;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Patrik Valo
 */
public class PortalUserHouseDTO {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PortalUserHouseDTO)) return false;
        PortalUserHouseDTO that = (PortalUserHouseDTO) o;
        return isAdmin() == that.isAdmin() &&
                isActive() == that.isActive() &&
                getPasswordHash().equals(that.getPasswordHash()) &&
                getEmail().equals(that.getEmail()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getCreatedTimestamp(), that.getCreatedTimestamp()) &&
                Objects.equals(getLastLoginTimestamp(), that.getLastLoginTimestamp());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (isAdmin ? 1 : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (createdTimestamp != null ? createdTimestamp.hashCode() : 0);
        result = 31 * result + (lastLoginTimestamp != null ? lastLoginTimestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PortalUserHouseDTO{" +
                "id=" + id +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", isAdmin=" + isAdmin +
                ", isActive=" + isActive +
                ", createdTimestamp=" + createdTimestamp +
                ", lastLoginTimestamp=" + lastLoginTimestamp +
                '}';
    }

}

