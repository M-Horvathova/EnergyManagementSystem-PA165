import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Button, ButtonGroup, Grid, Typography } from "@material-ui/core";
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
            url: Config.urlRestBase + `/smartMeters/${id}`,
        }).then((response) => {
            console.log(response.data);
            setSmartMeter({
                id: response.data.id,
                smartMeterDescription: response.data.smartMeterDescription,
                running: response.data.running,
                powerConsumptionSinceLastLog: response.data.powerConsumptionSinceLastLog,
                cumulativePowerConsumption: response.data.cumulativePowerConsumption,
                lastLogTakenAt: response.data.lastLogTakenAt
            });
        });
    }, [id]);

/*    const handleOnClick = () => {
        if (smartMeter) {
            axios({
                method: "PUT",
                url: Config.urlRestBase + `/smartmeters/${id}`,
                data: {
                    description: smartMeter.smartMeterDescription
                },
            }).then((response) => {
                setSmartMeter({
                    id: response.data.id,
                    smartMeterDescription: response.data.smartMeterDescription,
                    running: response.data.running,
                    powerConsumptionSinceLastLog: response.data.powerConsumptionSinceLastLog,
                    cumulativePowerConsumption: response.data.cumulativePowerConsumption,
                    lastLogTakenAt: response.data.lastLogTakenAt
                });
            });
        }
    };*/

    return (
        <Fragment>
            <Typography gutterBottom variant="h4" component="h2">
                {smartMeter?.smartMeterDescription}
            </Typography>
            <Grid container spacing={3}>
                <Grid item>
                    <ButtonGroup
                        variant="contained"
                        disableElevation
                        aria-label="contained button group"
                    >
                        <Button
                            color="primary"
                            onClick={() => history.push("/smartMeter/create")}
                        >
                            {t("smartmeter.add")}
                        </Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
        </Fragment>
    );
};

export default SmartMeterDetail;
