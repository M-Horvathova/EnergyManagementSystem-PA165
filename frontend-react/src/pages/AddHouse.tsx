import { Fragment, FunctionComponent } from "react";
import { Grid, Typography } from "@material-ui/core";
import HouseForm from "../components/HouseForm";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";

export interface AddHouseProps {}

const AddHouse: FunctionComponent<AddHouseProps> = () => {
    const history = useHistory();
    const { t } = useTranslation();

    const handleOnSubmit = async (
        name: string,
        street: string,
        code: string,
        city: string,
        postCode: string,
        country: string
    ) => {
        await axios({
            method: "POST",
            url: Config.urlRestBase + "/houses/create",
            data: {
                name,
                street: !street ? null : street,
                code: !code ? null : code,
                city,
                postCode,
                country,
                running: true,
                portalUserId: 1,
            },
        });
        history.push("/houses");
    };

    return (
        <Fragment>
            <Typography variant="h4" component="h2">
                {t("addHouse.add")}
            </Typography>
            <Grid container spacing={3}>
                <HouseForm onSubmit={handleOnSubmit} />
            </Grid>
        </Fragment>
    );
};

export default AddHouse;
