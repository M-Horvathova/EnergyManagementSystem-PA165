import React, { FC, useState } from "react";
import { makeStyles } from "@material-ui/styles";
import Card from "@material-ui/core/Card";
import Grid from "@material-ui/core/Grid";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";

import AccountBoxIcon from "@material-ui/icons/AccountBox";
import { useTranslation } from "react-i18next";
import auth from "../services/auth";
import Alert from "@material-ui/lab/Alert";

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

const Registration: FC = () => {
    const classes = useStyles();

    const validate = () => {
        var temp: {[index: string]: string;} = {
            "email": (/.*@.*..*/).test(email) ? "" : t("register.err_invalid_email"),
            "password": password ? "" : t("register.err_password_required"),
            "confirmPassword": confirmPassword ? "" : t("register.err_confirm_password_required"),
            "firstName": firstName ? "" : t("register.err_first_name_required"),
            "lastName": lastName ? "" : t("register.err_last_name_required"),
            "phone": phone.length > 9 ? "" : t("register.err_invalid_phone"),
            "passwordsSame": password === confirmPassword ? "" : t("register.err_password_not_same")
        };

        setErrors({...temp});
        return Object.values(temp).every(x => x === "");
    }

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phone, setPhone] = useState("");
    const [errors, setErrors] = useState<{[index: string]: string}>({});
    const [beErrors, setBEErrors] = useState(false);

    const { t } = useTranslation();

    const handleLoginEvent = async () => {
        if (validate()) {
            var status = await auth.register(email, password, confirmPassword, firstName, lastName, phone);
            if (status == 409) {
                setBEErrors(true);
                return;
            }
            window.location.href = "/pa165";
        }
    };

    return (
        <Grid container className={classes.app}>
            <Card className={classes.card}>
                <CardContent>
                    <AccountBoxIcon style={{ fontSize: "60" }} />
                    <Typography variant="h5" component="h1">
                        {t("register.register")}
                    </Typography>
                    {beErrors && <Alert variant="outlined" severity="error">{t("register.already_registered")}</Alert>}
                    <TextField
                        error={errors["email"] != null}
                        helperText={errors["email"]}
                        label={t("register.email")}
                        type="text"
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
                        error={errors["password"] != null}
                        helperText={errors["password"]}
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
                        error={errors["confirmPassword"] != null || errors["passwordsSame"] != null}
                        helperText={errors["confirmPassword"] === "" ? errors["passwordsSame"] : errors["confirmPassword"]}
                        label={t("register.password_conf")}
                        type="password"
                        name="confirmPassword"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        color="secondary"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                    <TextField
                        error={errors["firstName"] != null}
                        helperText={errors["firstName"]}
                        label={t("register.first_name")}
                        type="text"
                        name="firstName"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        color="secondary"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                    <TextField
                        error={errors["lastName"] != null}
                        helperText={errors["lastName"]}
                        label={t("register.last_name")}
                        type="text"
                        name="lastName"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        color="secondary"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                    />
                    <TextField
                        error={errors["phone"] != null}
                        helperText={errors["phone"]}
                        label={t("register.phone")}
                        type="text"
                        name="phone"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        color="secondary"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                    />
                </CardContent>
                <CardActions className={classes.text}>
                    <Button
                        className={classes.button}
                        variant="contained"
                        size="large"
                        color="primary"
                        onClick={handleLoginEvent}
                    >
                        {t("register.register")}
                    </Button>
                </CardActions>
            </Card>
        </Grid>
    );
};

export default Registration;
