package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * Class representing user entity in our portal
 *
 * @author Martin Podhora
 */
@Entity
public class PortalUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String passwordHash;

    @Column(nullable=false,unique=true)
    @Pattern(regexp=".+@.+\\....?")
    private String email;

    @Column(nullable=false)
    private String firstName;

    @Column(nullable=false)
    private String lastName;

    @Pattern(regexp="\\+?\\d+")
    @Column(nullable=false)
    private String phone;

    @Column(nullable=false)
    private UserRole userRole;

    @Column(nullable=false)
    private LocalDateTime createdTimestamp;

    public long getId() {
        return id;
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

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public int hashCode() {
        final int prime = 23;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof PortalUser)) {
            return false;
        }

        PortalUser other = (PortalUser) obj;
        if (email == null) {
            if (other.getEmail() != null) {
                return false;
            }
        }
        else if (!email.equals(other.getEmail())) {
            return false;
        }

        return true;
    }
}