package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;

    @Autowired
    public HouseServiceImpl(HouseDao houseDao) {
        this.houseDao = houseDao;
    }

    @Override
    public void createHouse(House house) {
        houseDao.create(house);
    }

    @Override
    public House changeAddress(House house, Address newAddress) {
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
    public House findByName(String name) {
        return houseDao.findByName(name);
    }

    @Override
    public List<House> findByAddress(Address address) {
        return houseDao.findByAddress(address);
    }

    @Override
    public List<House> findAll() {
        return houseDao.findAll();
    }

    @Override
    public void deleteHouse(House house) {
        houseDao.delete(house);
    }
}
