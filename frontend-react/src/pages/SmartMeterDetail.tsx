import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Button, ButtonGroup, Grid, Typography, TextField } from "@material-ui/core";
import { useHistory, useParams } from "react-router-dom";
import axios from "axios";
import Config from "../utils/Config";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";
import { useTranslation } from "react-i18next";

export interface SmartMeterDetailProps {}

const SmartMeterDetail: FunctionComponent<SmartMeterDetailProps> = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const history = useHistory();
    const [smartMeter, setSmartMeter] = useState<SmartMeterHouseDTO | null>();

    useEffect(() => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/smartmeters/${id}`,
        }).then((response) => {
            console.log(response.data);
            setSmartMeter({
                id: response.data.id,
                smartMeterDescription: response.data.smartMeterDescription,
                running: response.data.running,
                powerConsumptionSinceLastLog: response.data.powerConsumptionSinceLastLog,
                cumulativePowerConsumption: response.data.cumulativePowerConsumption,
                lastLogTakenAt: response.data.lastLogTakenAt,
                houseId : response.data.houseId
            });
        });
    }, [id]);

    return (
        <Fragment>
            <Typography gutterBottom variant="h4" component="h2">
                {smartMeter?.smartMeterDescription}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
                {smartMeter?.running ? t("smartMeter.turnedOn") : t("smartMeter.turnedOff")}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
                { t("smartMeter.totalPowerConsumption") + " " + smartMeter?.cumulativePowerConsumption + " kwH"}
            </Typography>
            <Grid container spacing={3}>
            </Grid>
        </Fragment>
    );
};

export default SmartMeterDetail;
