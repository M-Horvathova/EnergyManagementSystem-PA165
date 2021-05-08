import React, { FC, useState } from "react";
import i18n, {
    languages,
    languageLabels,
    flags,
    getSavedLng,
    saveLng,
} from "../utils/i18";
import { withTranslation } from "react-i18next";
import Flag from "react-world-flags";
import {
    Typography,
    Button,
    Menu,
    MenuItem,
    ListItemIcon,
} from "@material-ui/core";

const LocMenu: FC = () => {
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [language, setLanguage] = useState<string>(getSavedLng() || "en");

    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const changeLanguage = (lng: string) => {
        setLanguage(lng);
        i18n.changeLanguage(lng);
        saveLng(lng);
        setAnchorEl(null);
    };

    return (
        <div>
            <Button
                aria-controls="simple-menu"
                aria-haspopup="true"
                onClick={handleClick}
            >
                <Typography variant="button" style={{ color: "white" }}>
                    {language}
                </Typography>
            </Button>
            <Menu
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
            >
                {languages.length === 0 ? (
                    <MenuItem />
                ) : (
                    languages.map((item, index) => (
                        <MenuItem
                            key={index}
                            onClick={() => changeLanguage(item)}
                        >
                            <ListItemIcon>
                                <Flag
                                    code={flags[index]}
                                    height="20"
                                    width="30"
                                />
                            </ListItemIcon>
                            {languageLabels[index]}
                        </MenuItem>
                    ))
                )}
            </Menu>
        </div>
    );
};

export default withTranslation()(LocMenu);
