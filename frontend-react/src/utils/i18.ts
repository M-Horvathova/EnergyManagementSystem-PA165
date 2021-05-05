import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';


import en from "../localization/en";

const resources = {
    en
}

export const languageLabels: Array<string> = [
    "EN"
];

export const languages: Array<string> = [
    "en"
];

export const flags: Array<string> = [
    "gb"
]


i18n
    .use(initReactI18next)
    .init({
        resources,
        lng: "en",
        fallbackLng: "en",// default language, good for testing
        interpolation: {
            escapeValue: false
        }
    });

export default i18n;