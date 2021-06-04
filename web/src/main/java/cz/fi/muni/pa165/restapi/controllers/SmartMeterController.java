package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.enums.DayTime;
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
     * '{"houseId":1,"smartMeterDescription":"Smart meter - okruh garáž","isRunning": true}'
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
            return smartMeterFacade.updateSmartMeter(smartMeterEditDTO);
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
    public final List<SmartMeterDTO> findByHouse(@PathVariable("id") long houseId) throws Exception {
        try {
            return smartMeterFacade.getSmartMeterByHouse(houseId);
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
     * '{"day":22, "month": 12, "year":2020}'
     * http://localhost:8080/pa165/rest/smartmeters/powerSpent/1
     *
     * @param id ID of the smart meter
     * @return spent power for smart meter for date
     */
    @RequestMapping(value = "/powerSpent/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final double getPowerSpentForDate(@PathVariable("id") long id, @RequestBody SmartMeterPowerSpentDTO smartMeterPowerSpentDTO) {
        try {
            //react numbers months from zero
            LocalDate localDate =  LocalDate.of(smartMeterPowerSpentDTO.getYear(), smartMeterPowerSpentDTO.getMonth() + 1, smartMeterPowerSpentDTO.getDay());
            var smartMeter = smartMeterFacade.getSmartMeter(id);
            return smartMeterFacade.getPowerSpentForDateForSmartMeter(localDate, smartMeter);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get all power spent across all smart meters curl -i -X POST
     * '{"dayFrom":22, "monthFrom": 12, "yearFrom":2020,"dayTo":20, "monthTo": 2, "yearTo":2021}'
     * http://localhost:8080/pa165/rest/smartmeters/powerSpentInterval/1
     *
     * @param id ID of the smart meter
     * @return spent power for smart meter for date
     */
    @RequestMapping(value = "/powerSpentInterval/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final SmartMeterStatsIntervalDTO getPowerSpentForDateInterval(@PathVariable("id") long id, @RequestBody SmartMeterPowerSpentInIntervalDTO smartMeterPowerSpentInIntervalDTO) {
        try {
            //react numbers months from zero
            LocalDate localDateFrom =  LocalDate.of(smartMeterPowerSpentInIntervalDTO.getYearFrom(), smartMeterPowerSpentInIntervalDTO.getMonthFrom() + 1, smartMeterPowerSpentInIntervalDTO.getDayFrom());
            LocalDate localDateTo =  LocalDate.of(smartMeterPowerSpentInIntervalDTO.getYearTo(), smartMeterPowerSpentInIntervalDTO.getMonthTo() + 1, smartMeterPowerSpentInIntervalDTO.getDayTo());
            var sm = smartMeterFacade.getSmartMeter(id);

            SmartMeterStatsIntervalDTO statisticDTO = new SmartMeterStatsIntervalDTO();

            double dayTotal = smartMeterFacade.getPowerSpentForDateFrameWithDayTime(localDateFrom, localDateTo, sm, DayTime.Day);
            double nightTotal = smartMeterFacade.getPowerSpentForDateFrameWithDayTime(localDateFrom, localDateTo, sm, DayTime.Night);
            double total =  smartMeterFacade.getPowerSpentForSmartMeterInDateRange(localDateFrom, localDateTo, sm);

            statisticDTO.setCumulativePowerConsumption(total);
            statisticDTO.setTotalSpentPerDay(dayTotal);
            statisticDTO.setTotalSpentPerNight(nightTotal);

            return statisticDTO;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Get statistics about smart meter curl -i -X GET
     * http://localhost:8080/pa165/rest/smartmeters/statistics/1
     * @param id ID of the smart meter
     * @return statistics for smart meter
     */
    @RequestMapping(value = "/statistics/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final SmartMeterStatisticsDTO getSmartMeterStatistics(@PathVariable("id") long id) {
        try {
            SmartMeterDTO sm = smartMeterFacade.getSmartMeter(id);
            SmartMeterStatisticsDTO statisticDTO = new SmartMeterStatisticsDTO();
            statisticDTO.setSmartMeterDescription(sm.getSmartMeterDescription());
            statisticDTO.setRunning(sm.isRunning());
            statisticDTO.setCumulativePowerConsumption(sm.getCumulativePowerConsumption());

            double averagePerDay = smartMeterFacade.getAveragePowerSpentForDayTimeSmartMeter(id, DayTime.Day);
            double averagePerNight = smartMeterFacade.getAveragePowerSpentForDayTimeSmartMeter(id, DayTime.Night);

            statisticDTO.setAverageSpentPerDay(averagePerDay);
            statisticDTO.setAverageSpentPerNight(averagePerNight);
            return statisticDTO;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}
