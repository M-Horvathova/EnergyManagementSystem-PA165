import React, { FunctionComponent } from "react"
import { makeStyles } from "@material-ui/core/styles"
import Card from "@material-ui/core/Card"
import CardActions from "@material-ui/core/CardActions"
import CardContent from "@material-ui/core/CardContent"
import Button from "@material-ui/core/Button"
import Typography from "@material-ui/core/Typography"
import House from "../interfaces/House"
import { useHistory } from "react-router"

export interface HouseTileProps {
    house: House
}

const useStyles = makeStyles({
    root: {
        minWidth: 300,
        maxWidth: 345,
    },
})

const HouseTile: FunctionComponent<HouseTileProps> = ({ house }) => {
    const classes = useStyles()
    const history = useHistory()
    const { address } = house

    const { id } = house

    return (
        <Card className={classes.root}>
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
                    onClick={() => history.push("/pa165/house/" + id)}
                >
                    Open
                </Button>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/pa165/house/edit/" + id)}
                >
                    Edit
                </Button>
                <Button
                    size="small"
                    color="secondary"
                    onClick={() => history.push("/pa165/house/edit/" + id)}
                >
                    Remove
                </Button>
            </CardActions>
        </Card>
    )
}

export default HouseTile
