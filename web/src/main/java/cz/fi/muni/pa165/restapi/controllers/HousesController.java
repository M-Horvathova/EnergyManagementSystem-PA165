package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.HouseCreateDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.HouseEditDTO;
import cz.fi.muni.pa165.dto.RunningDTO;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.restapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Patrik Valo
 */
@RestController
@RequestMapping("/houses")
public class HousesController {

    private final HouseFacade houseFacade;

    @Autowired
    public HousesController(HouseFacade houseFacade) {
        this.houseFacade = houseFacade;
    }

    /**
     * Get House by ID curl -i -X GET
     * http://localhost:8080/pa165/rest/houses/1
     *
     * @param id ID of the house
     * @return HouseDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HouseDTO getHouse(@PathVariable("id") long id) throws Exception {
        HouseDTO houseDTO = houseFacade.getHouseWithId(id);

        if (houseDTO != null) {
            return houseDTO;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Create a new house by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data
     * '{"name":"Home","street":"Nova","code":null,"city":"Slovak","postCode":"90801",
     * "country":"Slovakia", "running": true, "portalUserId": 1}'
     * http://localhost:8080/pa165/rest/houses/create
     *
     * @param houseCreateDTO HouseCreateDTO with required fields for creation
     * @return HouseDTO
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO createHouse(@RequestBody HouseCreateDTO houseCreateDTO) throws Exception {
        try {
            Long id = houseFacade.createHouse(houseCreateDTO);
            return houseFacade.getHouseWithId(id);
        } catch (Exception e) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Update the fields of the house by PUT method curl -X PUT -i -H "Content-Type: application/json" --data
     * '{"name":"Home","street":"Nova","code":null,"city":"Slovak","postCode":"90801", "country":"Slovakia"}'
     * http://localhost:8080/pa165/rest/houses/1
     *
     * @param id ID of the house, which should be edited
     * @param houseEditDTO HouseEditDTO with required fields for edit
     * @return updated HouseDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO editHouse(@PathVariable("id") long id, @RequestBody HouseEditDTO houseEditDTO) throws Exception {
        try {
            houseFacade.editHouse(id, houseEditDTO);
            return houseFacade.getHouseWithId(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Change running status by PUT method curl -X PUT -i -H "Content-Type: application/json" --data
     * '{"running": false}'
     * http://localhost:8080/pa165/rest/houses/running/1
     *
     * @param id ID of the house
     * @param runningDTO RunningDTO with required field
     * @return HouseDTO
     * @throws Exception
     */
    @RequestMapping(value = "/running/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO changeRunning(@PathVariable("id") long id, @RequestBody RunningDTO runningDTO) throws Exception {
        try {
            houseFacade.changeRunning(id, runningDTO.getRunning());
            return houseFacade.getHouseWithId(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Delete one house by id curl -i -X DELETE
     * http://localhost:8080/pa165/rest/houses/1
     *
     * @param id ID of the house
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteHouse(@PathVariable("id") long id) throws Exception {
        try {
            houseFacade.deleteHouse(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get all Houses for user with given ID curl -i -X GET
     * http://localhost:8080/pa165/rest/houses/findByUser/1
     *
     * @param userId ID of the user
     * @return List<HouseDTO>
     * @throws Exception
     */
    @RequestMapping(value = "/findByUser/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<HouseDTO> findByUser(@PathVariable("id") long userId) throws Exception {
        try {
            return houseFacade.getHousesByUser(userId);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
