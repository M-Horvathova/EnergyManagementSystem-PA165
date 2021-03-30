package cz.fi.muni.pa165.entity;

import java.io.Serializable;

/**
 * @author Patrik Valo
 */
public class AddressId implements Serializable {

    private String streetName;
    private String city;
    private String country;

    public AddressId() {}

    public AddressId(String streetName, String city, String country) {
        this.streetName = streetName;
        this.city = city;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressId)) return false;

        AddressId addressId = (AddressId) o;

        if (streetName != null ? !streetName.equals(addressId.streetName) : addressId.streetName != null) return false;
        if (city != null ? !city.equals(addressId.city) : addressId.city != null) return false;
        return country != null ? country.equals(addressId.country) : addressId.country == null;
    }

    @Override
    public int hashCode() {
        int result = streetName != null ? streetName.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
