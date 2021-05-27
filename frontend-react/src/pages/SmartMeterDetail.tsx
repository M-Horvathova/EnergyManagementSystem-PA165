import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import {Card, Grid, ListItem, Typography} from "@material-ui/core";
import { useParams } from "react-router-dom";
import axios from "axios";
import Config from "../utils/Config";
import { useTranslation } from "react-i18next";
import DateFnsUtils from "@date-io/date-fns";
import {KeyboardDatePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import SmartMeterStatisticsDTO from "../interfaces/SmartMeterStatisticsDTO";
import SmartMeterPowerSpentForDate from "../interfaces/SmartMeterPowerSpentForDate";
import CardContent from '@material-ui/core/CardContent';
import List from "@material-ui/core/List";
import {TurnedInNot, TurnedIn, OfflineBolt, NightsStay, WbSunny} from "@material-ui/icons";
import Avatar from '@material-ui/core/Avatar';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemText from '@material-ui/core/ListItemText';

export interface SmartMeterDetailProps {}

/*
  author: Matej Rišňovský
  design: Michaela Horváthová
*/
const SmartMeterDetail: FunctionComponent<SmartMeterDetailProps> = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const [smartMeterPowerSpentInDate, setSmartMeterPowerSpentInDate] = useState<SmartMeterPowerSpentForDate | null>();
    const [selectedDateFrom, setSelectedDateFrom] = React.useState<Date | null>(
        new Date(Date.now()),
    );
    const [selectedDateTo, setSelectedDateTo] = React.useState<Date | null>(
        new Date(Date.now()),
    );
    const [smartMeterStats, setSmartMeterStats] = useState<SmartMeterStatisticsDTO | null>();

    const handleDateFromChange = (date: Date | null) => {
        setSelectedDateFrom(date);

        axios({
            method: "POST",
            url: Config.urlRestBase + `/smartmeters/powerSpent/${id}`,
            data: {
                day: date?.getDate(),
                month: date?.getMonth(),
                year: date?.getFullYear()
            },
        }).then((response) => {
            setSmartMeterPowerSpentInDate({
                result : response.data
            });
        });
    };

    const handleDateToChange = (date: Date | null) => {
        setSelectedDateTo(date);

        axios({
            method: "POST",
            url: Config.urlRestBase + `/smartmeters/powerSpent/${id}`,
            data: {
                day: date?.getDate(),
                month: date?.getMonth(),
                year: date?.getFullYear()
            },
        }).then((response) => {
            setSmartMeterPowerSpentInDate({
                result : response.data
            });
        });
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
                cumulativePowerConsumption: response.data.cumulativePowerConsumption,
                averageSpentPerDay: response.data.averageSpentPerDay,
                averageSpentPerNight: response.data.averageSpentPerNight
            });
        });
    }, [id]);

    return (
        <Fragment>
            <Typography gutterBottom variant="h4" component="h2">
                {smartMeterStats?.smartMeterDescription}
            </Typography>

            <Card style={{ minHeight: '30vh' }}>
                <CardContent>
                    <Grid container spacing={2} alignItems="center">
                        <Grid item xs={12} md={8} sm={8}>
                            <Typography variant='h5'>
                                Total tats description TODO
                            </Typography>
                        <List>
                            <ListItem>
                                <ListItemAvatar>
                                    <Avatar>
                                        {smartMeterStats?.running ? <TurnedIn /> : <TurnedInNot/>}
                                    </Avatar>
                                </ListItemAvatar>
                                <ListItemText>
                                    <Typography variant="body2" color="textSecondary" component="p">
                                        { smartMeterStats?.running ? t("smartMeter.turnedOn") : t("smartMeter.turnedOff")}
                                    </Typography>
                                </ListItemText>
                            </ListItem>
                            <ListItem>
                                <ListItemAvatar>
                                    <Avatar>
                                        <OfflineBolt/>
                                    </Avatar>
                                </ListItemAvatar>
                                <ListItemText>
                                    <Typography variant="body2" color="textSecondary" component="p">
                                        { t("smartMeter.totalPowerConsumption") + " " + smartMeterStats?.cumulativePowerConsumption + " kwH"}
                                    </Typography>
                                </ListItemText>
                            </ListItem>
                            <ListItem>
                                <ListItemAvatar>
                                    <Avatar>
                                        <WbSunny/>
                                    </Avatar>
                                </ListItemAvatar>
                                <ListItemText>
                                    <Typography variant="body2" color="textSecondary" component="p">
                                        { t("smartMeter.averagePowerConsumptionInDay") + " " + smartMeterStats?.averageSpentPerDay + " kwH"}
                                    </Typography>
                                </ListItemText>
                            </ListItem>
                            <ListItem>
                                <ListItemAvatar>
                                    <Avatar>
                                        <NightsStay/>
                                    </Avatar>
                                </ListItemAvatar>
                                <ListItemText>
                                    <Typography variant="body2" color="textSecondary" component="p">
                                        { t("smartMeter.averagePowerConsumptionAtNight") + " " + smartMeterStats?.averageSpentPerNight + " kwH"}
                                    </Typography>
                                </ListItemText>
                            </ListItem>
                        </List>
                        </Grid>
                            <p/>
                            <Grid item xs={12}>
                            <Typography variant='h5'>
                                Stats in time frame description TODO
                            </Typography>
                            </Grid>
                        <Grid item  xs={12} md={6} sm={6}>
                            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                <KeyboardDatePicker
                                    disableToolbar
                                    variant="inline"
                                    format="MM/dd/yyyy"
                                    margin="normal"
                                    id="date-picker-inline"
                                    label={t("smartMeter.powerSpentForDate")}
                                    value={selectedDateFrom}
                                    onChange={handleDateFromChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                />

                            </MuiPickersUtilsProvider>
                        </Grid>
                        <Grid item  xs={12} md={6} sm={6}>
                            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                <KeyboardDatePicker
                                    disableToolbar
                                    variant="inline"
                                    format="MM/dd/yyyy"
                                    margin="normal"
                                    id="date-picker-inline"
                                    label={t("smartMeter.powerSpentForDate")}
                                    value={selectedDateTo}
                                    onChange={handleDateToChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                />
                            </MuiPickersUtilsProvider>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body2" color="textPrimary" component="p">
                                {smartMeterPowerSpentInDate?.result === undefined ? "" :  smartMeterPowerSpentInDate?.result + " kwH"}
                            </Typography>
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>
        </Fragment>
    );
};

export default SmartMeterDetail;
