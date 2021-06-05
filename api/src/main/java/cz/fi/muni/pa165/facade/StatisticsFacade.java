package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.portalUser.AllUsersStatisticsDTO;

import java.time.LocalDate;

/**
 * @author Martin Podhora
 */
public interface StatisticsFacade {
    /**
     * Method used to get statistics in time interval
     * @param from can be date from or null, in that case search has no lower bound
     * @param to can be date to or null, in that case search has no upper bound
     * @return statistics DTO
     */
    AllUsersStatisticsDTO getUsersStatisticsForInterval(LocalDate from, LocalDate to);
}
