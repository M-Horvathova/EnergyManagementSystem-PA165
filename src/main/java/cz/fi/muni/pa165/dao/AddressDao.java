package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;

import java.util.List;

/**
 * @author Patrik Valo
 */
public interface AddressDao {

    /**
     * Saves the address into db
     * @param address the address, which should be saved
     */
    void create(Address address);

    /**
     * Updates the address in db
     * @param address the address, which should be updated
     * @return updated address
     */
    Address update(Address address);

    /**
     * Finds the address by the given id
     * @param id the id of the address
     * @return address with given id
     */
    Address findById(Long id);

    /**
     * Finds all addresses in db
     * @return all addresses in the db
     */
    List<Address> findAll();

    /**
     * Deletes the address
     * @param address the address, which should be deleted
     */
    void delete(Address address);
}
