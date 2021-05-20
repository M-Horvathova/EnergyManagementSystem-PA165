import { Grid } from "@material-ui/core";
import React, { FunctionComponent, Fragment } from "react";
import { useTranslation } from "react-i18next";
import SmartMeterTile from "./SmartMeterTile";
import SmartMeterHouseDTO from "../interfaces/SmartMeterHouseDTO";

export interface SmartMeterListProps {
    smartMeters: Array<SmartMeterHouseDTO>;
    onRemove(id: number): void;
}

const SmartMeterList: FunctionComponent<SmartMeterListProps> = ({
    smartMeters,
    onRemove,
}) => {
    const { t } = useTranslation();
    return (
        <Fragment>
            <Grid container direction="row" alignItems="flex-start" spacing={3}>
                {smartMeters.length === 0 ? (
                    <Grid item>{t("house.nothing")}</Grid>
                ) : (
                    ""
                )}
                {smartMeters.map((smartMeter) => {
                    return (
                        <Grid key={smartMeter.id} item>
                            <SmartMeterTile
                                smartMeter={smartMeter}
                                onRemove={onRemove}
                            />
                        </Grid>
                    );
                })}
            </Grid>
        </Fragment>
    );
};

export default SmartMeterList;
