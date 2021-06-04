import React, { Fragment, FunctionComponent } from "react";
import { Grid, Typography } from "@material-ui/core";
import SmartMeterForm from "../components/SmartMeterForm";
import { useHistory } from "react-router";
import { useLocation } from "react-router";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";
import BackHeader from "../components/BackHeader";

export interface AddSmartMeterProps {}

/*
  author: Matej Rišňovský
*/
const AddSmartMeter: FunctionComponent<AddSmartMeterProps> = () => {
    const { search } = useLocation();
    const houseId: number = +search.split("houseId=")[1];
    const history = useHistory();
    const { t } = useTranslation();

    const handleOnSubmit = async (
        smartMeterDescription: string,
        running: boolean
    ) => {
        await axios({
            method: "POST",
            url: Config.urlRestBase + `/smartmeters/create`,
            data: {
                houseId: houseId,
                smartMeterDescription,
                running,
            },
        });
        history.replace(`/house/${houseId}`);
    };

    return (
        <Fragment>
            <BackHeader />
            <Typography variant="h4" component="h2">
                {t("smartMeter.create")}
            </Typography>
            <Grid container spacing={3}>
                <SmartMeterForm onSubmit={handleOnSubmit} />
            </Grid>
        </Fragment>
    );
};

export default AddSmartMeter;
