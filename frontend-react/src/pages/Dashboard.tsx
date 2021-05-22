import React, {FC, Fragment, FunctionComponent, useEffect, useState} from "react";
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import Typography from "@material-ui/core/Typography";
import DateFnsUtils from '@date-io/date-fns';
import {Button, Grid} from "@material-ui/core";
import HouseList from "../components/HouseList";
import {useTranslation} from "react-i18next";
import HouseDTO from "../interfaces/HouseDTO";
import PortalUserListingDTO from "../interfaces/PortalUserListingDTO";
import UserList from "../components/UserList";
import auth, {getCurrentUser} from "../services/auth";
import axios from "axios";
import Config from "../utils/Config";
import classes from "*.module.css";

export interface UsersProps {}

const Dashboard: FunctionComponent<UsersProps> = () => {
    const [from, setFrom] = React.useState<Date | null>(
        null,
    );
    const [to, setTo] = React.useState<Date | null>(
        null,
    );
    const handleFromDateChange = (date: Date | null) => {
        setFrom(date);
    };
    const handleToDateChange = (date: Date | null) => {
        setTo(date);
    };
    const { t } = useTranslation();

    const handleGetStatisticsEvent = async () => {

    };

    // const [users, setUsers] = useState<PortalUserListingDTO>(new PortalUserListingDTO());
    //
    // useEffect(() => {
    //     axios({
    //         method: "GET",
    //         url: Config.urlRestBase + `/users/1`,
    //     }).then((response) => {
    //         const result: PortalUserListingDTO = response.data as PortalUserListingDTO;
    //         setUsers(result);
    //     });
    // }, []);

    return (
        <div>
            <Typography variant={"h3"} align={"center"}>
                {"Welcome admin!"}
            </Typography>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    id="date-picker-inline"
                    label="Date picker inline"
                    value={from}
                    onChange={handleFromDateChange}
                    KeyboardButtonProps={{
                        'aria-label': 'change date',
                    }}
                />
                <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    id="date-picker-inline"
                    label="Date picker inline"
                    value={to}
                    onChange={handleToDateChange}
                    KeyboardButtonProps={{
                        'aria-label': 'change date',
                    }}
                />
                <Button
                    className={classes.button}
                    variant="contained"
                    size="large"
                    color="primary"
                    onClick={handleGetStatisticsEvent}
                >
                    {t("dashboard.get_statistics")}
                </Button>
            </MuiPickersUtilsProvider>
        </div>

        // <Fragment>
        //     <Typography gutterBottom variant="h4" component="h2">
        //         {t("users.users")}
        //     </Typography>
        //     <UserList users={users}/>
        // </Fragment>
    );
};

export default Dashboard;
