import React, { FunctionComponent, useEffect, useState, Fragment } from "react";
import User from "../interfaces/User";
import HouseList from "../components/HouseList";
import House from "../interfaces/House";
import { Button, Grid, Typography } from "@material-ui/core";
import { useTranslation } from "react-i18next";

export interface HousesProps {
    user: User;
}

const Houses: FunctionComponent<HousesProps> = ({ user }) => {
    const [houses, setHouses] = useState<Array<House>>([]);
    const { t } = useTranslation();

    useEffect(() => {
        // TODO call backend to fetch
        setHouses([
            {
                id: 1,
                name: "Kounicova",
                address: {
                    id: 1,
                    city: "Brno",
                    code: null,
                    country: "Cesko",
                    postCode: "8997666",
                    street: null,
                },
            },
            {
                id: 2,
                name: "Vinarská",
                address: {
                    id: 2,
                    city: "Brno",
                    code: "12",
                    country: "Cesko",
                    postCode: "8997666",
                    street: null,
                },
            },
            {
                id: 3,
                name: "Dom pri lesnej ceste",
                address: {
                    id: 3,
                    city: "Bratislava",
                    code: "158",
                    country: "Slovensko",
                    postCode: "8997666",
                    street: "Lesna ulica nad Dunajom",
                },
            },
            {
                id: 4,
                name: "Môj dom",
                address: {
                    id: 3,
                    city: "Bratislava",
                    code: "158",
                    country: "Slovensko",
                    postCode: "8997666",
                    street: "Lesna ulica nad Dunajom",
                },
            },
        ]);
    }, []);

    const handleOnRemove = (id: number) => {
        // TODO call backend to remove
        const updatedHouses = [...houses];
        setHouses(updatedHouses.filter((house) => house.id !== id));
    };

    return (
        <Fragment>
            <Typography gutterBottom variant="h4" component="h2">
                {t("houses.houses")}
            </Typography>
            <Grid container spacing={3}>
                <Grid item>
                    <Button
                        variant="contained"
                        color="primary"
                        disableElevation
                    >
                        {t("houses.add")}
                    </Button>
                </Grid>
            </Grid>
            <HouseList houses={houses} onRemove={handleOnRemove} />
        </Fragment>
    );
};

export default Houses;
