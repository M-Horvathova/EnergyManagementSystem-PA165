import { Fragment, FunctionComponent } from "react";
import { Grid, Typography } from "@material-ui/core";
import HouseForm from "../components/HouseForm";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";

export interface AddHouseProps {}

const AddHouse: FunctionComponent<AddHouseProps> = () => {
    const history = useHistory();
    const { t } = useTranslation();

    const handleOnSubmit = (
        name: string,
        street: string,
        code: string,
        city: string,
        postCode: string,
        country: string
    ) => {
        // TODO call backend
        history.push("/pa165/houses");
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