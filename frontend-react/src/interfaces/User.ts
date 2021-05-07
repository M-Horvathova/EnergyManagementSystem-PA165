export default interface User {
    email: string;
    firstName: string;
    lastName: string;
    role: 'user' | 'admin';
};
  