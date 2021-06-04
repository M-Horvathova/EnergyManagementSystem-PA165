import React, { FunctionComponent } from "react";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";

/*
  author: Matej Rišňovský
*/
export interface SmartMeterTileProps {
    smartMeter: SmartMeterHouseDTO;
    onRemove(id: number): void;
}

const SmartMeterTile: FunctionComponent<SmartMeterTileProps> = ({
    smartMeter,
    onRemove,
}) => {
    const history = useHistory();
    const { t } = useTranslation();
    const { id } = smartMeter;

    return (
        <Card variant="outlined">
            <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                    {!smartMeter.smartMeterDescription
                        ? "---"
                        : smartMeter.smartMeterDescription}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {smartMeter.cumulativePowerConsumption + " kwH"}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {smartMeter.running
                        ? t("smartMeter.turnedOn")
                        : t("smartMeter.turnedOff")}
                </Typography>
            </CardContent>
            <CardActions>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/smartMeter/" + id)}
                >
                    {t("smartMeter.open")}
                </Button>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/smartMeter/edit/" + id)}
                >
                    {t("smartMeter.edit")}
                </Button>
                <Button
                    size="small"
                    color="secondary"
                    onClick={() => onRemove(id)}
                >
                    {t("smartMeter.remove")}
                </Button>
            </CardActions>
        </Card>
    );
};

export default SmartMeterTile;
