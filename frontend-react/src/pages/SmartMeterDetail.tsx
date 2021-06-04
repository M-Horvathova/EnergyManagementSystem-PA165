import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Card, Grid, ListItem, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import axios from "axios";
import Config from "../utils/Config";
import { useTranslation } from "react-i18next";
import DateFnsUtils from "@date-io/date-fns";
import {
    KeyboardDatePicker,
    MuiPickersUtilsProvider,
} from "@material-ui/pickers";
import SmartMeterStatisticsDTO from "../interfaces/SmartMeterStatisticsDTO";
import SmartMeterStatsIntervalDTO from "../interfaces/SmartMeterStatsIntervalDTO";
import CardContent from "@material-ui/core/CardContent";
import List from "@material-ui/core/List";
import {
    TurnedInNot,
    TurnedIn,
    OfflineBolt,
    NightsStay,
    WbSunny,
} from "@material-ui/icons";
import Avatar from "@material-ui/core/Avatar";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import ListItemText from "@material-ui/core/ListItemText";

export interface SmartMeterDetailProps {}

/*
  author: Matej Rišňovský
  design: Michaela Horváthová
*/
const SmartMeterDetail: FunctionComponent<SmartMeterDetailProps> = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const [selectedDateFrom, setSelectedDateFrom] = React.useState<Date | null>(
        new Date(new Date().setDate(new Date().getDate() - 1))
    );
    const [selectedDateTo, setSelectedDateTo] = React.useState<Date | null>(
        new Date(Date.now())
    );
    const [smartMeterStats, setSmartMeterStats] =
        useState<SmartMeterStatisticsDTO | null>();
    const [smartMeterStatsInterval, setSmartMeterStatsInterval] =
        useState<SmartMeterStatsIntervalDTO | null>();

    useEffect(() => {
        axios({
            method: "POST",
            url: Config.urlRestBase + `/smartmeters/powerSpentInterval/${id}`,
            data: {
                dayFrom: selectedDateFrom?.getDate(),
                monthFrom:
                    selectedDateFrom?.getMonth() === undefined
                        ? 1
                        : selectedDateFrom?.getMonth() + 1,
                yearFrom: selectedDateFrom?.getFullYear(),
                dayTo: selectedDateTo?.getDate(),
                monthTo:
                    selectedDateTo?.getMonth() === undefined
                        ? 1
                        : selectedDateTo?.getMonth() + 1,
                yearTo: selectedDateTo?.getFullYear(),
            },
        }).then((response) => {
            setSmartMeterStatsInterval({
                cumulativePowerConsumption:
                    response.data.cumulativePowerConsumption,
                totalSpentPerNight: response.data.totalSpentPerNight,
                totalSpentPerDay: response.data.totalSpentPerDay,
            });
        });
    }, [selectedDateFrom, selectedDateTo, id]);

    const handleDateFromChange = (date: Date | null) => {
        setSelectedDateFrom(date);
    };

    const handleDateToChange = (date: Date | null) => {
        setSelectedDateTo(date);
    };

    useEffect(() => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/smartmeters/statistics/${id}`,
        }).then((response) => {
            console.log(response.data);
            setSmartMeterStats({
                smartMeterDescription: response.data.smartMeterDescription,
                running: response.data.running,
                cumulativePowerConsumption:
                    response.data.cumulativePowerConsumption,
                averageSpentPerDay: response.data.averageSpentPerDay,
                averageSpentPerNight: response.data.averageSpentPerNight,
            });
        });
    }, [id]);

    return (
        <Fragment>
            <Typography gutterBottom variant="h4" component="h2">
                {smartMeterStats?.smartMeterDescription}
            </Typography>

            <Card style={{ minHeight: "15vh" }}>
                <CardContent>
                    <Grid container spacing={2} alignItems="center">
                        <Grid item xs={12} md={12} sm={12}>
                            <Typography variant="h5">
                                {t("smartMeter.totalStats")}
                            </Typography>
                        </Grid>

                        <Grid item xs={12} md={6} sm={6}>
                            <List>
                                <ListItem>
                                    <ListItemAvatar>
                                        <Avatar>
                                            {smartMeterStats?.running ? (
                                                <TurnedIn color="secondary" />
                                            ) : (
                                                <TurnedInNot color="secondary" />
                                            )}
                                        </Avatar>
                                    </ListItemAvatar>
                                    <ListItemText>
                                        <Typography
                                            variant="body2"
                                            component="p"
                                        >
                                            {smartMeterStats?.running
                                                ? t("smartMeter.turnedOn")
                                                : t("smartMeter.turnedOff")}
                                        </Typography>
                                    </ListItemText>
                                </ListItem>
                                <ListItem>
                                    <ListItemAvatar>
                                        <Avatar>
                                            <OfflineBolt />
                                        </Avatar>
                                    </ListItemAvatar>
                                    <ListItemText>
                                        <Typography
                                            variant="body2"
                                            component="p"
                                        >
                                            {t(
                                                "smartMeter.totalPowerConsumption"
                                            ) +
                                                " " +
                                                smartMeterStats?.cumulativePowerConsumption +
                                                " kwH"}
                                        </Typography>
                                    </ListItemText>
                                </ListItem>
                            </List>
                        </Grid>
                        <Grid item xs={12} md={6} sm={6}>
                            <List>
                                <ListItem>
                                    <ListItemAvatar>
                                        <Avatar>
                                            <WbSunny />
                                        </Avatar>
                                    </ListItemAvatar>
                                    <ListItemText>
                                        <Typography
                                            variant="body2"
                                            component="p"
                                        >
                                            {t(
                                                "smartMeter.averagePowerConsumptionInDay"
                                            ) +
                                                " " +
                                                Math.round(
                                                    (smartMeterStats?.averageSpentPerDay ===
                                                    undefined
                                                        ? 0
                                                        : smartMeterStats?.averageSpentPerDay +
                                                          Number.EPSILON) * 100
                                                ) /
                                                    100 +
                                                " kwH"}
                                        </Typography>
                                    </ListItemText>
                                </ListItem>
                                <ListItem>
                                    <ListItemAvatar>
                                        <Avatar>
                                            <NightsStay />
                                        </Avatar>
                                    </ListItemAvatar>
                                    <ListItemText>
                                        <Typography
                                            variant="body2"
                                            component="p"
                                        >
                                            {t(
                                                "smartMeter.averagePowerConsumptionAtNight"
                                            ) +
                                                " " +
                                                Math.round(
                                                    (smartMeterStats?.averageSpentPerNight ===
                                                    undefined
                                                        ? 0
                                                        : smartMeterStats?.averageSpentPerNight +
                                                          Number.EPSILON) * 100
                                                ) /
                                                    100 +
                                                " kwH"}
                                        </Typography>
                                    </ListItemText>
                                </ListItem>
                            </List>
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>
            <p />
            <Card style={{ minHeight: "30vh" }}>
                <CardContent>
                    <Grid container spacing={2} alignItems="center">
                        <Grid item xs={12}>
                            <Typography variant="h5">
                                {t("smartMeter.intervalStats")}
                            </Typography>
                        </Grid>
                        <Grid item xs={12} md={6} sm={6}>
                            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                <KeyboardDatePicker
                                    disableToolbar
                                    variant="inline"
                                    format="MM/dd/yyyy"
                                    margin="normal"
                                    id="date-picker-from"
                                    label={t("smartMeter.powerSpentFromDate")}
                                    value={selectedDateFrom}
                                    onChange={handleDateFromChange}
                                    KeyboardButtonProps={{
                                        "aria-label": "change date",
                                    }}
                                />
                            </MuiPickersUtilsProvider>
                        </Grid>
                        <Grid item xs={12} md={6} sm={6}>
                            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                <KeyboardDatePicker
                                    disableToolbar
                                    variant="inline"
                                    format="MM/dd/yyyy"
                                    margin="normal"
                                    id="date-picker-to"
                                    label={t("smartMeter.powerSpentToDate")}
                                    value={selectedDateTo}
                                    onChange={handleDateToChange}
                                    KeyboardButtonProps={{
                                        "aria-label": "change date",
                                    }}
                                />
                            </MuiPickersUtilsProvider>
                        </Grid>
                        <Grid item xs={12} md={8} sm={8}>
                            {smartMeterStatsInterval?.cumulativePowerConsumption ===
                            undefined ? (
                                <List />
                            ) : (
                                <List>
                                    <ListItem>
                                        <ListItemAvatar>
                                            <Avatar>
                                                <OfflineBolt />
                                            </Avatar>
                                        </ListItemAvatar>
                                        <ListItemText>
                                            <Typography
                                                variant="body2"
                                                component="p"
                                            >
                                                {t(
                                                    "smartMeter.totalPowerConsumptionInterval"
                                                ) +
                                                    " " +
                                                    smartMeterStatsInterval?.cumulativePowerConsumption +
                                                    " kwH"}
                                            </Typography>
                                        </ListItemText>
                                    </ListItem>
                                    <ListItem>
                                        <ListItemAvatar>
                                            <Avatar>
                                                <WbSunny />
                                            </Avatar>
                                        </ListItemAvatar>
                                        <ListItemText>
                                            <Typography
                                                variant="body2"
                                                component="p"
                                            >
                                                {t(
                                                    "smartMeter.totalPowerConsumptionInDay"
                                                ) +
                                                    " " +
                                                    smartMeterStatsInterval?.totalSpentPerDay +
                                                    " kwH"}
                                            </Typography>
                                        </ListItemText>
                                    </ListItem>
                                    <ListItem>
                                        <ListItemAvatar>
                                            <Avatar>
                                                <NightsStay />
                                            </Avatar>
                                        </ListItemAvatar>
                                        <ListItemText>
                                            <Typography
                                                variant="body2"
                                                component="p"
                                            >
                                                {t(
                                                    "smartMeter.totalPowerConsumptionAtNight"
                                                ) +
                                                    " " +
                                                    smartMeterStatsInterval?.totalSpentPerNight +
                                                    " kwH"}
                                            </Typography>
                                        </ListItemText>
                                    </ListItem>
                                </List>
                            )}
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>
        </Fragment>
    );
};

export default SmartMeterDetail;
