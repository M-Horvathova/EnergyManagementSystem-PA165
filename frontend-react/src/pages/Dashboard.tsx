import React, {FunctionComponent, useEffect, useState} from "react";
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import Typography from "@material-ui/core/Typography";
import DateFnsUtils from '@date-io/date-fns';
import {Button} from "@material-ui/core";
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
import {createStyles, lighten, makeStyles, Theme} from "@material-ui/core/styles";
import StatisticDTO from "../interfaces/StatisticDTO";

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

const headCells: HeadCell[] = [
    { id: 'userName', numeric: false, disablePadding: false, label: 'username' },
    { id: 'fromToTotalSpent', numeric: true, disablePadding: false, label: 'total spent' },
    { id: 'fromToAverageSpent', numeric: true, disablePadding: false, label: 'average spent' }
];

interface EnhancedTableProps {
    classes: ReturnType<typeof useStyles>;
    onRequestSort: (event: React.MouseEvent<unknown>, property: keyof StatisticDTO) => void;
    order: Order;
    orderBy: string;
    rowCount: number;
}

function EnhancedTableHead(props: EnhancedTableProps) {
    const { classes, order, orderBy, rowCount, onRequestSort } = props;
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

const useToolbarStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            paddingLeft: theme.spacing(2),
            paddingRight: theme.spacing(1),
        },
        highlight:
            theme.palette.type === 'light'
                ? {
                    color: theme.palette.secondary.main,
                    backgroundColor: lighten(theme.palette.secondary.light, 0.85),
                }
                : {
                    color: theme.palette.text.primary,
                    backgroundColor: theme.palette.secondary.dark,
                },
        title: {
            flex: '1 1 100%',
        },
    }),
);

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
        },
    }),
);

export interface UsersProps {}

const Dashboard: FunctionComponent<UsersProps> = () => {
    const [from, setFrom] = React.useState<Date | null>(null);
    const [to, setTo] = React.useState<Date | null>(null);
    const [statistics, setStatistics] = useState<StatisticsDTO>(new StatisticsDTO());

    const getFromDate = () => {
        return from === null ? null : from.toISOString();
    }

    const getToDate = () => {
        return to === null ? null : to.toISOString();
    }

    const handleFromDateChange = (date: Date | null) => {
        setFrom(date);
    };
    const handleToDateChange = (date: Date | null) => {
        setTo(date);
    };
    const { t } = useTranslation();

    useEffect(() => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/statistics/${getFromDate()}/${getToDate()}`,
        }).then((response) => {
            const result: StatisticsDTO = response.data as StatisticsDTO;
            setStatistics(result);
            setRows(result.statistics);
        });
    }, []);

    const handleGetStatisticsEvent = async (event: object) => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/statistics/${getFromDate()}/${getToDate()}`,
        }).then((response) => {
            const result: StatisticsDTO = response.data as StatisticsDTO;
            setStatistics(result);
            setRows(result.statistics);
        });
    };

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

            <div className={classes.root}>
                <Paper className={classes.paper}>
                    <TableContainer>
                        <Table
                            className={classes.table}
                            aria-labelledby="tableTitle"
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
                                            <TableRow>
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
