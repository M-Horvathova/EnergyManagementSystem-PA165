import PortalUserDTO from "./PortalUserDTO";

export default class PortalUserListingDTO {
    pagesCount: number = 0;
    page: number = 0;
    portalUserDTOs: Array<PortalUserDTO> = [];
}