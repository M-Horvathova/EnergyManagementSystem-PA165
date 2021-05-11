export default interface AddressDTO {
    id: number;
    street: string | null;
    code: string | null;
    city: string;
    postCode: string;
    country: string;
}
