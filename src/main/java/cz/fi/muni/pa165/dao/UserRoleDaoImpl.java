package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.PortalUser;
import cz.fi.muni.pa165.entity.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin Podhora
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(UserRole userRole) {
        em.persist(userRole);
    }

    @Override
    public UserRole findById(long id) {
        return em.find(UserRole.class, id);
    }

    @Override
    public List<UserRole> findAll() {
        return em.createQuery("SELECT ur FROM UserRole ur", UserRole.class).getResultList();
    }

    @Override
    public UserRole findByName(String name) {
        try {
            return em.createQuery("SELECT ur FROM UserRole ur where roleName = :name", UserRole.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }    }

    @Override
    public void update(UserRole userRole) {
        em.merge(userRole);
    }

    @Override
    public void delete(UserRole userRole) {
        userRole = em.merge(userRole);
        em.remove(userRole);
    }
}
