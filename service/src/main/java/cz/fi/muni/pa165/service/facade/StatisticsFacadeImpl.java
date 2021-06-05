package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.portalUser.UserStatisticDTO;
import cz.fi.muni.pa165.dto.portalUser.AllUsersStatisticsDTO;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.SmartMeter;
import cz.fi.muni.pa165.facade.StatisticsFacade;
import cz.fi.muni.pa165.service.serviceInterface.HouseService;
import cz.fi.muni.pa165.service.serviceInterface.MeterLogService;
import cz.fi.muni.pa165.service.serviceInterface.PortalUserService;
import cz.fi.muni.pa165.service.serviceInterface.SmartMeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Martin Podhora
 */
@Service
public class StatisticsFacadeImpl implements StatisticsFacade {
    final static Logger log = LoggerFactory.getLogger(MeterLogFacadeImpl.class);

    private PortalUserService portalUserService;
    private HouseService houseService;
    private MeterLogService meterLogService;
    private SmartMeterService smartMeterService;

    @Autowired
    public StatisticsFacadeImpl(
            PortalUserService portalUserService,
            HouseService houseService,
            MeterLogService meterLogService,
            SmartMeterService smartMeterService) {
        this.portalUserService = portalUserService;
        this.houseService = houseService;
        this.meterLogService = meterLogService;
        this.smartMeterService = smartMeterService;
    }

    public AllUsersStatisticsDTO getUsersStatisticsForInterval(LocalDate from, LocalDate to) {
        AllUsersStatisticsDTO statistics = new AllUsersStatisticsDTO();
        List<PortalUser> users = portalUserService.getAllUsers();
        for (PortalUser user : users) {
            UserStatisticDTO userStatisticDTO = new UserStatisticDTO();
            userStatisticDTO.setUserName(user.getEmail());
            double totalPowerSpent = 0.0;
            double averagePowerSpent = 0.0;
            List<House> houses = houseService.findByUser(user);
            for (House house : houses) {
                List<SmartMeter> smartMeters = smartMeterService.findByHouse(house);
                totalPowerSpent += smartMeterService.getPowerSpentForIntervalForSmartMeters(from, to, smartMeters);
                averagePowerSpent += smartMeterService.getAveragePowerSpentForIntervalForSmartMeters(from, to, smartMeters);
            }

            userStatisticDTO.setFromToTotalSpent(totalPowerSpent);
            userStatisticDTO.setFromToAverageSpent(averagePowerSpent / (houses.size() == 0 ? 1 : houses.size()));
            statistics.addStatistic(userStatisticDTO);
        }

        statistics.setTotalSpent(smartMeterService.getAllPowerSpent());
        statistics.setAverageSpent(smartMeterService.getAveragePowerSpent());

        return statistics;
    }
}
