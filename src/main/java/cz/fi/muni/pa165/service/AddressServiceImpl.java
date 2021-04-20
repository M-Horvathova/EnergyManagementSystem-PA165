package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Patrik Valo
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Autowired
    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public Address createAddress(Address address) {
        Address existingAddress = addressDao.find(address.getStreet(), address.getCode(),
                address.getCity(), address.getPostCode(), address.getCountry());

        if (existingAddress != null) {
            return existingAddress;
        }

        addressDao.create(address);
        return address;
    }

    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }
}
