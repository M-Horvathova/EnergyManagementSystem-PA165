import React, {FunctionComponent, useEffect, useState} from "react";
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import Typography from "@material-ui/core/Typography";
import DateFnsUtils from '@date-io/date-fns';
import {Card, CardContent, ListItem} from "@material-ui/core";
import {useTranslation} from "react-i18next";
import axios from "axios";
import Config from "../utils/Config";
import StatisticsDTO from "../interfaces/StatisticsDTO";
import Paper from "@material-ui/core/Paper";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TablePagination from "@material-ui/core/TablePagination";
import TableHead from "@material-ui/core/TableHead";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import {createStyles, makeStyles, Theme} from "@material-ui/core/styles";
import StatisticDTO from "../interfaces/StatisticDTO";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import {OfflineBolt, BarChart} from "@material-ui/icons";
import ListItemText from "@material-ui/core/ListItemText";
import List from "@material-ui/core/List";

/*
  author: Martin Podhora
*/

function descendingComparator<T>(a: T, b: T, orderBy: keyof T) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

type Order = 'asc' | 'desc';

function getComparator<Key extends keyof any>(
    order: Order,
    orderBy: Key,
): (a: { [key in Key]: number | string }, b: { [key in Key]: number | string }) => number {
    return order === 'desc'
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort<T>(array: T[], comparator: (a: T, b: T) => number) {
    const stabilizedThis = array.map((el, index) => [el, index] as [T, number]);
    stabilizedThis.sort((a, b) => {
        const order = comparator(a[0], b[0]);
        if (order !== 0) return order;
        return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
}

interface HeadCell {
    disablePadding: boolean;
    id: keyof StatisticDTO;
    label: string;
    numeric: boolean;
}

interface EnhancedTableProps {
    classes: ReturnType<typeof useStyles>;
    onRequestSort: (event: React.MouseEvent<unknown>, property: keyof StatisticDTO) => void;
    order: Order;
    orderBy: string;
    rowCount: number;
}

function EnhancedTableHead(props: EnhancedTableProps) {
    const { t } = useTranslation();

    const headCells: HeadCell[] = [
        { id: 'userName', numeric: false, disablePadding: false, label: t("dashboard.table_username") },
        { id: 'fromToTotalSpent', numeric: true, disablePadding: false, label: t("dashboard.table_total_spent") },
        { id: 'fromToAverageSpent', numeric: true, disablePadding: false, label: t("dashboard.table_average_spent") }
    ];

    const { classes, order, orderBy, onRequestSort } = props;
    const createSortHandler = (property: keyof StatisticDTO) => (event: React.MouseEvent<unknown>) => {
        onRequestSort(event, property);
    };

    return (
        <TableHead>
            <TableRow>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        align={headCell.numeric ? 'right' : 'left'}
                        padding={headCell.disablePadding ? 'none' : 'default'}
                        sortDirection={orderBy === headCell.id ? order : false}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}
                        >
                            {headCell.label}
                            {orderBy === headCell.id ? (
                                <span className={classes.visuallyHidden}>
                  {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                </span>
                            ) : null}
                        </TableSortLabel>
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            width: '100%',
        },
        paper: {
            width: '100%',
            marginBottom: theme.spacing(2),
        },
        table: {
            minWidth: 750,
        },
        visuallyHidden: {
            border: 0,
            clip: 'rect(0 0 0 0)',
            height: 1,
            margin: -1,
            overflow: 'hidden',
            padding: 0,
            position: 'absolute',
            top: 20,
            width: 1,
        },
        button: {
            borderRadius: "12px",
            flex: "1",
            top: 20,
        },
        cardroot: {
            minWidth: 275,
        },
        bullet: {
            display: 'inline-block',
            margin: '0 2px',
            transform: 'scale(0.8)',
        },
        title: {
            fontSize: 20,
            fontWeight: 'bold',
        },
        pos: {
            marginBottom: 12,
        },
    }),
);

export const getFromDate = (from: Date|null) => {
    return from === null ? null : from.toISOString();
}

export const getToDate = (to: Date|null) => {
    return to === null ? null : to.toISOString();
}

export interface UsersProps {}

const Dashboard: FunctionComponent<UsersProps> = () => {
    const [from, setFrom] = React.useState<Date | null>( new Date(Date.now()));
    const [to, setTo] = React.useState<Date | null>( new Date(Date.now()));
    const [statistics, setStatistics] = useState<StatisticsDTO>(new StatisticsDTO());
    const { t } = useTranslation();

    const handleFromDateChange = (date: Date | null) => {
        setFrom(date);
    };
    const handleToDateChange = (date: Date | null) => {
        setTo(date);
    };

    useEffect(() => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/statistics/${getFromDate(from)}/${getToDate(to)}`,
        }).then((response) => {
            const result: StatisticsDTO = response.data as StatisticsDTO;
            setStatistics(result);
            setRows(result.statistics);
        });
    }, [from ,to]);

    const classes = useStyles();
    const [order, setOrder] = React.useState<Order>('asc');
    const [orderBy, setOrderBy] = React.useState<keyof StatisticDTO>('userName');
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [rows, setRows] = React.useState<StatisticDTO[]>([]);

    const handleRequestSort = (event: React.MouseEvent<unknown>, property: keyof StatisticDTO) => {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const emptyRows = rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage);


    return (
        <div>
            <Typography variant={"h3"} align={"center"}>
                {t("dashboard.welcome_admin")}
            </Typography>
            <Card className={classes.cardroot}>
                <CardContent>
                    <Typography className={classes.title} color="textPrimary" gutterBottom>
                        {t("dashboard.all_time_statistics")}
                    </Typography>
                    <List>
                        <ListItem>
                            <ListItemAvatar>
                                <Avatar>
                                    <OfflineBolt/>
                                </Avatar>
                            </ListItemAvatar>
                            <ListItemText>
                                <Typography className={classes.pos} color="textSecondary">
                                    {t("dashboard.all_time_total")}: {statistics.totalSpent}
                                </Typography>
                            </ListItemText>
                        </ListItem>
                        <ListItem>
                            <ListItemAvatar>
                                <Avatar>
                                    <BarChart />
                                </Avatar>
                            </ListItemAvatar>
                            <ListItemText>
                                <Typography className={classes.pos} color="textSecondary">
                                    {t("dashboard.all_time_average")}:
                                    {Math.round((statistics.averageSpent + Number.EPSILON) * 100) / 100}
                                </Typography>
                            </ListItemText>
                        </ListItem>
                    </List>
                </CardContent>
            </Card>

            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    id="date-picker-inline-2"
                    label={t("dashboard.user_stats_from")}
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
                    label={t("dashboard.user_stats_to")}
                    value={to}
                    onChange={handleToDateChange}
                    KeyboardButtonProps={{
                        'aria-label': 'change date',
                    }}
                />
            </MuiPickersUtilsProvider>

            <div className={classes.root}>
                <Paper className={classes.paper}>
                    <TableContainer>
                        <Table
                            className={classes.table}
                            aria-labelledby={t("dashboard.user_statistics")}
                            size={'medium'}
                            aria-label="enhanced table"
                        >
                            <EnhancedTableHead
                                classes={classes}
                                order={order}
                                orderBy={orderBy}
                                onRequestSort={handleRequestSort}
                                rowCount={rows.length}
                            />
                            <TableBody>
                                {stableSort(rows, getComparator(order, orderBy))
                                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                    .map((row, index) => {
                                        return (
                                            <TableRow key={index}>
                                                <TableCell align="left">{row.userName}</TableCell>
                                                <TableCell align="right">{row.fromToTotalSpent}</TableCell>
                                                <TableCell align="right">{row.fromToAverageSpent}</TableCell>
                                            </TableRow>
                                        );
                                    })}
                                {emptyRows > 0 && (
                                    <TableRow style={{ height: 53 * emptyRows }}>
                                        <TableCell colSpan={6} />
                                    </TableRow>
                                )}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        rowsPerPageOptions={[5, 10, 25]}
                        component="div"
                        count={rows.length}
                        labelRowsPerPage={t("dashboard.rows_per_page")}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        onChangePage={handleChangePage}
                        onChangeRowsPerPage={handleChangeRowsPerPage}
                    />
                </Paper>
            </div>
        </div>
    );
};

export default Dashboard;
