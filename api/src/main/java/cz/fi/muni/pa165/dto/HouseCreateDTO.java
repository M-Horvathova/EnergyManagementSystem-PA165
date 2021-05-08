package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Patrik Valo
 */
public class HouseCreateDTO {

    private String name;
    private Boolean running;
    private String street;
    private String code;
    private String city;
    private String postCode;
    private String country;
    private Long portalUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPortalUserId() {
        return portalUserId;
    }

    public void setPortalUserId(Long portalUserId) {
        this.portalUserId = portalUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseCreateDTO that = (HouseCreateDTO) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(running, that.running)) return false;
        if (!Objects.equals(street, that.street)) return false;
        if (!Objects.equals(code, that.code)) return false;
        if (!Objects.equals(city, that.city)) return false;
        if (!Objects.equals(postCode, that.postCode)) return false;
        if (!Objects.equals(country, that.country)) return false;
        return Objects.equals(portalUserId, that.portalUserId);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (running != null ? running.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (portalUserId != null ? portalUserId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HouseCreateDTO{" +
                "name='" + name + '\'' +
                ", isRunning=" + running +
                ", street='" + street + '\'' +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                ", portalUserId=" + portalUserId +
                '}';
    }
}
