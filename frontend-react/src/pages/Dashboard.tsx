import React, { FC } from "react";
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import Typography from "@material-ui/core/Typography";
import DateFnsUtils from '@date-io/date-fns';

const Dashboard: FC = () => {
    const [selectedDate, setSelectedDate] = React.useState<Date | null>(
        new Date('2021-04-20T21:11:54'),
    );

    const handleDateChange = (date: Date | null) => {
        setSelectedDate(date);
    };

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
                value={selectedDate}
                onChange={handleDateChange}
                KeyboardButtonProps={{
                    'aria-label': 'change date',
                }}
            />
            </MuiPickersUtilsProvider>
        </div>
    );
};

export default Dashboard;
