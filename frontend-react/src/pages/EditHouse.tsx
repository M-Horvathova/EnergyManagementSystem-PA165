import React, { Fragment, FunctionComponent, useEffect, useState } from "react";
import { Grid, Typography } from "@material-ui/core";
import { useParams } from "react-router-dom";
import House from "../interfaces/House";
import HouseForm from "../components/HouseForm";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";

export interface EditHouseProps {}

const EditHouse: FunctionComponent<EditHouseProps> = () => {
    const { id } = useParams<{ id: string }>();
    const [house, setHouse] = useState<House | null>();
    const history = useHistory();
    const { t } = useTranslation();

    useEffect(() => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/houses/${id}`,
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
                running: response.data.running,
            });
        });
    }, [id]);

    const handleOnSubmit = async (
        name: string,
        street: string,
        code: string,
        city: string,
        postCode: string,
        country: string
    ) => {
        await axios({
            method: "PUT",
            url: Config.urlRestBase + `/houses/${id}`,
            data: {
                name,
                street: !street ? null : street,
                code: !code ? null : code,
                city,
                postCode,
                country,
            },
        });
        history.push("/houses");
    };

    return (
        <Fragment>
            <Typography variant="h4" component="h2">
                {t("editHouse.edit")} #{house?.id}
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
