package cz.fi.muni.pa165.entity;

import com.google.gson.Gson;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing user entity in our portal
 *
 * @author Martin Podhora
 */
@Entity
public class PortalUser implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String passwordHash;

    @Column(nullable=false,unique=true)
    private String email;

    @Column(nullable=false)
    private String firstName;

    @Column(nullable=false)
    private String lastName;

    @Column(nullable=false)
    private String phone;

    @ManyToOne(optional = false)
    private UserRole userRole;

    @Column(nullable=false)
    private boolean isActive;

    @Future
    @Column(nullable=false)
    private LocalDateTime createdTimestamp;

    @Future
    @Column(nullable=true)
    private LocalDateTime lastLoginTimestamp;

    @OneToMany(mappedBy = "portalUser")
    private Set<House> houses = new HashSet<House>();;


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


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isAdmin() {
        return userRole.getRoleName().equals(UserRole.ADMINISTRATOR_ROLE_NAME);
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


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        houses.forEach(house -> house.setPortalUser(this));
        this.houses = houses;
    }

    public void addHouse(House house) {
        houses.add(house);
        house.setPortalUser(this);
    }

    public void removeHouse(House house) {
        houses.remove(house);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof PortalUser)) return false;
        PortalUser that = (PortalUser) o;
        return getId() == that.getId()
                && isActive == that.isActive
                && getPasswordHash().equals(that.getPasswordHash())
                && getEmail().equals(that.getEmail())
                && getFirstName().equals(that.getFirstName())
                && getLastName().equals(that.getLastName())
                && getPhone().equals(that.getPhone())
                && getUserRole().equals(that.getUserRole())
                && getCreatedTimestamp().equals(that.getCreatedTimestamp())
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
                getUserRole(),
                isActive,
                getCreatedTimestamp(),
                getLastLoginTimestamp(),
                getHouses());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}