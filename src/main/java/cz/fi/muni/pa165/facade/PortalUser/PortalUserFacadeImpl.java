package cz.fi.muni.pa165.facade.PortalUser;


import cz.fi.muni.pa165.BeanMappingService;
import cz.fi.muni.pa165.dao.MeterLogDao;
import cz.fi.muni.pa165.dto.PortalUser.NewPortalUserDTO;
import cz.fi.muni.pa165.dto.PortalUser.PortalUserDTO;
import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.service.MeterLogService;
import cz.fi.muni.pa165.service.MeterLogServiceImpl;
import cz.fi.muni.pa165.service.PortalUser.PortalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Martin Podhora
 */
public class PortalUserFacadeImpl implements PortalUserFacade {
    private final PortalUserService portalUserService;

    private final BeanMappingService beanMappingService;

    @Autowired
    public PortalUserFacadeImpl(PortalUserService portalUserService, BeanMappingService beanMappingService) {
        this.portalUserService = portalUserService;
        this.beanMappingService = beanMappingService;
    }
    @Override
    public void registerUser(NewPortalUserDTO portalUser, String unencryptedPassword) {

    }

    @Override
    public void registerAdministrator(NewPortalUserDTO portalUser, String unencryptedPassword) {

    }

    @Override
    public List<PortalUserDTO> getAllUsers() {
        return null;
    }

    @Override
    public boolean authenticate(PortalUserDTO portalUser, String password) {
        return false;
    }

    @Override
    public PortalUserDTO findUserById(long id) {
        return null;
    }

    @Override
    public PortalUserDTO findUserByEmail(String email) {
        return null;
    }

    @Override
    public void updateBasicUserInfo(PortalUserDTO portalUser) {

    }

    @Override
    public void deactivateUser(long id) {

    }

    @Override
    public void reactivateUser(long id) {

    }

    @Override
    public void changePassword(long id, String oldPassword, String newPassword) {

    }

    @Override
    public void delete(long id) {

    }
}
