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
        maxWidth: 345,
    },
})

const HouseTile: FunctionComponent<HouseTileProps> = ({ house }) => {
    const classes = useStyles()
    const history = useHistory()

    const { id } = house

    return (
        <Card className={classes.root}>
            <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                    {house.name}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    Lizards are a widespread group of squamate reptiles, with
                    over 6,000 species, ranging across all continents except
                    Antarctica
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
