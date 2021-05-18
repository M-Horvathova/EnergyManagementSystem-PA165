import axios from "axios";
import jwtDecode from "jwt-decode";
import LoginUser from "../interfaces/LoginUser";
import Config from "../utils/Config";
import {createContext} from "react";
import Registration from "../pages/Registration";

const tokenKey = "token";

type UserCtx = {
    user: LoginUser | null;
}

export const UserContext = createContext<UserCtx>({user: null});

export async function login(email: string, password: string) {
    const response = await axios({
        method: "POST",
        url: Config.urlRestBase + "/login",
        data: {
            userName: email,
            password,
        },
    });

    if (response.status === 401) {
        var temp: {[index: string]: string;} = {
            "password": ("login.incorrectPassword").match(response.statusText) ? "" : "login.incorrectPassword",
            "username": ("login.incorrectPassword").match(response.statusText) ? "" : "login.notExistingUser"
        };
    }

    const jwt = response.data;
    localStorage.setItem(tokenKey, jwt);
}

export function logout() {
    localStorage.removeItem(tokenKey);
}

export async function register(
        email: string,
        password: string,
        confirmPassword: string,
        firstName: string,
        lastName: string,
        phone: string) {
    const response = await axios({
        method: "POST",
        url: Config.urlRestBase + "/register",
        data: {
            userName: email,
            password,
            passwordConfirmation: confirmPassword,
            firstName,
            lastName,
            phone
        },
    });

    const jwt = response.data;
    localStorage.setItem(tokenKey, jwt);
}

export function getCurrentUser(): LoginUser | null {
    try {
        const jwt = localStorage.getItem(tokenKey);
        if (jwt != null) {
            return jwtDecode(jwt);
        }
        return null;
    } catch (ex) {
        return null;
    }
}

const returnModule = {
    register,
    login,
    logout,
    getCurrentUser,
    UserContext,
};

export default returnModule;
