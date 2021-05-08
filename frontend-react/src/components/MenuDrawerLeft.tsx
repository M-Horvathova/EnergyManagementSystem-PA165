import { FC, useState } from "react";
import { Link } from "react-router-dom";

import Drawer from "@material-ui/core/Drawer";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ListItem from "@material-ui/core/ListItem";
import { makeStyles } from "@material-ui/core/styles";
import red from "@material-ui/core/colors/red";
import HomeIcon from "@material-ui/icons/Home";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import InfoIcon from "@material-ui/icons/Info";
import CardGiftcardIcon from "@material-ui/icons/CardGiftcard";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";
import PersonAddIcon from "@material-ui/icons/PersonAdd";
import Box from "@material-ui/core/Box";

import LocMenu from "./LocMenu";
import { useTranslation } from "react-i18next";

const useStyles = makeStyles({
    link: {
        textDecoration: "none",
        color: red[900],
        fontSize: "20",
    },

    title: {
        flexGrow: 1,
    },

    button: {
        borderRadius: "2px",
        backgroundColor: "darkgrey",
        "&:hover": {
            backgroundColor: "white",
        },
    },

    list: {
        backgroundColor: "white",
        borderStyle: "none",
        boxShadow: "none",
    },
});

const MenuDrawerLeft: FC = () => {
    const classes = useStyles();
    const [open, setOpen] = useState(false);
    const { t } = useTranslation();

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Box display="flex" flexGrow={1}>
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            onClick={handleDrawerOpen}
                            edge="start"
                        >
                            <MenuIcon />
                        </IconButton>
                        <Typography variant="h4" style={{ marginTop: "8px" }}>
                            {t("menu.app_name")}
                        </Typography>
                    </Box>
                    <LocMenu />
                </Toolbar>
            </AppBar>
            <Drawer variant="persistent" anchor="left" open={open}>
                <div>
                    <IconButton onClick={handleDrawerClose}>
                        <ChevronLeftIcon />
                    </IconButton>
                </div>
                <Divider />
                <List className={classes.list}>
                    {
                        <>
                            <ListItem alignItems="flex-start"></ListItem>
                            <Divider />
                            <ListItem divider alignItems="center">
                                <ListItemIcon>
                                    {" "}
                                    <HomeIcon />
                                </ListItemIcon>
                                <Link
                                    className={classes.link}
                                    to="/"
                                    onClick={handleDrawerClose}
                                >
                                    <b>{t("menu.home")}</b>
                                </Link>
                            </ListItem>
                            <ListItem divider>
                                <ListItemIcon>
                                    {" "}
                                    <InfoIcon />{" "}
                                </ListItemIcon>
                                <Link
                                    className={classes.link}
                                    to="pa165/about/"
                                    onClick={handleDrawerClose}
                                >
                                    <b>{t("menu.about")}</b>
                                </Link>
                            </ListItem>
                            <ListItem>
                                <ListItemIcon>
                                    {" "}
                                    <CardGiftcardIcon></CardGiftcardIcon>{" "}
                                </ListItemIcon>
                                <Link
                                    className={classes.link}
                                    to="/list/"
                                    onClick={handleDrawerClose}
                                >
                                    <b>{t("menu.gift_lists")}</b>
                                </Link>
                            </ListItem>
                            <Divider />
                            <Divider />

                            <Divider />
                        </>
                    }
                    {
                        <>
                            <ListItem divider>
                                <ListItemIcon>
                                    {" "}
                                    <InfoIcon />{" "}
                                </ListItemIcon>
                                <Link
                                    className={classes.link}
                                    to="/about"
                                    onClick={handleDrawerClose}
                                >
                                    <b>{t("menu.about")}</b>
                                </Link>
                            </ListItem>
                            <Divider />
                            <ListItem divider>
                                <ListItemIcon>
                                    {" "}
                                    <AccountCircleIcon />{" "}
                                </ListItemIcon>
                                <Link
                                    className={classes.link}
                                    to="/login/"
                                    onClick={handleDrawerClose}
                                >
                                    <b>{t("menu.login")}</b>
                                </Link>
                            </ListItem>
                            <ListItem divider>
                                <ListItemIcon>
                                    {" "}
                                    <PersonAddIcon />{" "}
                                </ListItemIcon>
                                <Link
                                    className={classes.link}
                                    to="/register/"
                                    onClick={handleDrawerClose}
                                >
                                    <b>{t("menu.register")}</b>
                                </Link>
                            </ListItem>
                        </>
                    }
                </List>
            </Drawer>
        </div>
    );
};

export default MenuDrawerLeft;
