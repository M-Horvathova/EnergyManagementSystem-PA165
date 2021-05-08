import { Grid } from "@material-ui/core";
import React, { FunctionComponent, Fragment } from "react";
import House from "../interfaces/House";
import HouseTile from "./HouseTile";
import { useTranslation } from "react-i18next";

export interface HouseListProps {
    houses: Array<House>;
    onRemove(id: number): void;
}

const HouseList: FunctionComponent<HouseListProps> = ({ houses, onRemove }) => {
    const { t } = useTranslation();
    return (
        <Fragment>
            <Grid container direction="row" alignItems="flex-start" spacing={3}>
                {houses.length === 0 ? (
                    <Grid item>{t("houses.nothing")}</Grid>
                ) : (
                    ""
                )}
                {houses.map((house) => {
                    return (
                        <Grid key={house.id} item>
                            <HouseTile house={house} onRemove={onRemove} />
                        </Grid>
                    );
                })}
            </Grid>
        </Fragment>
    );
};

export default HouseList;
