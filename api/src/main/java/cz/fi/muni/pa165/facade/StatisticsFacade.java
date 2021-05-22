package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.StatisticsDTO;

import java.time.LocalDate;

public interface StatisticsFacade {
    StatisticsDTO getUsersStatisticsForInterval(LocalDate from, LocalDate to);
}
