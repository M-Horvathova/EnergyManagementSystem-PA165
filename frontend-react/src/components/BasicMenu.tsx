import React, { FC } from "react";
import { Link } from "react-router-dom";

import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Menu from "@material-ui/core/Menu";
import IconButton from "@material-ui/core/IconButton";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";
import { useTranslation } from "react-i18next";
import LocMenu from "./LocMenu";
import { MenuItem } from "@material-ui/core";
import { logout } from "../services/auth";

/*
 author: Michaela Horváthová
*/
const useStyles = makeStyles((theme) => ({
    menuButton: {
        marginRight: theme.spacing(2),
    },

    link: {
        textDecoration: "none",
        color: "white",
    },
}));

const BasicMenu: FC = () => {
    const classes = useStyles();
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);

    const { t } = useTranslation();

    const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = () => {
        logout();
        window.location.href = "/pa165";
    };

    return (
        <AppBar color="primary" position="static" variant="outlined">
            <Toolbar>
                <>
                    <Button className={classes.menuButton}>
                        <Link className={classes.link} to="/">
                            <b>{t("menu.home")}</b>
                        </Link>
                    </Button>
                </>
                <Button className={classes.menuButton}>
                    <Link className={classes.link} to="/about">
                        <b>{t("menu.about")}</b>
                    </Link>
                </Button>
                <>
                    <Box display="flex" flexGrow={1}>
                        <Box
                            display="flex"
                            m="auto"
                            alignItems="center"
                            justifyContent="center"
                        >
                            <Typography variant="h4">
                                {t("menu.app_name")}
                            </Typography>
                        </Box>
                    </Box>
                    <IconButton
                        aria-label="account of current user"
                        aria-controls="menu-appbar"
                        aria-haspopup="true"
                        onClick={handleMenu}
                        color="inherit"
                    >
                        <AccountCircleIcon />
                    </IconButton>
                    <Menu
                        id="menu-appbar"
                        anchorEl={anchorEl}
                        anchorOrigin={{
                            vertical: "top",
                            horizontal: "right",
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: "top",
                            horizontal: "right",
                        }}
                        open={open}
                        onClose={handleClose}
                    >
                        <MenuItem onClick={handleLogout}>
                            {t("menu.logout")}
                        </MenuItem>
                    </Menu>
                    <LocMenu />
                </>
            </Toolbar>
        </AppBar>
    );
};

export default BasicMenu;
