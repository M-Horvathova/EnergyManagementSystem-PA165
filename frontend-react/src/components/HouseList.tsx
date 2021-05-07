import { Grid } from "@material-ui/core"
import React, { FunctionComponent, Fragment } from "react"
import House from "../interfaces/House"
import HouseTile from "./HouseTile"

export interface HouseListProps {
    houses: Array<House>
}

const HouseList: FunctionComponent<HouseListProps> = ({ houses }) => {
    return (
        <Fragment>
            <Grid container direction="row" alignItems="flex-start" spacing={3}>
                {houses.map((house) => {
                    return (
                        <Grid key={house.id} item>
                            <HouseTile house={house} />
                        </Grid>
                    )
                })}
            </Grid>
        </Fragment>
    )
}

export default HouseList
