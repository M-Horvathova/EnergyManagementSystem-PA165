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
            "email": (/.*@.*..*/).test(email) ? "" : "registration.invalidEmail",
            "password": password ? "" : "registration.passwordRequired",
            "confirmPassword": confirmPassword ? "" : "registration.confirmPasswordRequired",
            "firstName": firstName ? "" : "registration.firstNameRequired",
            "lastName": lastName ? "" : "registration.lastNameRequired",
            "phone": phone.length > 9 ? "" : "registration.invalidPhoneLength"
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

    const { t } = useTranslation();

    const handleLoginEvent = async () => {
        if (validate()) {
            window.alert("testing")
        } else {
            return;
        }
        await auth.register(email, password, confirmPassword, firstName, lastName, phone);
        window.location.href = "/pa165";
    };

    return (
        <Grid container className={classes.app}>
            <Card className={classes.card}>
                <CardContent>
                    <AccountBoxIcon style={{ fontSize: "60" }} />
                    <Typography variant="h5" component="h1">
                        {t("Registration.Registration")}
                    </Typography>
                    <TextField
                        error={errors["email"] != null}
                        helperText={errors["email"]}
                        label={t("registration.email")}
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
                        label={t("registration.password")}
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
                        error={errors["confirmPassword"] != null}
                        helperText={errors["confirmPassword"]}
                        label={t("registration.confirmPassword")}
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
                        label={t("registration.firstName")}
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
                        label={t("registration.lastName")}
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
                        label={t("registration.phone")}
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
                        {t("registration.register")}
                    </Button>
                </CardActions>
            </Card>
        </Grid>
    );
};

export default Registration;
