import React, { FunctionComponent } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";

export interface SmartMeterTileProps {
    smartMeter: SmartMeterHouseDTO;
    onRemove(id: number): void;
}

const useStyles = makeStyles({
    running: {
        minWidth: 300,
        maxWidth: 345,
    },
    notRunning: { minWidth: 300, maxWidth: 345, backgroundColor: "#f5f5f5" },
});

const SmartMeterTile: FunctionComponent<SmartMeterTileProps> = ({
    smartMeter,
    onRemove,
}) => {
    const classes = useStyles();
    const history = useHistory();
    const { t } = useTranslation();
    const { id } = smartMeter;

    return (
        <Card
            className={
                smartMeter.running ? classes.running : classes.notRunning
            }
            variant="outlined"
        >
            <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                    {smartMeter.id}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {!smartMeter.smartMeterDescription
                        ? "---"
                        : smartMeter.smartMeterDescription}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {smartMeter.lastLogTakenAt}
                </Typography>
            </CardContent>
            <CardActions>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/smartMeter/" + id)}
                >
                    {t("house.open")}
                </Button>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/smartMeter/edit/" + id)}
                >
                    {t("house.edit")}
                </Button>
                <Button
                    size="small"
                    color="secondary"
                    onClick={() => onRemove(id)}
                >
                    {t("house.remove")}
                </Button>
            </CardActions>
        </Card>
    );
};

export default SmartMeterTile;
