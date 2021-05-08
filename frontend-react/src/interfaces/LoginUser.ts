export default interface LoginUser {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    role: "User" | "Administrator";
}
