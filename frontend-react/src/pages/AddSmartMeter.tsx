import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";
import SmartMetrForm from "../components/SmartMetrForm";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";

export interface AddSmartMeterProps {}

const AddSmartMeter: FunctionComponent<AddSmartMeterProps> = () => {
    const history = useHistory();
    const { t } = useTranslation();

    const handleOnSubmit = async (
        description : string,
        running: boolean,
    ) => {
        await axios({
            method: "POST",
            url: Config.urlRestBase + `/smartmeters/create`,
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
                {t("createSmartMeter.create")}
            </Typography>
            <Grid container spacing={3}>
                <SmartMetrForm onSubmit={handleOnSubmit} />
            </Grid>
        </Fragment>
    );
};

export default AddSmartMeter;
