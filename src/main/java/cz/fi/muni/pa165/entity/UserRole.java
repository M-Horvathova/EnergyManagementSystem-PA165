package cz.fi.muni.pa165.entity;

import com.google.gson.Gson;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Martin Podhora
 */
@Entity
public class UserRole implements Serializable {
    public static final String USER_ROLE_NAME = "User";
    public static final String ADMINISTRATOR_ROLE_NAME = "Administrator";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String roleName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return getId() == userRole.getId()
                && getRoleName().equals(userRole.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleName());
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
