import { Fragment, FunctionComponent } from "react";
import { Grid, Typography } from "@material-ui/core";
import HouseForm from "../components/HouseForm";
import { useHistory } from "react-router";

export interface AddHouseProps {}

const AddHouse: FunctionComponent<AddHouseProps> = () => {
    const history = useHistory();

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
                Add house
            </Typography>
            <Grid container spacing={3}>
                <HouseForm onSubmit={handleOnSubmit} />
            </Grid>
        </Fragment>
    );
};

export default AddHouse;
