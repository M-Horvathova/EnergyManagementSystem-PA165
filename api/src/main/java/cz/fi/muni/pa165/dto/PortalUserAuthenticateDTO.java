package cz.fi.muni.pa165.dto;

import com.google.gson.Gson;

import java.util.Objects;

public class PortalUserAuthenticateDTO {
    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof PortalUserAuthenticateDTO)) return false;
        PortalUserAuthenticateDTO that = (PortalUserAuthenticateDTO) o;
        return Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPassword());
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
