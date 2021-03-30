package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Valo
 */
@Entity
@IdClass(AddressId.class)
public class Address {

    @Id
    private String streetName;

    @Id
    private String city;

    @Id
    private String country;

    @OneToMany(mappedBy = "address")
    private Set<House> houses = new HashSet<>();

    public Address() {}

    public Address(String streetName, String city, String country) {
        this.streetName = streetName;
        this.city = city;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<House> getHouses() {
        return Collections.unmodifiableSet(houses);
    }

    public void addHouse(House house) {
        houses.add(house);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (getStreetName() != null ? !getStreetName().equals(address.getStreetName()) : address.getStreetName() != null)
            return false;
        if (getCity() != null ? !getCity().equals(address.getCity()) : address.getCity() != null) return false;
        if (getCountry() != null ? !getCountry().equals(address.getCountry()) : address.getCountry() != null)
            return false;
        return getHouses() != null ? getHouses().equals(address.getHouses()) : address.getHouses() == null;
    }

    @Override
    public int hashCode() {
        int result = getStreetName() != null ? getStreetName().hashCode() : 0;
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getHouses() != null ? getHouses().hashCode() : 0);
        return result;
    }
}