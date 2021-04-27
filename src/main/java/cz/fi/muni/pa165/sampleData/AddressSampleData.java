package cz.fi.muni.pa165.sampleData;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

@Service
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class AddressSampleData {
    @Autowired
    private AddressDao addressDao;

    private Address address10;
    private Address address20;

    public Address getAddress10() {
        if (address10 == null) {
            return generateAddress10();
        }
        return address10;
    }

    public void setAddress10(Address address10) {
        this.address10 = address10;
    }

    public Address getAddress20() {
        if (address20 == null) {
            return generateAddress20();
        }
        return address20;
    }

    public void setAddress20(Address address20) {
        this.address20 = address20;
    }

    public Address generateAddress10() {
        Address address = new Address();
        address.setStreet("Botanická");
        address.setCode("15");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("123456");
        this.address10 = address;
        addressDao.create(address);
        return address;
    }

    public Address generateAddress20() {
        Address address = new Address();
        address.setStreet("Hrázní");
        address.setCode("35");
        address.setCity("Brno");
        address.setCountry("Czech Republic");
        address.setPostCode("654321");
        this.address20 = address;
        addressDao.create(address);
        return address;
    }

    public void generateAddresses() {
        generateAddress10();
        generateAddress20();
    }

}
