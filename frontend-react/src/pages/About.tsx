
import React, { FC } from "react";
import Typography from "@material-ui/core/Typography";
import { useTranslation } from 'react-i18next';
import FaceIcon from '@material-ui/icons/Face';
import {ListItem, ListItemAvatar, ListItemText, Icon} from "@material-ui/core";
import List from "@material-ui/core/List";
import fiLogoBlack from "../utils/inf-lg-cze-black.png";
import fiLogoWhite from "../utils/inf-lg-cze-white.png";
import {FlashOn} from "@material-ui/icons";


type Props = {
    theme: 'light' | 'dark';
}

const About: FC<Props> = ({theme}) => {
    const { t } = useTranslation();
    return <div>
        <br />
        <List>
        <Typography variant={"h3"} align={"center"} color="secondary">
            {t('about.title')}
        </Typography>
        <br />
            <ListItem>
                <ListItemAvatar>
                    <Icon color="secondary">
                        {theme === 'light' &&
                            <img src={fiLogoBlack} height={25} alt={"FI MUNI logo"}/>
                        }
                        {theme === 'dark' &&
                        <img src={fiLogoWhite} height={25} alt={"FI MUNI logo"}/>
                        }

                    </Icon>
                </ListItemAvatar>
                <ListItemText>
                    <p>{t('about.description1')}</p>
                </ListItemText>
            </ListItem>
            <ListItem>
                <ListItemAvatar>
                    <FlashOn color="secondary"/>
                </ListItemAvatar>
                <ListItemText>
                    <p>{t('about.description2')}</p>
                </ListItemText>
            </ListItem>

            <ListItem>
                <ListItemAvatar>
                    <FaceIcon color="secondary"/>
                </ListItemAvatar>
                <ListItemText>
                    <p><i>{t('about.creators')}</i></p>
                </ListItemText>
            </ListItem>

        </List>
    </div>
};

export default About
