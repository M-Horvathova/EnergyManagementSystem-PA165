import React, { FunctionComponent } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import HouseDTO from "../interfaces/HouseDTO";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";

export interface HouseTileProps {
    house: HouseDTO;
    onRemove(id: number): void;
}

const useStyles = makeStyles({
    running: {
        minWidth: 300,
        maxWidth: 345,
    },
    notRunning: { minWidth: 300, maxWidth: 345, backgroundColor: "#f5f5f5" },
});

const HouseTile: FunctionComponent<HouseTileProps> = ({ house, onRemove }) => {
    const classes = useStyles();
    const history = useHistory();
    const { address } = house;
    const { t } = useTranslation();
    const { id } = house;

    return (
        <Card
            className={house.running ? classes.running : classes.notRunning}
            variant="outlined"
        >
            <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                    {house.name}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {!address.street ? "---" : address.street}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {!address.code ? "---" : address.code}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {address.city}, {address.postCode}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {address.country}
                </Typography>
            </CardContent>
            <CardActions>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/house/" + id)}
                >
                    {t("houses.open")}
                </Button>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/house/edit/" + id)}
                >
                    {t("houses.edit")}
                </Button>
                <Button
                    size="small"
                    color="secondary"
                    onClick={() => onRemove(id)}
                >
                    {t("houses.remove")}
                </Button>
            </CardActions>
        </Card>
    );
};

export default HouseTile;
