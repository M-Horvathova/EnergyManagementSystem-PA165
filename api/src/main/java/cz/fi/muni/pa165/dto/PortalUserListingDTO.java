package cz.fi.muni.pa165.dto;

import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class PortalUserListingDTO {
    private int pagesCount;

    private int page;

    private List<PortalUserDTO> portalUserDTOs;

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<PortalUserDTO> getPortalUserDTOs() {
        return portalUserDTOs;
    }

    public void setPortalUserDTOs(List<PortalUserDTO> portalUserDTOs) {
        this.portalUserDTOs = portalUserDTOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof PortalUserListingDTO)) return false;
        PortalUserListingDTO that = (PortalUserListingDTO) o;
        return Objects.equals(getPagesCount(), that.getPagesCount()) && Objects.equals(getPage(), that.getPage()) && Objects.equals(getPortalUserDTOs(), that.getPortalUserDTOs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPagesCount(), getPage(), getPortalUserDTOs());
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
