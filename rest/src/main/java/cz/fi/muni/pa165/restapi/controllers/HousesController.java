package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.facade.HouseFacade;
import cz.fi.muni.pa165.service.HouseServiceImpl;
import cz.fi.muni.pa165.service.PortalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/houses")
public class HousesController {

    final static Logger logger = LoggerFactory.getLogger(HousesController.class);

    private final HouseFacade houseFacade;
    private final HouseServiceImpl houseService;

    @Autowired
    public HousesController(HouseFacade houseFacade, HouseServiceImpl houseService) {
        this.houseFacade = houseFacade;
        this.houseService = houseService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HouseDTO getHouse(@PathVariable("id") long id) throws Exception {
        var house = houseFacade.getHouseWithId(1L);

        logger.debug("Before create" + house);
        return house;
    }
}
