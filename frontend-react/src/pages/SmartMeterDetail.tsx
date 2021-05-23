import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import axios from "axios";
import Config from "../utils/Config";
import { useTranslation } from "react-i18next";
import DateFnsUtils from "@date-io/date-fns";
import {KeyboardDatePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import SmartMeterStatisticsDTO from "../interfaces/SmartMeterStatisticsDTO";
import SmartMeterPowerSpentForDate from "../interfaces/SmartMeterPowerSpentForDate";

export interface SmartMeterDetailProps {}

/*
  author: Matej Rišňovský
*/
const SmartMeterDetail: FunctionComponent<SmartMeterDetailProps> = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const [smartMeterPowerSpentInDate, setSmartMeterPowerSpentInDate] = useState<SmartMeterPowerSpentForDate | null>();
    const [selectedDate, setSelectedDate] = React.useState<Date | null>(
        new Date(Date.now()),
    );
    const [smartMeterStats, setSmartMeterStats] = useState<SmartMeterStatisticsDTO | null>();

    const handleDateChange = (date: Date | null) => {
        setSelectedDate(date);

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
            <Typography variant="body2" color="textSecondary" component="p">
                { smartMeterStats?.running ? t("smartMeter.turnedOn") : t("smartMeter.turnedOff")}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
                { t("smartMeter.totalPowerConsumption") + " " + smartMeterStats?.cumulativePowerConsumption + " kwH"}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
                { t("smartMeter.averagePowerConsumptionInDay") + " " + smartMeterStats?.averageSpentPerDay + " kwH"}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
                { t("smartMeter.averagePowerConsumptionAtNight") + " " + smartMeterStats?.averageSpentPerNight + " kwH"}
            </Typography>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    id="date-picker-inline"
                    label={t("smartMeter.powerSpentForDate")}
                    value={selectedDate}
                    onChange={handleDateChange}
                    KeyboardButtonProps={{
                        'aria-label': 'change date',
                    }}
                />
                <Typography variant="body2" color="textSecondary" component="p">
                    {smartMeterPowerSpentInDate?.result === undefined ? "" :  smartMeterPowerSpentInDate?.result + " kwH"}
                </Typography>
            </MuiPickersUtilsProvider>
            <Grid container spacing={3}>
            </Grid>
        </Fragment>
    );
};

export default SmartMeterDetail;
