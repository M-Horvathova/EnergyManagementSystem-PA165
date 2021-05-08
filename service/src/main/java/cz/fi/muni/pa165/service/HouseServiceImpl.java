package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.AddressDao;
import cz.fi.muni.pa165.dao.HouseDao;
import cz.fi.muni.pa165.dao.SmartMeterDao;
import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.SmartMeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Patrik Valo
 */
@Service
public class HouseServiceImpl implements HouseService {

    private HouseDao houseDao;
    private AddressDao addressDao;
    private SmartMeterDao smartMeterDao;

    @Autowired
    public HouseServiceImpl(HouseDao houseDao, AddressDao addressDao, SmartMeterDao smartMeterDao) {
        this.houseDao = houseDao;
        this.addressDao = addressDao;
        this.smartMeterDao = smartMeterDao;
    }

    @Override
    public House createHouse(House house) {
        houseDao.create(house);
        return house;
    }

    @Override
    public House changeAddress(House house, Address newAddress) {
        if (newAddress.getId().equals(house.getAddress().getId())) {
            return house;
        }

        List<House> houses = houseDao.findByAddress(house.getAddress());
        Address address = house.getAddress();

        house.setAddress(newAddress);

        if (houses.size() <= 1) {
            addressDao.delete(address);
        }

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

        List<SmartMeter> smartMeters = smartMeterDao.findByHouse(house);

        for (SmartMeter smartMeter : smartMeters) {
            smartMeter.setRunning(isRunning);
            smartMeterDao.update(smartMeter);
        }

        return houseDao.update(house);
    }

    @Override
    public void addSmartMeter(House house, SmartMeter smartMeter) {
        house.addSmartMeter(smartMeter);
    }

    @Override
    public void removeSmartMeter(House house, SmartMeter smartMeter) {
        house.removeSmartMeter(smartMeter);
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
        Address address = house.getAddress();
        houseDao.delete(house);

        if (houses.size() <= 1) {
            addressDao.delete(address);
        }
    }
}
