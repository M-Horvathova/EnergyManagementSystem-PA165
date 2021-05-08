
import React, { FC } from "react";
import Typography from "@material-ui/core/Typography";
import { useTranslation } from 'react-i18next';


const About: FC = () => {
    const { t } = useTranslation();
    return <div>
        <br />
        <Typography variant={"h3"} align={"center"}>
            {t('about.title')}
        </Typography>
        <br />
        <p>{t('about.description1')}</p>
        <p>{t('about.description2')}</p>
        <p><i>{t('about.creators')}</i></p>
    </div>
};

export default About
