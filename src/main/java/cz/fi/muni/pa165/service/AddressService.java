package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Address;

import java.util.List;

/**
 * @author Patrik Valo
 */
public interface AddressService {

    /**
     * Creates thee address or if there exists a address with same parameters
     * it returns the existing address
     * @param address Address, which should be create
     * @return created or existing address
     */
    Address createAddress(Address address);
}
