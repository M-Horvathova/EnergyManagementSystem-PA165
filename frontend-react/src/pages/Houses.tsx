import React, { FunctionComponent, useEffect, useState, Fragment } from "react";
import HouseList from "../components/HouseList";
import House from "../interfaces/House";
import { Button, Grid, Typography } from "@material-ui/core";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router";
import { getCurrentUser } from "../services/auth";
import axios from "axios";
import Config from "../utils/Config";

export interface HousesProps {}

const Houses: FunctionComponent<HousesProps> = () => {
    const [houses, setHouses] = useState<Array<House>>([]);
    const { t } = useTranslation();
    const history = useHistory();

    useEffect(() => {
        const user = getCurrentUser();

        axios({
            method: "GET",
            url: Config.urlRestBase + `/houses/findByUser/${user?.id}`,
        }).then((response) => {
            const result: Array<House> = response.data.map((obj: any) => {
                return {
                    id: obj.id,
                    name: obj.name,
                    address: {
                        id: obj.address.id,
                        city: obj.address.city,
                        code: obj.address.code,
                        country: obj.address.country,
                        postCode: obj.address.postCode,
                        street: obj.address.street,
                    },
                    running: obj.running,
                } as House;
            });
            setHouses(result);
        });
    }, []);

    const handleOnRemove = async (id: number) => {
        await axios({
            method: "DELETE",
            url: Config.urlRestBase + `/houses/${id}`,
        });
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
                        onClick={() => history.push("/house/create")}
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
