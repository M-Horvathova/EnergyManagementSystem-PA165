import { FunctionComponent, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";
import { Button, Grid, TextField, Checkbox } from "@material-ui/core";
import { useTranslation } from "react-i18next";

export interface SmartMetrFormProps {
    smartMeter ?: SmartMeterHouseDTO;
    onSubmit(
        smartMeterDescription: string,
        isRunning: boolean
    ): void;
}

const useStyles = makeStyles({
    button: {
        marginTop: 15,
    },
});

const SmartMetrForm: FunctionComponent<SmartMetrFormProps> = ({ smartMeter, onSubmit }) => {
    const classes = useStyles();
    const { t } = useTranslation();

    const [smartMeterDescription, setSmartMeterDescription] = useState<string>(smartMeter ? smartMeter.smartMeterDescription : "");
    const [isRunning, setIsRunning] = useState<boolean>(smartMeter ? smartMeter.running : false);

    const handleOnClick = () => {
        onSubmit(smartMeterDescription, isRunning);
    };

    return (
        <Grid item xs={12}>
            <TextField
                size="small"
                label={t("smartMeterForm.description")}
                type="text"
                fullWidth
                margin="normal"
                variant="outlined"
                value={smartMeterDescription}
                onChange={(e) => setSmartMeterDescription(e.target.value)}
            />
            <Checkbox
                checked={isRunning}
                name={t("smartMeterForm.running")}
                onChange={(e) => setIsRunning(e.target.checked)}
            />
            <Button
                className={classes.button}
                variant="contained"
                color="primary"
                disableElevation
                onClick={handleOnClick}
            >
                {t("smartMeterForm.confirm")}
            </Button>
        </Grid>
    );
};

export default SmartMetrForm;
