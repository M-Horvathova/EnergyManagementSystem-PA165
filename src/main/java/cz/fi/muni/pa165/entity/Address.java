package cz.fi.muni.pa165.entity;

import javax.persistence.*;

/**
 * @author Patrik Valo
 */
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!getStreetName().equals(address.getStreetName())) return false;
        if (!getCity().equals(address.getCity())) return false;
        return getCountry().equals(address.getCountry());
    }

    @Override
    public int hashCode() {
        int result = getStreetName().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getCountry().hashCode();
        return result;
    }
}
