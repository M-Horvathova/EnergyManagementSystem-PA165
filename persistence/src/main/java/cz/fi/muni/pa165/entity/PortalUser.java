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
@Table(name = "portal_user")
public class PortalUser implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable=false, name = "password_hash")
    private String passwordHash;

    @Column(nullable=false,unique=true, name = "email")
    private String email;

    @Column(nullable=false, name = "first_name")
    private String firstName;

    @Column(nullable=false, name = "last_name")
    private String lastName;

    @Column(nullable=false, name = "phone")
    private String phone;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserRole userRole;

    @Column(nullable=false, name = "is_active")
    private boolean isActive;

    @Future
    @Column(nullable=false, name = "created_timestamp")
    private LocalDateTime createdTimestamp;

    @Future
    @Column(nullable=true, name = "last_login_timestamp")
    private LocalDateTime lastLoginTimestamp;

    @OneToMany(mappedBy = "portalUser", fetch = FetchType.LAZY)
    private Set<House> houses = new HashSet<House>();;


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
        return getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}