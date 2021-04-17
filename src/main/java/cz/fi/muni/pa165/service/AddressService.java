package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Address;

import java.util.List;

/**
 * @author Patrik Valo
 */
public interface AddressService {

    void create(Address address);

    Address findById(Long id);

    List<Address> findAll();

    Address find(String street, String code, String city, String postCode, String country);

    void delete(Address address);
}
