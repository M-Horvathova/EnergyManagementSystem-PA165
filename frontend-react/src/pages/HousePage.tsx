import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Button, ButtonGroup, Grid, Typography } from "@material-ui/core";
import { useHistory, useParams } from "react-router-dom";
import HouseDTO from "../interfaces/HouseDTO";
import axios from "axios";
import Config from "../utils/Config";
import SmartMeterList from "../components/SmartMeterList";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";
import { useTranslation } from "react-i18next";

export interface HousePageProps {}

const HousePage: FunctionComponent<HousePageProps> = () => {
    const { id } = useParams<{ id: string }>();
    const { t } = useTranslation();
    const history = useHistory();
    const [house, setHouse] = useState<HouseDTO | null>();
    const [smartMeters, setSmartMeters] = useState<Array<SmartMeterHouseDTO>>(
        []
    );

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

    const handleOnRemove = async (id: number) => {
        await axios({
            method: "DELETE",
            url: Config.urlRestBase + `/smartmeters/${id}`,
        });
        const updatedMeters = [...smartMeters];
        setSmartMeters(
            updatedMeters.filter((smartMeter) => smartMeter.id !== id)
        );
    };

    return (
        <Fragment>
            <Typography gutterBottom variant="h4" component="h2">
                {house?.name}
            </Typography>
            <Grid container spacing={3}>
                <Grid item>
                    <ButtonGroup
                        variant="contained"
                        disableElevation
                        aria-label="contained button group"
                    >
                        <Button
                            color="primary"
                            onClick={() => history.push(`/smartMeter/create?houseId=${id}`)}
                        >
                            {t("house.add")}
                        </Button>
                        <Button color="secondary" onClick={handleOnClick}>
                            {house?.running ? t("house.off") : t("house.on")}
                        </Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
            <SmartMeterList
                smartMeters={smartMeters}
                onRemove={handleOnRemove}
            />
        </Fragment>
    );
};

export default HousePage;
