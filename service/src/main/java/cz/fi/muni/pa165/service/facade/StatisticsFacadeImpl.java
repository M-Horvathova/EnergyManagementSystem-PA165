package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.StatisticDTO;
import cz.fi.muni.pa165.dto.StatisticsDTO;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.facade.StatisticsFacade;
import cz.fi.muni.pa165.service.HouseService;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.PortalUserService;
import cz.fi.muni.pa165.service.SmartMeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public StatisticsDTO getUsersStatisticsForInterval(LocalDate from, LocalDate to) {
        StatisticsDTO statistics = new StatisticsDTO();
        statistics.setFrom(from.toString());
        statistics.setTo(to.toString());
        List<PortalUser> users = portalUserService.getAllUsers();
        for (PortalUser user : users) {
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setUserName(user.getEmail());
            double totalPowerSpent = 0.0;
            double averagePowerSpent = 0.0;
            for (House house : user.getHouses()) {
                totalPowerSpent += smartMeterService.getPowerSpentForIntervalForSmartMeters(from, to, house.getSmartMeters());
                averagePowerSpent += smartMeterService.getAveragePowerSpentForIntervalForSmartMeters(from, to, house.getSmartMeters());
            }
            statisticDTO.setFromToTotalSpent(totalPowerSpent);
            statisticDTO.setFromToAverageSpent(averagePowerSpent);
            statistics.addStatistic(statisticDTO);
        }

        statistics.setTotalSpent(smartMeterService.getAllPowerSpent());
        statistics.setAverageSpent(smartMeterService.getAveragePowerSpent());

        return statistics;
    }
}
