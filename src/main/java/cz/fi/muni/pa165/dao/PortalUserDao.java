package cz.fi.muni.pa165.dao;


import cz.fi.muni.pa165.entity.PortalUser;

import java.util.List;

/**
 * @author Martin Podhora
 */
public interface PortalUserDao {
    PortalUser findById(long id);
    void create(PortalUser portalUser);
    void update(PortalUser portalUser);
    void delete(PortalUser portalUser);
    List<PortalUser> findAll();
    PortalUser findByUserName(String userName);
}
