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

@RestController
@RequestMapping("/houses")
public class HousesController {

    private final HouseFacade houseFacade;

    @Autowired
    public HousesController(HouseFacade houseFacade) {
        this.houseFacade = houseFacade;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HouseDTO getHouse(@PathVariable("id") long id) throws Exception {
        HouseDTO houseDTO = houseFacade.getHouseWithId(id);

        if (houseDTO != null) {
            return houseDTO;
        } else {
            throw new ResourceNotFoundException();
        }
    }

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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteHouse(@PathVariable("id") long id) throws Exception {
        try {
            houseFacade.deleteHouse(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/findByUser/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<HouseDTO> findByUser(@PathVariable("id") long userId) throws Exception {
        try {
            return houseFacade.getHousesByUser(userId);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
