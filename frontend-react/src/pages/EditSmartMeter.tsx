import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import SmartMeterForm from "../components/SmartMeterForm";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";
import BackHeader from "../components/BackHeader";

export interface EditSmartMeterProps {}

/*
  author: Matej Rišňovský
*/
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
            console.log(response);
            setSmartMeter({
                id: response.data.id,
                smartMeterDescription: response.data.smartMeterDescription,
                running: response.data.running,
                powerConsumptionSinceLastLog:
                    response.data.powerConsumptionSinceLastLog,
                cumulativePowerConsumption:
                    response.data.cumulativePowerConsumption,
                lastLogTakenAt: response.data.lastLogTakenAt,
                houseId: response.data.houseId,
            });
        });
    }, [id]);

    const handleOnSubmit = async (
        smartMeterDescription: string,
        running: boolean
    ) => {
        await axios({
            method: "PUT",
            url: Config.urlRestBase + `/smartmeters/${id}`,
            data: {
                smartMeterDescription,
                running,
            },
        });
        history.replace(`/house/${smartMeter?.houseId}`);
    };

    return (
        <Fragment>
            <BackHeader />
            <Typography variant="h4" component="h2">
                {t("smartMeter.edit")} '{smartMeter?.smartMeterDescription}'
            </Typography>
            <Grid container spacing={3}>
                {smartMeter ? (
                    <SmartMeterForm
                        smartMeter={smartMeter}
                        onSubmit={handleOnSubmit}
                    />
                ) : (
                    ""
                )}
            </Grid>
        </Fragment>
    );
};

export default EditSmartMeter;
