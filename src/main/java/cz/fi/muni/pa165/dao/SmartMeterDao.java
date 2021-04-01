package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.SmartMeter;

import java.util.List;

public interface SmartMeterDao {

    /**
     * Inserts given smart meter to db
     * @param smartMeter to be inserted
     */
    void create(SmartMeter smartMeter);

    /**
     * Updates given smart meter in db
     * @param smartMeter to be updated
     * @return updated smart meter
     */
    SmartMeter update(SmartMeter smartMeter);

    /**
     * Finds smart meter by given id
     * @param id of the smart meter
     * @return smart meter with given id
     */
    SmartMeter findById(Long id);

    /**
     * Finds all smart meters in db
     * @return all houses in db
     */
    List<SmartMeter> findAll();

    /**
     * Finds all currently running/switched off smart meters
     * @return all running/switched off smart meters in db
     */

    List<SmartMeter> findByRunning(boolean running);

    /**
     * Deletes given smart meter
     * @param smartMeter to be deleted
     */
    void delete(SmartMeter smartMeter);
}
