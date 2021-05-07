import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import House from "../interfaces/House";
import HouseForm from "../components/HouseForm";
import { useHistory } from "react-router";

export interface EditHouseProps {}

const EditHouse: FunctionComponent<EditHouseProps> = () => {
    const { id } = useParams<{ id: string }>();
    const [house, setHouse] = useState<House | null>();
    const history = useHistory();

    useEffect(() => {
        // TODO fetch house with id
        setHouse({
            id: +id,
            name: "Kounicova",
            address: {
                id: 1,
                city: "Brno",
                code: null,
                country: "Cesko",
                postCode: "8997666",
                street: null,
            },
        });
    }, []);

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
                Edit house #{house?.id}
            </Typography>
            <Grid container spacing={3}>
                {house ? (
                    <HouseForm house={house} onSubmit={handleOnSubmit} />
                ) : (
                    ""
                )}
            </Grid>
        </Fragment>
    );
};

export default EditHouse;
