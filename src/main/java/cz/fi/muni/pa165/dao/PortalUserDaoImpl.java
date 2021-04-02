package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.PortalUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Martin Podhora
 */
@Repository
@Transactional
public class PortalUserDaoImpl implements PortalUserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(PortalUser portalUser) {
        em.persist(portalUser);
    }

    @Override
    public PortalUser findById(long id) {
        return em.find(PortalUser.class, id);
    }

    @Override
    public List<PortalUser> findAll() {
        return em.createQuery("SELECT pu FROM PortalUser pu", PortalUser.class).getResultList();
    }

    @Override
    public PortalUser findByUserName(String userName) {
        return em.createQuery("SELECT pu FROM PortalUser pu where email = :userName", PortalUser.class)
                .setParameter("userName", userName)
                .getSingleResult();
    }

    @Override
    public void update(PortalUser portalUser) {
        em.merge(portalUser);
    }

    @Override
    public void delete(PortalUser portalUser) {
        portalUser = em.merge(portalUser);
        em.remove(portalUser);
    }
}
