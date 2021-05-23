import React, { FunctionComponent } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { useHistory } from "react-router";
import { useTranslation } from "react-i18next";
import PortalUserDTO from "../interfaces/PortalUserDTO";

/*
  author: Martin Podhora
*/
export interface UserTileProps {
    user: PortalUserDTO;
}

const useStyles = makeStyles({
    running: {
        minWidth: 300,
        maxWidth: 345,
    },
    notRunning: { minWidth: 300, maxWidth: 345, backgroundColor: "#f5f5f5" },
});

const UserTile: FunctionComponent<UserTileProps> = ({ user }) => {
    const history = useHistory();
    const { t } = useTranslation();
    const { id } = user;

    return (
        <Card variant="outlined">
            <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                    {user.email}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {!user.firstName ? "---" : user.firstName}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {!user.lastName ? "---" : user.lastName}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                    {!user.phone ? "---" : user.phone}
                </Typography>
            </CardContent>
            <CardActions>
                <Button
                    size="small"
                    color="primary"
                    onClick={() => history.push("/user/" + id)}
                >
                    {t("users.open")}
                </Button>
            </CardActions>
        </Card>
    );
};

export default UserTile;