import axios from "axios";
import jwtDecode from "jwt-decode";
import LoginUser from "../interfaces/LoginUser";
import Config from "../utils/Config";
import {createContext} from "react";
import Registration from "../pages/Registration";
import Login from "../pages/Login";

const tokenKey = "token";

type UserCtx = {
    user: LoginUser | null;
}

export const UserContext = createContext<UserCtx>({user: null});

export async function login(email: string, password: string) {
    try {
        const response = await axios({
            method: "POST",
            url: Config.urlRestBase + "/login",
            data: {
                userName: email,
                password,
            }
        });

        const jwt = response.data;
        localStorage.setItem(tokenKey, jwt);
        return response.status;
    } catch (e) {
        return e.response.status;
    }
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
    try {
        const response = await axios({
            method: "POST",
            url: Config.urlRestBase + "/register",
            data: {
                email,
                password,
                passwordConfirmation: confirmPassword,
                firstName,
                lastName,
                phone
            },
        });

        const jwt = response.data;
        localStorage.setItem(tokenKey, jwt);
        return response.status;
    } catch (e) {
        return e.response.status;
    }
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
