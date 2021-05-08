import axios from "axios";
import jwtDecode from "jwt-decode";
import LoginUser from "../interfaces/LoginUser";
import Config from "../utils/Config";

const tokenKey = "token";

export async function login(email: string, password: string) {
    const response = await axios({
        method: "POST",
        url: Config.urlRestBase + "/login",
        data: {
            userName: email,
            password,
        },
    });
    const jwt = response.data;
    localStorage.setItem(tokenKey, jwt);
}

export function logout() {
    localStorage.removeItem(tokenKey);
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
    login,
    logout,
    getCurrentUser,
};

export default returnModule;
