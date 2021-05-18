import i18n from "i18next";
import { initReactI18next } from "react-i18next";

import en from "../localization/en";
import es from "../localization/es";
import cs from "../localization/cs";
import de from "../localization/de";
import it from "../localization/it";

/*
 author: Michaela Horváthová
*/
const resources = {
    en,
    es,
    cs,
    de,
    it,
};

export const languageLabels: Array<string> = ["EN", "ES", "CS", "DE", "IT"];

export const languages: Array<string> = ["en", "es", "cs", "de", "it"];

export const flags: Array<string> = ["gb", "es", "cz", "de", "it"];

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
