package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.HouseCreateDTO;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.restapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public final HouseDTO createProduct(@RequestBody HouseCreateDTO houseCreateDTO) throws Exception {
        Long id = houseFacade.createHouse(houseCreateDTO);
        return houseFacade.getHouseWithId(id);
    }
}
