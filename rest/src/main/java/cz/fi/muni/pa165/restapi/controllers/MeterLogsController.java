package cz.fi.muni.pa165.restapi.controllers;


import cz.fi.muni.pa165.dto.MeterLogCreateDTO;
import cz.fi.muni.pa165.dto.MeterLogDTO;
import cz.fi.muni.pa165.facade.MeterLogFacade;
import cz.fi.muni.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.restapi.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Michaela Horváthová
 */

@RestController
@RequestMapping("/meterlogs")
public class MeterLogsController {

    final static Logger logger = LoggerFactory.getLogger(MeterLogsController.class);

    private final MeterLogFacade meterLogFacade;

    @Autowired
    public MeterLogsController(MeterLogFacade meterLogFacade) {
        this.meterLogFacade = meterLogFacade;
    }

    /**
     * Get Meter Log by ID curl -i -X GET
     * http://localhost:8080/pa165/rest/meterlogs/1
     * curl -i -X GET http://localhost:8080/pa165/rest/meterlogs/1
     * @param id ID of the Meter Log
     * @return MeterLogDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MeterLogDTO getMeterLog(@PathVariable("id") long id) throws Exception {
        logger.info("get log");
        MeterLogDTO meterLogDTO = meterLogFacade.getMeterLogWithId(id);
        if (meterLogDTO != null) {
            return meterLogDTO;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Create a new Meter Log by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data
     * http://localhost:8080/pa165/rest/meterlogs/create
     * curl -X POST -i -H "Content-Type: application/json" --data '{"logDate":"15-01-2020","logTime":"18:35:00","measure":15,"smartMeterId":1}' http://localhost:8080/pa165/rest/meterlogs/create
     * @param MeterLogCreateDTO MeterLogDTO with required fields for creation
     * @return MeterLogDTO
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MeterLogDTO createMeterLog(@RequestBody MeterLogCreateDTO MeterLogCreateDTO) throws Exception {
        try {
            Long id = meterLogFacade.createMeterLog(MeterLogCreateDTO);
            return meterLogFacade.getMeterLogWithId(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Delete single meter log by id curl -i -X DELETE
     * http://localhost:8080/pa165/rest/meterlogs/1
     *
     * @param id ID of the meter log
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteMeterLog(@PathVariable("id") long id) throws Exception {
        try {
            meterLogFacade.deleteMeterLog(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
