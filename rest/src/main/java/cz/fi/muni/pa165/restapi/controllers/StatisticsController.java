package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.StatisticsDTO;
import cz.fi.muni.pa165.facade.StatisticsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsFacade statisticsFacade;

    @Autowired
    public StatisticsController(StatisticsFacade statisticsFacade) {
        this.statisticsFacade = statisticsFacade;
    }

    @RequestMapping(path = "/{from}/{to}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final StatisticsDTO getStatistics(@PathVariable("from") String from, @PathVariable("to") String to) {
        LocalDate dateFrom = null;
        LocalDate dateTo = null;

        if (from != null && !from.equals("null")) {
            dateFrom = LocalDate.parse(from);
        }

        if (to != null && !to.equals("null")) {
            dateTo = LocalDate.parse(to);
        }

        return statisticsFacade.getUsersStatisticsForInterval(dateFrom, dateTo);
    }
}
