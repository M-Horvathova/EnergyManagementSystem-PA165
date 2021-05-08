import React, { FC, useState } from "react";
import { makeStyles } from "@material-ui/styles";

import { Link } from "react-router-dom";

import Card from "@material-ui/core/Card";
import Grid from "@material-ui/core/Grid";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";

import PersonAddIcon from "@material-ui/icons/PersonAdd";
import { useTranslation } from "react-i18next";

/*
    Author: Michaela Horváthová
*/
const useStyles = makeStyles({
    app: {
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        minHeight: "100vh",
    },
    card: {
        boxShadow: "0 3px 5px 2px rgba(56, 56, 56, 0.83)",
        maxWidth: "700px",
    },

    text: {
        alignContent: "left",
    },

    button: {
        borderRadius: "12px",
        flex: "1",
    },
});

const Login: FC = () => {
    const classes = useStyles();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const { t } = useTranslation();

    // const register = async (): Promise<void> => {

    // };

    return (
        <Grid container className={classes.app}>
            <Card className={classes.card}>
                <CardContent>
                    <PersonAddIcon style={{ fontSize: "60" }} />
                    <Typography variant="h5" component="h1">
                        {t("register.register")}
                    </Typography>
                    <TextField
                        label={t("register.email")}
                        type="email"
                        name="email"
                        fullWidth
                        autoComplete="email"
                        margin="normal"
                        variant="outlined"
                        color="secondary"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <TextField
                        label={t("register.password")}
                        type="password"
                        name="password"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        color="secondary"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <TextField
                        label={t("register.password_conf")}
                        type="password"
                        name="password"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        color="secondary"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />

                    <Typography variant="subtitle2" align="left" paragraph>
                        {t("register.already_registered")}{" "}
                        <Link to="/login/">{t("register.sign_in")}</Link>
                    </Typography>
                </CardContent>
                <CardActions className={classes.text}>
                    <Button
                        className={classes.button}
                        variant="contained"
                        size="large"
                        color="primary"
                    >
                        {t("register.register")}
                    </Button>
                </CardActions>
            </Card>
        </Grid>
    );
};

export default Login;
