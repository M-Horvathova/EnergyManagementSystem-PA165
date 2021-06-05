package cz.fi.muni.pa165.dto.house;

import java.util.Objects;

/**
 * @author Patrik Valo
 */
public class HouseEditDTO {

    private String name;
    private String street;
    private String code;
    private String city;
    private String postCode;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof HouseEditDTO)) return false;
        HouseEditDTO that = (HouseEditDTO) o;
        return getName().equals(that.getName()) &&
                Objects.equals(getStreet(), that.getStreet()) &&
                Objects.equals(getCode(), that.getCode()) &&
                getCity().equals(that.getCity()) &&
                getPostCode().equals(that.getPostCode()) &&
                getCountry().equals(that.getCountry());
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HouseCreateDTO{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
