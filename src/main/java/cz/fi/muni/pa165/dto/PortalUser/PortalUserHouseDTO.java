package cz.fi.muni.pa165.dto.PortalUser;

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
        if (o == null || getClass() != o.getClass()) return false;

        PortalUserHouseDTO that = (PortalUserHouseDTO) o;

        if (isAdmin != that.isAdmin) return false;
        if (isActive != that.isActive) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(passwordHash, that.passwordHash)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(phone, that.phone)) return false;
        if (!Objects.equals(createdTimestamp, that.createdTimestamp))
            return false;
        return Objects.equals(lastLoginTimestamp, that.lastLoginTimestamp);
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
