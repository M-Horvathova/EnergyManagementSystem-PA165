import jwtDecode from "jwt-decode";
import User from "../interfaces/User";

const tokenKey = "token";

export async function login(email: string, password: string) {
    // here we should call login api to receive token
    const jwt =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InZhbG8ucGF0cmlrQGdtYWlsLmNvbSIsImZpcnN0bmFtZSI6IlBhdHJpayIsImxhc3RuYW1lIjoiVmFsbyIsInJvbGUiOiJ1c2VyIn0.o5wChSnmm98Fy6qew6Cj9SKIh7NyiPqXPvFpXKBMMeg";
    localStorage.setItem(tokenKey, jwt);
}

export function logout() {
    localStorage.removeItem(tokenKey);
}

export function getCurrentUser(): User | null {
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

export function getJwt() {
    return localStorage.getItem(tokenKey);
}

const returnModule = {
    login,
    logout,
    getCurrentUser,
    getJwt,
};

export default returnModule;
