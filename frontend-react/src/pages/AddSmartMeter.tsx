import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import SmartMetrForm from "../components/SmartMetrForm";
import { useHistory } from "react-router";
import { useLocation } from "react-router";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";

export interface AddSmartMeterProps {}

const AddSmartMeter: FunctionComponent<AddSmartMeterProps> = () => {
    const {search} = useLocation();
    const houseId: number = +search.split("houseId=")[1];
    const history = useHistory();
    const { t } = useTranslation();

    const handleOnSubmit = async (
        smartMeterDescription : string,
        running: boolean,
    ) => {
        await axios({
            method: "POST",
            url: Config.urlRestBase + `/smartmeters/create`,
            data: {
                houseId : houseId,
                smartMeterDescription,
                running
            },
        });
        history.push(`/house/${houseId}`);
    };

    return (
        <Fragment>
            <Typography variant="h4" component="h2">
                {t("smartMeter.create")}
            </Typography>
            <Grid container spacing={3}>
                <SmartMetrForm onSubmit={handleOnSubmit} />
            </Grid>
        </Fragment>
    );
};

export default AddSmartMeter;
