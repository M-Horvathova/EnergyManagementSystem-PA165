package cz.fi.muni.pa165.service.facade;


import cz.fi.muni.pa165.facade.PortalUserFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.dto.HouseDTO;
import cz.fi.muni.pa165.dto.PortalUserAuthenticateDTO;
import cz.fi.muni.pa165.dto.PortalUserChangePasswordDTO;
import cz.fi.muni.pa165.dto.PortalUserDTO;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.service.PortalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Martin Podhora
 */
@Service
@Transactional
public class PortalUserFacadeImpl implements PortalUserFacade {
    private final PortalUserService portalUserService;
    private final BeanMappingService beanMappingService;

    @Autowired
    public PortalUserFacadeImpl(PortalUserService portalUserService, BeanMappingService beanMappingService) {
        this.portalUserService = portalUserService;
        this.beanMappingService = beanMappingService;
    }
    @Override
    public void registerUser(PortalUserDTO portalUserDTO, String unencryptedPassword) {
        PortalUser portalUser = portalUserDTOToPortalUser(portalUserDTO);
        Long id = portalUserService.registerUser(portalUser, unencryptedPassword);
        portalUserDTO.setId(id);
    }

    @Override
    public void registerAdministrator(PortalUserDTO portalUserDTO, String unencryptedPassword) {
        PortalUser portalUser = portalUserDTOToPortalUser(portalUserDTO);
        Long id = portalUserService.registerAdministrator(portalUser, unencryptedPassword);
        portalUserDTO.setId(id);
    }

    @Override
    public List<PortalUserDTO> getAllUsers() {
        List<PortalUser> portalUsers = portalUserService.getAllUsers();
        return portalUsers.stream().map(this::portalUserToPortalUserDTO).collect(Collectors.toList());
    }

    @Override
    public boolean authenticate(PortalUserAuthenticateDTO portalUserAuthenticateDTO) {
        return portalUserService.authenticate(
                portalUserService.findUserByEmail(portalUserAuthenticateDTO.getUserName()),
                portalUserAuthenticateDTO.getPassword());
    }

    @Override
    public PortalUserDTO findUserById(Long id)
    {
        return portalUserToPortalUserDTO(portalUserService.findUserById(id));
    }

    @Override
    public PortalUserDTO findUserByEmail(String email) {
        return portalUserToPortalUserDTO(portalUserService.findUserByEmail(email));
    }

    @Override
    public void updateBasicUserInfo(PortalUserDTO portalUserDTO) {
        PortalUser portalUser = portalUserService.findUserById(portalUserDTO.getId());
        portalUserDTOToPortalUser(portalUser, portalUserDTO);
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
        portaluserDTO.setHouses(beanMappingService.mapTo(portalUser.getHouses(), HouseDTO.class));
        return portaluserDTO;
    }

    private PortalUser portalUserDTOToPortalUser(PortalUserDTO portalUserDTO) {
        PortalUser portalUser = new PortalUser();
        portalUser.setEmail(portalUserDTO.getEmail());
        portalUser.setFirstName(portalUserDTO.getFirstName());
        portalUser.setLastName(portalUserDTO.getLastName());
        portalUser.setPhone(portalUserDTO.getPhone());
        return portalUser;
    }

    private void portalUserDTOToPortalUser(PortalUser portalUser, PortalUserDTO portalUserDTO) {
        portalUser.setEmail(portalUserDTO.getEmail());
        portalUser.setFirstName(portalUserDTO.getFirstName());
        portalUser.setLastName(portalUserDTO.getLastName());
        portalUser.setPhone(portalUserDTO.getPhone());
    }
}
