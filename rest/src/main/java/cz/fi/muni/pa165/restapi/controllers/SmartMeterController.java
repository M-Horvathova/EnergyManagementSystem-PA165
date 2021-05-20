package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.facade.SmartMeterFacade;
import cz.fi.muni.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.restapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Matej Rišňovský
 */

@RestController
@RequestMapping("/smartmeters")
public class SmartMeterController {
    private final SmartMeterFacade smartMeterFacade;
    private final HouseFacade houseFacade;

    @Autowired
    public SmartMeterController(SmartMeterFacade smartMeterFacade, HouseFacade houseFacade) {
        this.smartMeterFacade = smartMeterFacade;
        this.houseFacade = houseFacade;
    }

    /**
     * Get smart meter by ID curl -i -X GET
     * http://localhost:8080/pa165/smartmeters/1
     *
     * @param id ID of the smart meter
     * @return SmartMeterDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SmartMeterDTO getSmartMeter(@PathVariable("id") long id) throws Exception {
        SmartMeterDTO smartMeterDTO = smartMeterFacade.getSmartMeter(id);

        if (smartMeterDTO != null) {
            return smartMeterDTO;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Create a smart meter by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data
     * '{"houseId":"1","smartMeterDescription":"Smart meter - okruh garáž","isRunning": true}'
     * http://localhost:8080/pa165/rest/smartmeters/create
     *
     * @param smartMeterCreateDTO SmartMeterCreateDTO with required fields for creation
     * @return id
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final long createSmartMeter(@RequestBody SmartMeterCreateDTO smartMeterCreateDTO) throws Exception {
        try {
            Long id = smartMeterFacade.createSmartMeter(smartMeterCreateDTO);
            return id;
        } catch (Exception e) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Update the fields of the smart meter by PUT method curl -X PUT -i -H "Content-Type: application/json" --data
     * '{"smartMeterDescription":"Smart meter - okruh garáž","isRunning": true}'
     * http://localhost:8080/pa165/rest/smartmeters/1
     *
     * @param id ID of the smartMeter, which should be edited
     * @param smartMeterEditDTO HouseEditDTO with required fields for edit
     * @return updated SmartMeterDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final SmartMeterDTO editSmartMeter(@PathVariable("id") long id, @RequestBody SmartMeterEditDTO smartMeterEditDTO) throws Exception {
        try {
            smartMeterEditDTO.setId(id);
            smartMeterFacade.updateSmartMeter(smartMeterEditDTO);
            return smartMeterFacade.getSmartMeter(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Delete smart meter by id curl -i -X DELETE
     * http://localhost:8080/pa165/rest/smartmeters/1
     *
     * @param id ID of the smart meter
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteSmartMeter(@PathVariable("id") long id) throws Exception {
        try {
            SmartMeterDTO sm = smartMeterFacade.getSmartMeter(id);
            smartMeterFacade.deleteSmartMeter(sm);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get all smart meters for house with given ID curl -i -X GET
     * http://localhost:8080/pa165/rest/smartmeters/findByHouse/1
     *
     * @param houseId ID of the house
     * @return Set<SmartMeterHouseDTO>
     * @throws Exception
     */
    @RequestMapping(value = "/findByHouse/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Set<SmartMeterHouseDTO> findByHouse(@PathVariable("id") long houseId) throws Exception {
        try {
            HouseDTO houseDTO = houseFacade.getHouseWithId(houseId);
            return houseDTO.getSmartMeters();
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get all power spent across all smart meters curl -i -X GET
     * http://localhost:8080/pa165/rest/smartmeters/allPowerSpent
     *
     * @return all power spent across all smart meters
     */
    @RequestMapping(value = "/allPowerSpent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final double getAllPowerSpent() {
        try {
            return smartMeterFacade.getAllPowerSpent();
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get all power spent across all smart meters curl -i -X POST
     * '{"smartMeterDescription":"Smart meter - okruh garáž","isRunning": true}'
     * http://localhost:8080/pa165/rest/smartmeters/powerSpent/1
     *
     * @param id ID of the smart meter
     * @return spent power for smart meter for date
     */
    @RequestMapping(value = "/powerSpent/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final double getPowerSpentForDate(@PathVariable("id") long id, @RequestBody Date date) {
        try {
            LocalDate localDate =  LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
            var smartMeter = smartMeterFacade.getSmartMeter(id);
            return smartMeterFacade.getPowerSpentForDateForSmartMeter(localDate, smartMeter);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
