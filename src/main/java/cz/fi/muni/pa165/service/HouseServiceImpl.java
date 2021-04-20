package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Patrik Valo
 */
@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;
    private final AddressDao addressDao;

    @Autowired
    public HouseServiceImpl(HouseDao houseDao, AddressDao addressDao) {
        this.houseDao = houseDao;
        this.addressDao = addressDao;
    }

    @Override
    public House createHouse(House house) {
        houseDao.create(house);
        return house;
    }

    @Override
    public House changeAddress(House house, Address newAddress) {
        List<House> houses = houseDao.findByAddress(house.getAddress());

        if (houses.size() <= 1) {
            addressDao.delete(house.getAddress());
        }

        house.setAddress(newAddress);
        return houseDao.update(house);
    }

    @Override
    public House changeName(House house, String newName) {
        house.setName(newName);
        return houseDao.update(house);
    }

    @Override
    public House changeRunning(House house, Boolean isRunning) {
        house.setRunning(isRunning);
        return houseDao.update(house);
    }

    @Override
    public House findById(Long id) {
        return houseDao.findById(id);
    }

    @Override
    public List<House> findByName(String name) {
        return houseDao.findByName(name);
    }

    @Override
    public List<House> findByAddress(Address address) {
        return houseDao.findByAddress(address);
    }

    @Override
    public List<House> findByUser(PortalUser user) {
        return houseDao.findByUser(user);
    }

    @Override
    public List<House> findAll() {
        return houseDao.findAll();
    }

    @Override
    public void deleteHouse(House house) {
        List<House> houses = houseDao.findByAddress(house.getAddress());

        if (houses.size() <= 1) {
            addressDao.delete(house.getAddress());
        }

        houseDao.delete(house);
    }
}
