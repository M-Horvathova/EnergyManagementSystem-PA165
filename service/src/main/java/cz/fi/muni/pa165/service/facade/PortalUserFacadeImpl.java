package cz.fi.muni.pa165.service.facade;


import cz.fi.muni.pa165.dto.portalUser.*;
import cz.fi.muni.pa165.entity.House;
import cz.fi.muni.pa165.facade.PortalUserFacade;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.service.serviceInterface.PortalUserService;
import cz.fi.muni.pa165.service.mappers.HouseToDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Martin Podhora
 */
@Service
@Transactional
public class PortalUserFacadeImpl implements PortalUserFacade {
    private final PortalUserService portalUserService;
    private HouseToDTOMapper houseToDTOMapper;

    @Autowired
    public PortalUserFacadeImpl(PortalUserService portalUserService, HouseToDTOMapper houseToDTOMapper) {
        this.portalUserService = portalUserService;
        this.houseToDTOMapper = houseToDTOMapper;
    }
    @Override
    public void registerUser(PortalUserRegistrationDTO portalUserRegistrationDTO) {
        PortalUser portalUser = portalUserDTOToPortalUser(portalUserRegistrationDTO);
        Long id = portalUserService.registerUser(portalUser, portalUserRegistrationDTO.getPassword());
        portalUserRegistrationDTO.setId(id);
    }

    @Override
    public void registerAdministrator(PortalUserRegistrationDTO portalUserRegistrationDTO) {
        PortalUser portalUser = portalUserDTOToPortalUser(portalUserRegistrationDTO);
        Long id = portalUserService.registerAdministrator(portalUser, portalUserRegistrationDTO.getPassword());
        portalUserRegistrationDTO.setId(id);
    }

    @Override
    public List<PortalUserDTO> getAllUsers() {
        List<PortalUser> portalUsers = portalUserService.getAllUsers();
        return portalUsers.stream().map(this::portalUserToPortalUserDTO).collect(Collectors.toList());
    }

    @Override
    public PortalUserListingDTO getAllUsers(int page, int itemsCount) {
        List<PortalUser> portalUsers = portalUserService.getAllUsers(page, itemsCount);
        List<PortalUserDTO> portalUserDTOs = portalUsers.stream().map(this::portalUserToPortalUserDTO).collect(Collectors.toList());
        Long allItems = portalUserService.getTotalUsersCount();
        PortalUserListingDTO portalUserListingDTO = new PortalUserListingDTO();
        portalUserListingDTO.setPortalUserDTOs(portalUserDTOs);
        portalUserListingDTO.setPage(page);
        portalUserListingDTO.setPagesCount((int)((allItems / itemsCount) + 1));
        return portalUserListingDTO;
    }

    @Override
    public boolean authenticate(PortalUserAuthenticateDTO portalUserAuthenticateDTO) {
        PortalUser user = portalUserService.findUserByEmail(portalUserAuthenticateDTO.getUserName());
        if (user == null) {
            return false;
        }

        return portalUserService.authenticate(
                user,
                portalUserAuthenticateDTO.getPassword());
    }

    @Override
    public PortalUserDTO findUserById(Long id)
    {
        PortalUser user = portalUserService.findUserById(id);
        if (user == null) {
            return null;
        }

        return portalUserToPortalUserDTO(user);
    }

    @Override
    public PortalUserDTO findUserByEmail(String email) {
        PortalUser user = portalUserService.findUserByEmail(email);
        if (user == null) {
            return null;
        }

        return portalUserToPortalUserDTO(user);
    }

    @Override
    public void updateBasicUserInfo(PortalUserChangeBasicInfoDTO portalUserChangeBasicInfoDTO) {
        PortalUser portalUser = portalUserService.findUserById(portalUserChangeBasicInfoDTO.getId());
        portalUserDTOToPortalUser(portalUser, portalUserChangeBasicInfoDTO);
        portalUserService.updateBasicUserInfo(portalUser);
    }

    @Override
    public void deactivateUser(Long id) {
        portalUserService.deactivateUser(id);
    }

    @Override
    public void reactivateUser(Long id) {
        portalUserService.reactivateUser(id);
    }

    @Override
    public boolean changePassword(PortalUserChangePasswordDTO portalUserChangePasswordDTO) {
        return portalUserService.changePassword(
                portalUserChangePasswordDTO.getId(),
                portalUserChangePasswordDTO.getOldPassword(),
                portalUserChangePasswordDTO.getNewPassword());
    }

    @Override
    public void delete(Long id) {
        portalUserService.delete(portalUserService.findUserById(id));
    }

    private PortalUserDTO portalUserToPortalUserDTO(PortalUser portalUser) {
        if (portalUser == null) {
            return null;
        }

        PortalUserDTO portaluserDTO = new PortalUserDTO();
        portaluserDTO.setId(portalUser.getId());
        portaluserDTO.setActive(portalUser.isActive());
        portaluserDTO.setAdmin(portalUser.isAdmin());
        portaluserDTO.setEmail(portalUser.getEmail());
        portaluserDTO.setCreatedTimestamp(portalUser.getCreatedTimestamp());
        portaluserDTO.setLastLoginTimestamp(portalUser.getLastLoginTimestamp());
        portaluserDTO.setFirstName(portalUser.getFirstName());
        portaluserDTO.setLastName(portalUser.getLastName());
        portaluserDTO.setPasswordHash(portalUser.getPasswordHash());
        portaluserDTO.setPhone(portalUser.getPhone());
        portaluserDTO.setHouses(houseToDTOMapper.convertHouseListToHouseDTOList(new ArrayList<House>(portalUser.getHouses())));
        return portaluserDTO;
    }

    private PortalUser portalUserDTOToPortalUser(PortalUserRegistrationDTO portalUserDTO) {
        if (portalUserDTO == null) {
            return null;
        }

        PortalUser portalUser = new PortalUser();
        portalUser.setEmail(portalUserDTO.getEmail());
        portalUser.setFirstName(portalUserDTO.getFirstName());
        portalUser.setLastName(portalUserDTO.getLastName());
        portalUser.setPhone(portalUserDTO.getPhone());
        return portalUser;
    }

    private void portalUserDTOToPortalUser(PortalUser portalUser, PortalUserChangeBasicInfoDTO portalUserChangeBasicInfoDTO) {
        if (portalUser == null || portalUserChangeBasicInfoDTO == null) {
            return;
        }

        portalUser.setEmail(portalUserChangeBasicInfoDTO.getEmail());
        portalUser.setFirstName(portalUserChangeBasicInfoDTO.getFirstName());
        portalUser.setLastName(portalUserChangeBasicInfoDTO.getLastName());
        portalUser.setPhone(portalUserChangeBasicInfoDTO.getPhone());
    }
}
