import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Button, Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import HouseDTO from "../interfaces/HouseDTO";
import axios from "axios";
import Config from "../utils/Config";

export interface HousePageProps {}

const HousePage: FunctionComponent<HousePageProps> = () => {
    const { id } = useParams<{ id: string }>();
    const [house, setHouse] = useState<HouseDTO | null>();
    const [smartMeters, setSmartMeters] = useState<any>();

    useEffect(() => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/houses/${id}`,
        }).then((response) => {
            console.log(response.data);
            setHouse({
                id: response.data.id,
                name: response.data.name,
                address: {
                    id: response.data.address.id,
                    city: response.data.address.city,
                    code: response.data.address.code,
                    country: response.data.address.country,
                    postCode: response.data.address.postCode,
                    street: response.data.address.street,
                },
                smartMeters: response.data.smartMeters,
                running: response.data.running,
            });

            setSmartMeters(response.data.smartMeters);
        });
    }, [id]);

    const handleOnClick = () => {
        if (house) {
            axios({
                method: "PUT",
                url: Config.urlRestBase + `/houses/running/${id}`,
                data: { running: !house.running },
            }).then((response) => {
                setHouse({
                    id: response.data.id,
                    name: response.data.name,
                    address: {
                        id: response.data.address.id,
                        city: response.data.address.city,
                        code: response.data.address.code,
                        country: response.data.address.country,
                        postCode: response.data.address.postCode,
                        street: response.data.address.street,
                    },
                    smartMeters: response.data.smartMeters,
                    running: response.data.running,
                });

                setSmartMeters(response.data.smartMeters);
            });
        }
    };

    return (
        <Fragment>
            <Typography gutterBottom variant="h4" component="h2">
                House #{house?.id}
            </Typography>
            <Grid container spacing={3}>
                <Grid item>
                    <Button
                        variant="contained"
                        color="primary"
                        disableElevation
                        onClick={handleOnClick}
                    >
                        Turn {house?.running ? "off" : "on"} the house
                    </Button>
                </Grid>
                <Grid item>{JSON.stringify(smartMeters)}</Grid>
            </Grid>
        </Fragment>
    );
};

export default HousePage;
