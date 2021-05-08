export default interface Address {
    id: number;
    street: string | null;
    code: string | null;
    city: string;
    postCode: string;
    country: string;
}
