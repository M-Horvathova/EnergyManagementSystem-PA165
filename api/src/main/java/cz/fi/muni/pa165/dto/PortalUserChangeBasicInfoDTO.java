package cz.fi.muni.pa165.dto;

import com.google.gson.Gson;

import java.util.Objects;

public class PortalUserChangeBasicInfoDTO {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof  PortalUserChangeBasicInfoDTO)) return false;
        PortalUserChangeBasicInfoDTO that = (PortalUserChangeBasicInfoDTO) o;
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
