import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";
import SmartMetrForm from "../components/SmartMetrForm";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";

export interface EditSmartMeterProps {}

const EditSmartMeter: FunctionComponent<EditSmartMeterProps> = () => {
    const { id } = useParams<{ id: string }>();
    const [smartMeter, setSmartMeter] = useState<SmartMeterHouseDTO | null>();
    const history = useHistory();
    const { t } = useTranslation();

    useEffect(() => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/smartmeters/${id}`,
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
    }, [id]);

    const handleOnSubmit = async (
        description : string,
        running: boolean,
    ) => {
        await axios({
            method: "PUT",
            url: Config.urlRestBase + `/smartmeters/${id}`,
            data: {
                description,
                running
            },
        });
        history.push("/smartmeters");
    };

    return (
        <Fragment>
            <Typography variant="h4" component="h2">
                {t("editSmartMeter.edit")} #{smartMeter?.id}
            </Typography>
            <Grid container spacing={3}>
                {smartMeter ? (
                    <SmartMetrForm smartMeter={smartMeter} onSubmit={handleOnSubmit} />
                ) : (
                    ""
                )}
            </Grid>
        </Fragment>
    );
};

export default EditSmartMeter;