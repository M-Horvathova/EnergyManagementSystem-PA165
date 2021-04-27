package cz.fi.muni.pa165.dto.PortalUser;

import com.google.gson.Gson;

import java.util.Objects;

public class PortalUserChangePasswordDTO {
    private int id;

    private String oldPassword;

    private String newPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof PortalUserChangePasswordDTO)) return false;
        PortalUserChangePasswordDTO that = (PortalUserChangePasswordDTO) o;
        return Objects.equals(getOldPassword(), that.getOldPassword()) && Objects.equals(getNewPassword(), that.getNewPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOldPassword(), getNewPassword());
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
