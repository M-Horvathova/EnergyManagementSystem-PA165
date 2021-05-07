import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import House from "../interfaces/House";

export interface HousePageProps {}

const HousePage: FunctionComponent<HousePageProps> = () => {
    const { id } = useParams<{ id: string }>();
    const [house, setHouse] = useState<House | null>();

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
    }, [id]);

    return (
        <Fragment>
            <Typography variant="h4" component="h2">
                House #{house?.id}
            </Typography>
            <Grid container spacing={3}>
                <Grid item>
                    <Typography gutterBottom variant="h5" component="h2">
                        {house?.name}
                    </Typography>
                </Grid>
            </Grid>
        </Fragment>
    );
};

export default HousePage;
