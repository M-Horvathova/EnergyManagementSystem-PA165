package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.StatisticsDTO;
import cz.fi.muni.pa165.facade.StatisticsFacade;
import cz.fi.muni.pa165.restapi.exceptions.InvalidDateFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

/**
 * @author Martin Podhora
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private static final DateTimeFormatter ISO_LOCAL_DATE_TIME_Z = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral('T')
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .appendLiteral('Z')
                .toFormatter();
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

        try {
            if (from != null && !from.equals("null")) {
                dateFrom = LocalDate.parse(from, ISO_LOCAL_DATE_TIME_Z);
            }

            if (to != null && !to.equals("null")) {
                dateTo = LocalDate.parse(to, ISO_LOCAL_DATE_TIME_Z);
            }
        } catch (DateTimeParseException ex) {
            throw new InvalidDateFormatException();
        }

        return statisticsFacade.getUsersStatisticsForInterval(dateFrom, dateTo);
    }
}
