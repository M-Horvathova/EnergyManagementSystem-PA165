import { FunctionComponent, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import House from "../interfaces/House";
import { Button, Grid, TextField } from "@material-ui/core";
import { useTranslation } from "react-i18next";

export interface HouseFormProps {
    house?: House;
    onSubmit(
        name: string,
        street: string,
        code: string,
        city: string,
        postCode: string,
        country: string
    ): void;
}

const useStyles = makeStyles({
    button: {
        marginTop: 15,
    },
});

const HouseForm: FunctionComponent<HouseFormProps> = ({ house, onSubmit }) => {
    const classes = useStyles();
    const { t } = useTranslation();

    const [name, setName] = useState<string>(house ? house.name : "");
    const [street, setStreet] = useState<string>(
        house ? (house.address.street ? house.address.street : "") : ""
    );
    const [code, setCode] = useState<string>(
        house ? (house.address.code ? house.address.code : "") : ""
    );
    const [city, setCity] = useState<string>(house ? house.address.city : "");
    const [postCode, setPostCode] = useState<string>(
        house ? house.address.postCode : ""
    );
    const [country, setCountry] = useState<string>(
        house ? house.address.country : ""
    );

    const handleOnClick = () => {
        // validate not null items
        if (!name || !city || !postCode || !country) {
            return;
        }

        onSubmit(name, street, code, city, postCode, country);
    };

    return (
        <Grid item xs={12}>
            <TextField
                error={!name}
                required
                size="small"
                label={t("houseForm.name")}
                type="text"
                fullWidth
                margin="normal"
                variant="outlined"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <TextField
                label={t("houseForm.street")}
                size="small"
                type="text"
                fullWidth
                margin="normal"
                variant="outlined"
                value={street}
                onChange={(e) => setStreet(e.target.value)}
            />
            <TextField
                label={t("houseForm.code")}
                size="small"
                type="text"
                fullWidth
                margin="normal"
                variant="outlined"
                value={code}
                onChange={(e) => setCode(e.target.value)}
            />
            <TextField
                error={!city}
                required
                label={t("houseForm.city")}
                size="small"
                type="text"
                fullWidth
                margin="normal"
                variant="outlined"
                value={city}
                onChange={(e) => setCity(e.target.value)}
            />
            <TextField
                error={!postCode}
                required
                label={t("houseForm.postCode")}
                size="small"
                type="text"
                fullWidth
                margin="normal"
                variant="outlined"
                value={postCode}
                onChange={(e) => setPostCode(e.target.value)}
            />
            <TextField
                error={!country}
                required
                label={t("houseForm.country")}
                size="small"
                type="text"
                fullWidth
                margin="normal"
                variant="outlined"
                value={country}
                onChange={(e) => setCountry(e.target.value)}
            />
            <Button
                className={classes.button}
                variant="contained"
                color="primary"
                disableElevation
                disabled={!name || !city || !postCode || !country}
                onClick={handleOnClick}
            >
                {t("houseForm.confirm")}
            </Button>
        </Grid>
    );
};

export default HouseForm;
