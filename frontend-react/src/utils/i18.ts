import i18n from "i18next";
import { initReactI18next } from "react-i18next";

import en from "../localization/en";
import es from "../localization/es";
import cs from "../localization/cs";

/*
 author: Michaela Horváthová
*/
const resources = {
    en,
    es,
    cs,
};

export const languageLabels: Array<string> = ["EN", "ES", "CS"];

export const languages: Array<string> = ["en", "es", "cs"];

export const flags: Array<string> = ["gb", "es", "cz"];

export const getSavedLng = () => {
    return localStorage.getItem("lng");
};

export const saveLng = (lng: string) => {
    return localStorage.setItem("lng", lng);
};

i18n.use(initReactI18next).init({
    resources,
    lng: getSavedLng() || "en",
    fallbackLng: "en", // default language, good for testing
    interpolation: {
        escapeValue: false,
    },
});

export default i18n;
