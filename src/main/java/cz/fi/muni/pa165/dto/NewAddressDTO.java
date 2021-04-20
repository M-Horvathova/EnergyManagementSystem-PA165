package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 * @author Patrik Valo
 */
public class NewAddressDTO {

    private Long houseId;
    private String street;
    private String code;
    private String city;
    private String postCode;
    private String country;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewAddressDTO that = (NewAddressDTO) o;

        if (!Objects.equals(houseId, that.houseId)) return false;
        if (!Objects.equals(street, that.street)) return false;
        if (!Objects.equals(code, that.code)) return false;
        if (!Objects.equals(city, that.city)) return false;
        if (!Objects.equals(postCode, that.postCode)) return false;
        return Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        int result = houseId != null ? houseId.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewAddressDTO{" +
                "houseId=" + houseId +
                ", street='" + street + '\'' +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
