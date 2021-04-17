package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Autowired
    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public void create(Address address) {
        addressDao.create(address);
    }

    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public Address find(String street, String code, String city, String postCode, String country) {
        return addressDao.find(street, code, city, postCode, country);
    }

    @Override
    public void delete(Address address) {
        addressDao.delete(address);
    }
}
